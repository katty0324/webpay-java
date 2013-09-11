package jp.webpay.api;

abstract class Accessor {
    protected final WebPayClient client;

    protected Accessor(WebPayClient client) {
        this.client = client;
    }

    protected void assertId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("The given ID is empty");
        }
    }
}
