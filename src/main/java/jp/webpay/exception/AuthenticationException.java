package jp.webpay.exception;

import lombok.NonNull;

import java.util.Map;

public class AuthenticationException extends WebPayException {
    AuthenticationException(int status, @NonNull Map<String, String> errorInfo) {
        super(errorInfo.get("message"), status, errorInfo);
    }
}
