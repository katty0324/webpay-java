package jp.webpay.api;

import jp.webpay.model.Customer;
import jp.webpay.request.CustomerRequest;
import lombok.NonNull;

public class Customers {
    private final WebPayClient client;

    Customers(@NonNull WebPayClient client) {
        this.client = client;
    }

    public Customer create(@NonNull CustomerRequest request) {
        return Customer.fromJsonResponse(client, client.post("/customers", request.toForm()));
    }
}
