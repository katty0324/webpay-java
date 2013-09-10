package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import jp.webpay.exception.ApiConnectionException;
import lombok.NonNull;
import net.arnx.jsonic.JSON;

import java.util.List;

public class CustomerList extends EntityList<Customer> {
    public void setData(@NonNull List<Customer> data) {
        this.data = data;
    }

    public static CustomerList fromJsonResponse(@NonNull WebPayClient client, @NonNull String json) {
        CustomerList list;
        try {
            list = JSON.decode(json, CustomerList.class);
        } catch (net.arnx.jsonic.JSONException e) {
            throw ApiConnectionException.jsonException(json);
        }
        for (Customer row : list.getData()) {
            row.client = client;
        }
        return list;
    }
}
