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
}
