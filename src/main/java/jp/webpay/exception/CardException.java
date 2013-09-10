package jp.webpay.exception;

import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

public class CardException extends WebPayException {
    @Getter
    private final String type;
    @Getter
    private final String code;
    @Getter
    private final String param;

    public CardException(int status, @NonNull Map<String, String> errorInfo) {
        super(errorInfo.get("message"), status, errorInfo);
        this.type = errorInfo.get("type");
        this.code = errorInfo.get("code");
        this.param = errorInfo.get("param");
    }
}
