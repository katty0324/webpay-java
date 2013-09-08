package jp.webpay.api;

import jp.webpay.model.Charge;
import jp.webpay.model.ChargeList;
import jp.webpay.request.ChargeRequest;
import jp.webpay.request.ListRequest;

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

    public Charge capture(String id) {
        return Charge.fromJsonResponse(client, client.post("/charges/" + id + "/capture", new Form()));
    }

    public Charge capture(String id, long amount) {
        Form form = new Form();
        form.param("amount", String.valueOf(amount));
        return Charge.fromJsonResponse(client, client.post("/charges/" + id + "/capture", form));
    }

    public ChargeList all() {
        return all(new ListRequest(), null);
    }

    public ChargeList all(ListRequest request) {
        return all(request, null);
    }

    public ChargeList all(String customerId) {
        return all(new ListRequest(), customerId);
    }

    public ChargeList all(ListRequest request, String customerId) {
        Form form = request.toForm();
        if (customerId != null) {
            form.param("customer", customerId);
        }
        return ChargeList.fromJsonResponse(client, client.get("/charges", form));
    }
}
