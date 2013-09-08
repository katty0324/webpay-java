package jp.webpay.exception;

import lombok.Getter;

public class ApiConnectionException extends WebPayException {
    @Getter
    private final Throwable cause;

    public ApiConnectionException(String message) {
        super(message);
        cause = null;
    }

    public ApiConnectionException(Throwable cause) {
        super("ApiConnectionException caused by " + cause.getMessage());
        this.cause = cause;
    }

    public static ApiConnectionException jsonException(String json) {
        return new ApiConnectionException("Response JSON is broken. Please check connection status and retry.\n" + json);
    }
}
