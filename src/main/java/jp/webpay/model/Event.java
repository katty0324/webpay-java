package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import jp.webpay.exception.ApiConnectionException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.arnx.jsonic.JSON;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
@ToString
public class Event extends AbstractModel {
    private String id;
    private String object;
    private Boolean livemode;
    private Long created;
    private EventData data;
    private Integer pendingWebhooks;
    private String type;

    public static Event fromJsonResponse(WebPayClient client, String json) {
        Event decoded;
        try {
            decoded = JSON.decode(json, Event.class);
        } catch (net.arnx.jsonic.JSONException e) {
            throw ApiConnectionException.jsonException(json);
        }
        decoded.client = client;
        return decoded;
    }

    public Date createdDate() {
        return timestampToDate(created);
    }

    @Getter
    @Setter
    @ToString
    public class EventData {
        private Map object;
        private Map previousAttributes;
    }
}
