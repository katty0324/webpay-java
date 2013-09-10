package jp.webpay.request;

import javax.ws.rs.core.Form;

public class CustomerRequest implements RequestEntity {
    private CardRequest card;
    private String email;
    private String description;

    public CustomerRequest card(CardRequest card) {
        this.card = card;
        return this;
    }

    public CustomerRequest email(String email) {
        this.email = email;
        return this;
    }

    public CustomerRequest description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Form toForm() {
        Form form = new Form();
        if (card != null) {
            card.addParams(form);
        }
        if (email != null && !email.isEmpty()) {
            form.param("email", email);
        }
        if (description != null && !description.isEmpty()) {
            form.param("description", description);
        }
        return form;
    }
}
