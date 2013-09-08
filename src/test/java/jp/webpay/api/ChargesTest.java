package jp.webpay.api;

import jp.webpay.model.Charge;
import jp.webpay.request.CardRequest;
import jp.webpay.request.ChargeRequest;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChargesTest extends ApiTestFixture {
    @Test
    public void testCreateCharge() throws Exception {
        stubFor(post("/v1/charges")
                .withRequestBody(matching(".*amount=1000.*customer=cus_fgR4vI92r54I6oK.*"))
                .willReturn(response("charges/create_with_customer")));
        ChargeRequest request = new ChargeRequest()
                .amount(1000)
                .customer("cus_fgR4vI92r54I6oK")
                .description("Test Charge from Java");

        Charge charge = client.charges.create(request);

        assertThat(charge.getAmount(), is(1000L));
        assertThat(charge.getCustomer(), is("cus_fgR4vI92r54I6oK"));
    }

    @Test
    public void testCreateChargeWithCard() throws Exception {
        stubFor(post("/v1/charges")
                .withRequestBody(containing("card%5Bname%5D=YUUKO+SHIONJI"))
                .willReturn(response("charges/create_with_card")));
        CardRequest card = new CardRequest()
                .number("4242-4242-4242-4242")
                .expMonth(12)
                .expYear(2015)
                .cvc(123)
                .name("YUUKO SHIONJI");
        ChargeRequest request = new ChargeRequest()
                .amount(1000)
                .card(card);

        Charge charge = client.charges.create(request);
        assertThat(charge.getCard().getName(), is("YUUKO SHIONJI"));
    }

    @Test
    public void testRetrieveCharge() throws Exception {
        stubFor(get("/v1/charges/ch_bWp5EG9smcCYeEx")
                .willReturn(response("charges/retrieve")));

        Charge charge = client.charges.retrieve("ch_bWp5EG9smcCYeEx");
        assertThat(charge.getId(), is("ch_bWp5EG9smcCYeEx"));
        assertThat(charge.getDescription(), is("アイテムの購入"));
    }

    @Test
    public void testRefundCharge() throws Exception {
        stubFor(get("/v1/charges/ch_bWp5EG9smcCYeEx")
                .willReturn(response("charges/retrieve")));
        stubFor(post("/v1/charges/ch_bWp5EG9smcCYeEx/refund")
                .withRequestBody(containing("amount=400"))
                .willReturn(response("charges/refund")));

        Charge charge = client.charges.retrieve("ch_bWp5EG9smcCYeEx");
        charge.refund();
        assertThat(charge.getRefunded(), is(true));
        assertThat(charge.getAmountRefunded(), is(charge.getAmount()));
    }

    @Test
    public void testCaptureCharge() throws Exception {
        stubFor(get("/v1/charges/ch_2X01NDedxdrRcA3")
                .willReturn(response("charges/retrieve_not_captured")));
        stubFor(post("/v1/charges/ch_2X01NDedxdrRcA3/capture")
                .withHeader("Content-Length", equalTo("0"))
                .willReturn(response("charges/capture")));

        Charge charge = client.charges.retrieve("ch_2X01NDedxdrRcA3");
        assertThat(charge.getCaptured(), is(false));
        charge.capture();
        assertThat(charge.getCaptured(), is(true));
    }
}
