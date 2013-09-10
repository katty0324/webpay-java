package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ChargeTest {

    private static final WebPayClient NOT_USED_CLIENT = new WebPayClient("fake_key");

    @Test
    public void generateChargeFromJSON() {
        String json = "{\"id\":\"ch_2CmdiFbI60iRbCy\",\"object\":\"charge\",\"livemode\":false,\"currency\":\"jpy\",\"description\":\"Consectetur excepturi optio adipisci neque ullam eius illo sed.\",\"amount\":525,\"amount_refunded\":0,\"customer\":null,\"created\":1378335175,\"paid\":true,\"refunded\":false,\"failure_message\":null,\"card\":{\"object\":\"card\",\"exp_year\":2017,\"exp_month\":4,\"fingerprint\":\"032c9ed7343b5947cfcb28510bc3f32e0621cd74\",\"name\":\"DEE EMMERICH\",\"country\":\"JP\",\"type\":\"Visa\",\"cvc_check\":\"pass\",\"last4\":\"4242\"},\"captured\":true,\"expire_time\":null}";
        Charge decoded = Charge.fromJsonResponse(NOT_USED_CLIENT, json);

        assertThat(decoded.getId(), is("ch_2CmdiFbI60iRbCy"));
        assertThat(decoded.getLivemode(), is(false));
        assertThat(decoded.getAmount(), is(525L));
        assertThat(decoded.getExpireTime(), nullValue());

        Card card = decoded.getCard();
        assertThat(card.getName(), is("DEE EMMERICH"));
        assertThat(card.getExpMonth(), is(4));
    }

    @Test
    public void createdDateReturnsNullIfCreatedIsNotSet() {
        Charge charge = new Charge();
        charge.setCreated(null);
        assertThat(charge.createdDate(), nullValue());
    }

    @Test
    public void createdDateReturnsDateObject() {
        long timestamp = new Date().getTime() / 1000;
        Charge charge = new Charge();
        charge.setCreated(timestamp);
        assertThat(charge.createdDate().getTime(), is(timestamp * 1000));
    }

    @Test
    public void expireDateReturnsNullIfExpireTimeIsNotSet() {
        Charge charge = new Charge();
        charge.setExpireTime(null);
        assertThat(charge.expireDate(), nullValue());
    }

    @Test
    public void expireDateReturnsDateObject() {
        long timestamp = new Date().getTime() / 1000;
        Charge charge = new Charge();
        charge.setExpireTime(timestamp);
        assertThat(charge.expireDate().getTime(), is(timestamp * 1000));
    }
}
