package jp.webpay.api;

import jp.webpay.model.Customer;
import jp.webpay.request.CardRequest;
import jp.webpay.request.CustomerRequest;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomersTest extends ApiTestFixture {
    @Test
    public void testCreateCustomer() throws Exception {
        stubFor(post("/v1/customers")
                .withRequestBody(containing("description=Test"))
                .withRequestBody(containing("email=customer"))
                .withRequestBody(containing("card%5Bname%5D=YUUKO+SHIONJI"))
                .willReturn(response("customers/create")));
        CardRequest card = new CardRequest()
                .number("4242-4242-4242-4242")
                .expMonth(12)
                .expYear(2015)
                .cvc(123)
                .name("YUUKO SHIONJI");
        CustomerRequest request = new CustomerRequest()
                .card(card)
                .description("Test Customer from Java")
                .email("customer@example.com");

        Customer customer = client.customers.create(request);

        assertThat(customer.getEmail(), is("customer@example.com"));
        assertThat(customer.getActiveCard().getName(), is("YUUKO SHIONJI"));
    }

    @Test
    public void testRetrieveCustomer() throws Exception {
        stubFor(get("/v1/customers/cus_39o4Fv82E1et5Xb")
                .willReturn(response("customers/retrieve")));

        Customer customer = client.customers.retrieve("cus_39o4Fv82E1et5Xb");
        assertThat(customer.getId(), is("cus_39o4Fv82E1et5Xb"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveCustomerWithoutId() throws Exception {
        client.customers.retrieve("");
    }
}
