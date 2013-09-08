package jp.webpay.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EntityList<T extends AbstractModel> {
    private String object;
    private String url;
    private Integer count;

    // To set data by JSONIC, each model's list class should define setter
    @Setter(AccessLevel.NONE)
    protected List<T> data;
}
