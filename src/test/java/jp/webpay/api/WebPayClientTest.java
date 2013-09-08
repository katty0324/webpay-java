package jp.webpay.api;

import jp.webpay.exception.APIException;
import jp.webpay.exception.AuthenticationException;
import jp.webpay.exception.CardException;
import jp.webpay.exception.InvalidRequestException;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WebPayClientTest extends ApiTestFixture {

    @Test(expected = APIException.class)
    public void testGetRaisesAPIException() throws Exception {
        stubError("errors/unknown_api_error");
        try {
            client.get("/test");
        } catch (APIException e) {
            assertThat(e.getMessage(), is("Unknown error occurred"));
            assertThat(e.getType(), is("api_error"));
            throw e;
        }
    }

    @Test(expected = InvalidRequestException.class)
    public void testGetRaisesInvalidRequest() throws Exception {
        stubError("errors/bad_request");
        try {
            client.get("/test");
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Missing required param: currency"));
            assertThat(e.getType(), is("invalid_request_error"));
            assertThat(e.getParam(), is("currency"));
            throw e;
        }
    }

    @Test(expected = InvalidRequestException.class)
    public void testGetRaisesNotFound() throws Exception {
        stubError("errors/not_found");
        try {
            client.get("/test");
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("No such charge: foo"));
            assertThat(e.getType(), is("invalid_request_error"));
            assertThat(e.getParam(), is("id"));
            throw e;
        }
    }

    @Test(expected = AuthenticationException.class)
    public void testGetRaisesUnauthorized() throws Exception {
        stubError("errors/unauthorized");
        try {
            client.get("/test");
        } catch (AuthenticationException e) {
            assertThat(e.getMessage(), is("Invalid API key provided. Check your API key is correct."));
            throw e;
        }
    }

    @Test(expected = CardException.class)
    public void testGetRaisesCardError() throws Exception {
        stubError("errors/card_error");
        try {
            client.get("/test");
        } catch (CardException e) {
            assertThat(e.getMessage(), is("Your card number is incorrect"));
            assertThat(e.getType(), is("card_error"));
            assertThat(e.getCode(), is("incorrect_number"));
            assertThat(e.getParam(), is("number"));
            throw e;
        }
    }

    private void stubError(String error) throws Exception {
        stubFor(get("/v1/test").willReturn(response(error)));
    }
}
