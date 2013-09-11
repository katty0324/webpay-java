package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import jp.webpay.exception.ApiConnectionException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import net.arnx.jsonic.JSON;

import java.util.List;

@Getter
@Setter
@ToString
public class Account extends AbstractModel {
    private String id;
    private String object;
    private Boolean chargeEnabled;
    private List<String> currenciesSupported;
    private Boolean detailsSubmitted;
    private String email;
    private String statementDescriptor;

    public static Account fromJsonResponse(@NonNull WebPayClient client, @NonNull String json) {
        Account decoded;
        try {
            decoded = JSON.decode(json, Account.class);
        } catch (net.arnx.jsonic.JSONException e) {
            throw ApiConnectionException.jsonException(json);
        }
        decoded.client = client;
        return decoded;
    }

    public boolean isChargeEnabled() {
        return chargeEnabled;
    }

    public boolean isDetailsSubmitted() {
        return detailsSubmitted;
    }
}
