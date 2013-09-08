package jp.webpay.request;

import javax.ws.rs.core.Form;

public interface RequestEntity {
    public Form toForm();
}
