package jp.webpay.api;

import jp.webpay.model.Customer;
import jp.webpay.request.CustomerRequest;
import lombok.NonNull;

public class Customers extends Accessor {
    Customers(@NonNull WebPayClient client) {
        super(client);
    }

    public Customer create(@NonNull CustomerRequest request) {
        return Customer.fromJsonResponse(client, client.post("/customers", request.toForm()));
    }

    public Customer retrieve(@NonNull String id) {
        assertId(id);
        return Customer.fromJsonResponse(client, client.get("/customers/" + id));
    }

    public Customer update(@NonNull String id, @NonNull CustomerRequest request) {
        assertId(id);
        return Customer.fromJsonResponse(client, client.post("/customers/" + id, request.toForm()));
    }
}
