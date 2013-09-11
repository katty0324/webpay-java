package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import jp.webpay.exception.ApiConnectionException;
import lombok.NonNull;
import net.arnx.jsonic.JSON;

import java.util.List;

public class EventList extends EntityList<Event> {
    public void setData(@NonNull List<Event> data) {
        this.data = data;
    }

    public static EventList fromJsonResponse(@NonNull WebPayClient client, @NonNull String json) {
        EventList list;
        try {
            list = JSON.decode(json, EventList.class);
        } catch (net.arnx.jsonic.JSONException e) {
            throw ApiConnectionException.jsonException(json);
        }
        for (Event row : list.getData()) {
            row.client = client;
        }
        return list;
    }
}
