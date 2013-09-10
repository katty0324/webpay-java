package jp.webpay.exception;

import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

public class InvalidRequestException extends WebPayException {
    @Getter
    private final String type;
    @Getter
    private final String param;

    InvalidRequestException(int status, @NonNull Map<String, String> errorInfo) {
        super(errorInfo.get("message"), status, errorInfo);
        this.type = errorInfo.get("type");
        this.param = errorInfo.get("param");
    }
}
