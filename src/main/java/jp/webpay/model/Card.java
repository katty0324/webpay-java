package jp.webpay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Card {
    private String object;
    private Integer expMonth;
    private Integer expYear;
    private String fingerprint;
    private String last4;
    private String type;
    private String cvcCheck;
    private String name;
}
