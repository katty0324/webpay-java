package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import jp.webpay.exception.ApiConnectionException;
import lombok.NonNull;
import lombok.ToString;
import net.arnx.jsonic.JSON;

import java.util.List;

@ToString
public class ChargeList extends EntityList<Charge> {
    public void setData(@NonNull List<Charge> data) {
        this.data = data;
    }

    public static ChargeList fromJsonResponse(@NonNull WebPayClient client, @NonNull String json) {
        ChargeList list;
        try {
            list = JSON.decode(json, ChargeList.class);
        } catch (net.arnx.jsonic.JSONException e) {
            throw ApiConnectionException.jsonException(json);
        }
        for (Charge row : list.getData()) {
            row.client = client;
        }
        return list;
    }
}
