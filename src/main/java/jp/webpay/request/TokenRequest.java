package jp.webpay.request;

import javax.ws.rs.core.Form;

public class TokenRequest implements RequestEntity {
    private String card;

    public TokenRequest card(String card) {
        this.card = card;
        return this;
    }

    @Override
    public Form toForm() {
        Form form = new Form();
        form.param("card", card);
        return form;
    }
}
