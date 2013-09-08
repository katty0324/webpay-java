package jp.webpay.request;

import javax.ws.rs.core.Form;

public class ListRequest implements RequestEntity {
    private int count = 10, offset = 0;
    private Integer createdGt, createdGte, createdLt, createdLte;

    public ListRequest count(int i) {
        count = i;
        return this;
    }

    public ListRequest offset(int i) {
        offset = i;
        return this;
    }

    public ListRequest createdGt(Integer i) {
        createdGt = i;
        return this;
    }

    public ListRequest createdGte(Integer i) {
        createdGte = i;
        return this;
    }

    public ListRequest createdLt(Integer i) {
        createdLt = i;
        return this;
    }

    public ListRequest createdLte(Integer i) {
        createdLte = i;
        return this;
    }

    @Override
    public Form toForm() {
        Form form = new Form();
        form.param("count", String.valueOf(count));
        form.param("offset", String.valueOf(offset));
        if (createdGt != null) {
            form.param("created[gt]", createdGt.toString());
        }
        if (createdGte != null) {
            form.param("created[gte]", createdGte.toString());
        }
        if (createdLt != null) {
            form.param("created[lt]", createdLt.toString());
        }
        if (createdLte != null) {
            form.param("created[lte]", createdLte.toString());
        }
        return form;
    }
}
