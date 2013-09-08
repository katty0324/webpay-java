package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import lombok.ToString;
import net.arnx.jsonic.JSON;

import java.util.List;

@ToString
public class ChargeList extends EntityList<Charge> {
    public void setData(List<Charge> data) {
        this.data = data;
    }

    public static ChargeList fromJsonResponse(WebPayClient client, String json) {
        ChargeList list = JSON.decode(json, ChargeList.class);
        for (Charge row : list.getData()) {
            row.client = client;
        }
        return list;
    }
}
