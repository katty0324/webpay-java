package jp.webpay.request;

import lombok.NonNull;

import javax.ws.rs.core.Form;

public class CardRequest implements RequestEntity {
    private String number, name;
    private Integer expMonth, expYear, cvc;

    public CardRequest number(@NonNull String number) {
        this.number = number;
        return this;
    }

    public CardRequest name(@NonNull String name) {
        this.name = name;
        return this;
    }

    public CardRequest expMonth(int expMonth) {
        this.expMonth = expMonth;
        return this;
    }

    public CardRequest expYear(int expYear) {
        this.expYear = expYear;
        return this;
    }

    public CardRequest cvc(int cvc) {
        this.cvc = cvc;
        return this;
    }

    public void addParams(Form form) {
        form.param("card[number]", number);
        form.param("card[exp_month]", expMonth.toString());
        form.param("card[exp_year]", expYear.toString());
        form.param("card[cvc]", cvc.toString());
        form.param("card[name]", name);
    }

    @Override
    public Form toForm() {
        Form form = new Form();
        addParams(form);
        return form;
    }
}
