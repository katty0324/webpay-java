package jp.webpay.api;

import jp.webpay.model.Token;
import jp.webpay.request.CardRequest;
import lombok.NonNull;

public class Tokens extends Accessor {
    Tokens(WebPayClient client) {
        super(client);
    }

    public Token create(@NonNull CardRequest request) {
        return Token.fromJsonResponse(client, client.post("/tokens", request.toForm()));
    }
}
