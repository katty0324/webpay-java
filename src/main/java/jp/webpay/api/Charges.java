package jp.webpay.api;

import jp.webpay.model.Charge;
import jp.webpay.request.ChargeRequest;

import javax.ws.rs.core.Form;

public class Charges {
    private final WebPayClient client;

    Charges(WebPayClient client) {
        this.client = client;
    }

    public Charge create(ChargeRequest request) {
        return Charge.fromJsonResponse(client, client.post("/charges", request.toForm()));
    }

    public Charge retrieve(String id) {
        return Charge.fromJsonResponse(client, client.get("/charges/" + id));
    }

    public Charge refund(String id, Long amount) {
        Form form = new Form();
        form.param("amount", amount.toString());
        return Charge.fromJsonResponse(client, client.post("/charges/" + id + "/refund", form));
    }
}
