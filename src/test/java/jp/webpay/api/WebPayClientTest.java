package jp.webpay.api;

import jp.webpay.exception.APIException;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WebPayClientTest extends ApiTestFixture {

    @Test(expected = APIException.class)
    public void testGetRaisesAPIException() throws Exception {
        stubFor(get("/v1/test")
                .willReturn(response("errors/unknown_api_error")));
        try {
            client.get("/test");
        } catch (APIException e) {
            assertThat(e.getMessage(), is("Unknown error occurred"));
            assertThat(e.getType(), is("api_error"));
            throw e;
        }
    }
}
