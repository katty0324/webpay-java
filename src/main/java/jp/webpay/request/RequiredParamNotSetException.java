package jp.webpay.request;

public class RequiredParamNotSetException extends RuntimeException {
    RequiredParamNotSetException(String param) {
        super("Required parameter " + param + " is not set");
    }
}
