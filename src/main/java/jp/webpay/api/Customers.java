package jp.webpay.api;

import jp.webpay.exception.ApiConnectionException;
import jp.webpay.model.Customer;
import jp.webpay.request.CustomerRequest;
import lombok.NonNull;
import net.arnx.jsonic.JSON;

import java.util.Map;

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

    public boolean delete(@NonNull String id) {
        assertId(id);
        String json = client.delete("/customers/" + id);

        try {
            Map data = JSON.decode(json);
            return (boolean)data.get("deleted");
        } catch (net.arnx.jsonic.JSONException | ClassCastException e) {
            throw ApiConnectionException.jsonException(json);
        }
    }
}
