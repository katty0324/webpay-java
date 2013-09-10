package jp.webpay.model;

import jp.webpay.api.WebPayClient;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomerTest {
    private static final WebPayClient NOT_USED_CLIENT = new WebPayClient("fake_key");

    @Test
    public void testFromJsonResponse() throws Exception {
        String json = "{\n" +
                "  \"id\": \"cus_39o4Fv82E1et5Xb\",\n" +
                "  \"object\": \"customer\",\n" +
                "  \"livemode\": false,\n" +
                "  \"created\": 1378819398,\n" +
                "  \"email\": \"customer@example.com\",\n" +
                "  \"description\": \"Test Customer from Java\",\n" +
                "  \"active_card\": {\n" +
                "    \"object\": \"card\",\n" +
                "    \"exp_year\": 2015,\n" +
                "    \"exp_month\": 12,\n" +
                "    \"fingerprint\": \"215b5b2fe460809b8bb90bae6eeac0e0e0987bd7\",\n" +
                "    \"name\": \"YUUKO SHIONJI\",\n" +
                "    \"country\": \"JP\",\n" +
                "    \"type\": \"Visa\",\n" +
                "    \"cvc_check\": \"pass\",\n" +
                "    \"last4\": \"4242\"\n" +
                "  }\n" +
                "}";
        Customer customer = Customer.fromJsonResponse(NOT_USED_CLIENT, json);

        assertThat(customer.getId(), is("cus_39o4Fv82E1et5Xb"));
        assertThat(customer.getActiveCard().getFingerprint(), is("215b5b2fe460809b8bb90bae6eeac0e0e0987bd7"));
        assertThat(customer.getEmail(), is("customer@example.com"));
    }
}
