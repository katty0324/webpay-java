package jp.webpay.exception;

import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

public class APIException extends WebPayException {
    @Getter
    private final String type;

    APIException(int status, @NonNull Map<String, String> errorInfo) {
        super(errorInfo.get("message"), status, errorInfo);
        this.type = errorInfo.get("type");
    }
}
