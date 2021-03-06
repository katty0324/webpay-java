package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import jp.webpay.exception.ApiConnectionException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import net.arnx.jsonic.JSON;

import java.util.Date;

@Getter
@Setter
@ToString
public class Charge extends AbstractModel {
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

    public static Charge fromJsonResponse(@NonNull WebPayClient client, @NonNull String json) {
        Charge decoded;
        try {
            decoded = JSON.decode(json, Charge.class);
        } catch (net.arnx.jsonic.JSONException e) {
            throw ApiConnectionException.jsonException(json);
        }
        decoded.client = client;
        return decoded;
    }

    public Date createdDate() {
        return timestampToDate(created);
    }

    public Date expireDate() {
        return timestampToDate(expireTime);
    }

    public void refund() {
        copyAttributes(client.charges.refund(id, amount));
    }

    public void refund(long amount) {
        copyAttributes(client.charges.refund(id, amount));
    }

    public void capture() {
        copyAttributes(client.charges.capture(id));
    }

    public void capture(long amount) {
        copyAttributes(client.charges.capture(id, amount));
    }

    private void copyAttributes(Charge charge) {
        this.amount = charge.amount;
        this.card = charge.card;
        this.created = charge.created;
        this.currency = charge.currency;
        this.paid = charge.paid;
        this.captured = charge.captured;
        this.refunded = charge.refunded;
        this.amountRefunded = charge.amountRefunded;
        this.customer = charge.customer;
        this.description = charge.description;
        this.failureMessage = charge.failureMessage;
        this.expireTime = charge.expireTime;
    }
}
