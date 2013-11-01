package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import jp.webpay.exception.ApiConnectionException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import net.arnx.jsonic.JSON;

import java.util.Map;

@Getter
@ToString
public class RetrievedCustomer {
    private final String id;
    private final boolean deleted;
    private final Customer customer;

    private RetrievedCustomer(Customer customer) {
        this.id = customer.getId();
        this.deleted = false;
        this.customer = customer;
    }

    private RetrievedCustomer(String id) {
        this.id = id;
        this.deleted = true;
        this.customer = null;
    }

    public static RetrievedCustomer fromJsonResponse(@NonNull WebPayClient client, @NonNull String json) {
        Map<String, Object> decoded;
        try {
            decoded = JSON.decode(json);
        } catch (net.arnx.jsonic.JSONException e) {
            throw ApiConnectionException.jsonException(json);
        }
        if (decoded.containsKey("deleted") && decoded.get("deleted") == true) {
            return new RetrievedCustomer((String)decoded.get("id"));
        } else {
            return new RetrievedCustomer(Customer.fromJsonResponse(client, json));
        }
    }
}
