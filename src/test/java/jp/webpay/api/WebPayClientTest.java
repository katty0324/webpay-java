package jp.webpay.api;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import jp.webpay.model.Charge;
import jp.webpay.request.CardRequest;
import jp.webpay.request.ChargeRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Form;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WebPayClientTest {
    private static final int PORT = 9229;
    private WebPayClient client;
    private final String apiKey = "test_secret_XXXXXXXXXXXXXXXXXXXXX";
    private final String encoded = Base64.encodeBase64String((apiKey+":").getBytes());

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);

    @Before
    public void instantiate() {
        client = new WebPayClient(apiKey, "http://127.0.0.1:" + PORT + "/v1");
    }

    @Test
    public void testCreateCharge() throws Exception {
        stubFor(post(urlEqualTo("/v1/charges"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Authorization", equalTo("Basic " + encoded))
                .withRequestBody(matching(".*amount=1000.*customer=cus_fgR4vI92r54I6oK.*"))
                .willReturn(response("charges/of_customer")));
        Form form = new ChargeRequest()
                .amount(1000)
                .customer("cus_fgR4vI92r54I6oK")
                .description("Test Charge from Java")
                .toForm();
        Charge charge = client.createCharge(form);

        assertThat(charge.getAmount(), is(1000L));
        assertThat(charge.getCustomer(), is("cus_fgR4vI92r54I6oK"));
    }

    @Test
    public void testCreateChargeWithCard() throws Exception {
        stubFor(post(urlEqualTo("/v1/charges"))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Authorization", equalTo("Basic " + encoded))
                .withRequestBody(containing("card%5Bname%5D=YUUKO+SHIONJI"))
                .willReturn(response("charges/with_card")));
        CardRequest card = new CardRequest()
                .number("4242-4242-4242-4242")
                .expMonth(12)
                .expYear(2015)
                .cvc(123)
                .name("YUUKO SHIONJI");
        Form form = new ChargeRequest()
                .amount(1000)
                .card(card)
                .toForm();
        Charge charge = client.createCharge(form);
        assertThat(charge.getCard().getName(), is("YUUKO SHIONJI"));
    }

    private ResponseDefinitionBuilder response(String name) throws IOException {
        ResponseDefinitionBuilder builder = aResponse();

        String data = FileUtils.readFileToString(FileUtils.toFile(this.getClass().getResource("/" + name + ".txt")));
        String [] lines = data.split("\r\n");
        StringBuilder sb = new StringBuilder();
        boolean bodyStarted = false;
        for (int i = 0; i < lines.length; ++i) {
            String line = lines[i];
            if (i == 0) {
                builder.withStatus(Integer.valueOf(line.split(" ")[1]));
            } else if (bodyStarted) {
                sb.append(line);
                sb.append("\r\n");
            } else if (line.equals("")) {
                bodyStarted = true;
            } else {
                String[] split = line.split(":", 2);
                builder.withHeader(split[0], split[1].replaceAll("\\A\\s+", ""));
            }
        }

        return builder.withBody(sb.toString());
    }
}
