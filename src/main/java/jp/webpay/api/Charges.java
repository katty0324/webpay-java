package jp.webpay.api;

import jp.webpay.model.Charge;
import jp.webpay.model.ChargeList;
import jp.webpay.request.ChargeRequest;
import jp.webpay.request.ListRequest;
import lombok.NonNull;

import javax.ws.rs.core.Form;

public class Charges {
    private final WebPayClient client;

    Charges(@NonNull WebPayClient client) {
        this.client = client;
    }

    public Charge create(@NonNull ChargeRequest request) {
        return Charge.fromJsonResponse(client, client.post("/charges", request.toForm()));
    }

    public Charge retrieve(@NonNull String id) {
        assertId(id);
        return Charge.fromJsonResponse(client, client.get("/charges/" + id));
    }

    public Charge refund(@NonNull String id, long amount) {
        assertId(id);
        Form form = new Form();
        form.param("amount", String.valueOf(amount));
        return Charge.fromJsonResponse(client, client.post("/charges/" + id + "/refund", form));
    }

    public Charge capture(@NonNull String id) {
        assertId(id);
        return Charge.fromJsonResponse(client, client.post("/charges/" + id + "/capture", new Form()));
    }

    public Charge capture(@NonNull String id, long amount) {
        assertId(id);
        Form form = new Form();
        form.param("amount", String.valueOf(amount));
        return Charge.fromJsonResponse(client, client.post("/charges/" + id + "/capture", form));
    }

    public ChargeList all() {
        return all(new ListRequest(), null);
    }

    public ChargeList all(@NonNull ListRequest request) {
        return all(request, null);
    }

    public ChargeList all(@NonNull String customerId) {
        assertId(customerId);
        return all(new ListRequest(), customerId);
    }

    public ChargeList all(@NonNull ListRequest request, String customerId) {
        Form form = request.toForm();
        if (customerId != null && !customerId.equals("")) {
            form.param("customer", customerId);
        }
        return ChargeList.fromJsonResponse(client, client.get("/charges", form));
    }

    private void assertId(String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("The given ID is empty");
        }
    }
}
