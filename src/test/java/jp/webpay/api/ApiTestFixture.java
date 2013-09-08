package jp.webpay.api;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class ApiTestFixture {
    private static final int PORT = 9229;
    protected WebPayClient client;
    private final String apiKey = "test_secret_XXXXXXXXXXXXXXXXXXXXX";
    private final String encoded = Base64.encodeBase64String((apiKey + ":").getBytes());

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);

    @Before
    public void instantiateClient() {
        client = new WebPayClient(apiKey, "http://127.0.0.1:" + PORT + "/v1");
    }

    protected MappingBuilder post(String path) {
        return com.github.tomakehurst.wiremock.client.WireMock.post(urlEqualTo(path))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Authorization", equalTo("Basic " + encoded));
    }

    protected ResponseDefinitionBuilder response(String name) throws IOException {
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
