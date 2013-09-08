package jp.webpay.exception;

import java.util.Map;

public class AuthenticationException extends WebPayException {
    public AuthenticationException(int status, Map<String, String> errorInfo) {
        super(errorInfo.get("message"), status, errorInfo);
    }
}
