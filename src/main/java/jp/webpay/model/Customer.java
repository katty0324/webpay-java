package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import jp.webpay.exception.ApiConnectionException;
import jp.webpay.request.CardRequest;
import jp.webpay.request.CustomerRequest;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import net.arnx.jsonic.JSON;

import java.util.Date;

@Getter
@Setter
@ToString
public class Customer extends AbstractModel {
    private String id;
    private String object;
    private Boolean livemode;
    private Long created;
    private Card activeCard;
    private String description;
    private String email;

    // For update
    private CardRequest newCard;

    public static Customer fromJsonResponse(@NonNull WebPayClient client, @NonNull String json) {
        Customer decoded;
        try {
            decoded = JSON.decode(json, Customer.class);
        } catch (net.arnx.jsonic.JSONException e) {
            throw ApiConnectionException.jsonException(json);
        }
        decoded.client = client;
        return decoded;
    }

    public Date createdDate() {
        if (created == null) {
            return null;
        }
        return new Date(created * 1000);
    }

    public void save() {
        CustomerRequest request = new CustomerRequest()
                .card(newCard)
                .description(description)
                .email(email);
        copyAttributes(client.customers.update(id, request));
    }

    private void copyAttributes(Customer customer) {
        this.created = customer.created;
        this.activeCard = customer.activeCard;
        this.description = customer.description;
        this.email = customer.email;
    }
}
