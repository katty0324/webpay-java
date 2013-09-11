package jp.webpay.api;

import jp.webpay.model.EventList;
import jp.webpay.request.ListRequest;
import lombok.NonNull;

import javax.ws.rs.core.Form;

public class Events extends Accessor {
    protected Events(WebPayClient client) {
        super(client);
    }

    public EventList all() {
        return all(new ListRequest(), null);
    }

    public EventList all(@NonNull ListRequest request) {
        return all(request, null);
    }

    public EventList all(@NonNull String type) {
        return all(new ListRequest(), type);
    }

    public EventList all(@NonNull ListRequest request, String type) {
        Form form = request.toForm();
        if (type != null && !type.equals("")) {
            form.param("type", type);
        }
        return EventList.fromJsonResponse(client, client.get("/events", form));
    }
}
