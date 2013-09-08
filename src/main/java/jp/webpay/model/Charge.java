package jp.webpay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.arnx.jsonic.JSON;

import java.util.Date;

@Getter
@Setter
@ToString
public class Charge {
    private String id;
    private String object;
    private Boolean livemode;
    private Long amount;
    private Card card;
    private Long created;
    private String currency;
    private Boolean paid;
    private Boolean captured;
    private Boolean refunded;
    private Long amountRefunded;
    private String customer;
    private String description;
    private String failureMessage;
    private Long expireTime;

    public static Charge fromJsonResponse(String json) {
        return JSON.decode(json, Charge.class);
    }

    public Date createdDate() {
        if (created == null) {
            return null;
        }
        return new Date(created * 1000);
    }

    public Date expireDate() {
        if (expireTime == null) {
            return null;
        }
        return new Date(expireTime * 1000);
    }
}
