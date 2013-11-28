package jp.webpay.request;

import javax.ws.rs.core.Form;

public class TokenRequest implements RequestEntity {
    private String card;

    public TokenRequest card(String card) {
        this.card = card;
        return this;
    }

    public void addParams(Form form) {
        form.param("card", card);
    }

    @Override
    public Form toForm() {
        Form form = new Form();
        addParams(form);
        return form;
    }
}
