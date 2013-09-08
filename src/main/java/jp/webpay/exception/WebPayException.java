package jp.webpay.exception;

import lombok.Getter;
import net.arnx.jsonic.JSON;

import java.util.HashMap;
import java.util.Map;

public class WebPayException extends RuntimeException {
    @Getter
    private final Integer status;
    @Getter
    private final Map<String, String> errorInfo;

    WebPayException(String message) {
        this(message, null, null);
    }

    WebPayException(String message, Integer status, Map<String, String> errorInfo) {
        super(message);
        this.status = status;
        this.errorInfo = errorInfo;
    }

    public static WebPayException fromJsonResponse(int status, String json) {
        Object decoded;
        try {
            decoded = JSON.decode(json);
        } catch (net.arnx.jsonic.JSONException e) {
            throw ApiConnectionException.jsonException(json);
        }
        Map<String, String> errorInfo = new HashMap<>();
        if (decoded instanceof Map && ((Map) decoded).get("error") instanceof Map) {
            Map errorObject = (Map) ((Map) decoded).get("error");
            for (Object key : errorObject.keySet()) {
                errorInfo.put(key.toString(), errorObject.get(key).toString());
            }
        } else {
            return new ApiConnectionException("Failed to parse error response");
        }
        switch (status) {
            case 400:
            case 404:
                return new InvalidRequestException(status, errorInfo);
            case 401:
                return new AuthenticationException(status, errorInfo);
            case 402:
                return new CardException(status, errorInfo);
            default:
                return new APIException(status, errorInfo);
        }
    }


}
