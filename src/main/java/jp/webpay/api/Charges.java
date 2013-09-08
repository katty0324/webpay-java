package jp.webpay.api;

import jp.webpay.model.Charge;
import jp.webpay.request.ChargeRequest;

public class Charges {
    private final WebPayClient client;

    Charges(WebPayClient client) {
        this.client = client;
    }

    public Charge create(ChargeRequest request) {
        return Charge.fromJsonResponse(client.post("/charges", request.toForm()));
    }
}
