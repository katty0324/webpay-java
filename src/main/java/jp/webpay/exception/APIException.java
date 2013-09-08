package jp.webpay.exception;

import lombok.Getter;

import java.util.Map;

public class APIException extends WebPayException {
    @Getter
    private final String type;

    public APIException(int status, Map<String, String> errorInfo) {
        super(errorInfo.get("message"), status, errorInfo);
        this.type = errorInfo.get("type");
    }
}
