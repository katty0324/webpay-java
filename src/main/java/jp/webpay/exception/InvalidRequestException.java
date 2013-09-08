package jp.webpay.exception;

import lombok.Getter;

import java.util.Map;

public class InvalidRequestException extends WebPayException {
    @Getter
    private final String type;
    @Getter
    private final String param;

    public InvalidRequestException(int status, Map<String, String> errorInfo) {
        super(errorInfo.get("message"), status, errorInfo);
        this.type = errorInfo.get("type");
        this.param = errorInfo.get("param");
    }
}
