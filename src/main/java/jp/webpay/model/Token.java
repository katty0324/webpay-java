package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import jp.webpay.exception.ApiConnectionException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.arnx.jsonic.JSON;

@Getter
@Setter
@ToString
public class Token extends AbstractModel {
    private String id;
    private String object;
    private Boolean livemode;
    private Long created;
    private Boolean used;
    private Card card;

    public static Token fromJsonResponse(WebPayClient client, String json) {
        Token decoded;
        try {
            decoded = JSON.decode(json, Token.class);
        } catch (net.arnx.jsonic.JSONException e) {
            throw ApiConnectionException.jsonException(json);
        }
        decoded.client = client;
        return decoded;
    }
}
