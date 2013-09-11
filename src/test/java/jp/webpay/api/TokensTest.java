package jp.webpay.api;

import jp.webpay.model.Token;
import jp.webpay.request.CardRequest;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TokensTest extends ApiTestFixture {
    @Test
    public void testCreateToken() throws Exception {
        stubFor(post("/v1/tokens")
                .withRequestBody(containing("card%5Bname%5D=YUUKO+SHIONJI"))
                .willReturn(response("tokens/create")));
        CardRequest card = new CardRequest()
                .number("4242-4242-4242-4242")
                .expMonth(12)
                .expYear(2015)
                .cvc(123)
                .name("YUUKO SHIONJI");

        Token token = client.tokens.create(card);

        assertThat(token.getUsed(), is(false));
        assertThat(token.getCard().getName(), is("YUUKO SHIONJI"));
    }
}
