package jp.webpay.model;

import jp.webpay.api.WebPayClient;

import java.util.Date;

abstract class AbstractModel {
    protected WebPayClient client;

    protected Date timestampToDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp * 1000);
    }
}
