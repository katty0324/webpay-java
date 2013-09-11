package jp.webpay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    @Getter
    @Setter
    @ToString
    public class EventData {
        private Map object;
        private Map previousAttributes;
    }
}
