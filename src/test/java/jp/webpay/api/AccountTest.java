package jp.webpay.api;

import org.junit.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class AccountTest extends ApiTestFixture {
    @Test
    public void testRetrieveAccount() throws Exception {
        stubFor(get("/v1/account")
                .willReturn(response("account/retrieve")));

        jp.webpay.model.Account account = client.account.retrieve();
        assertThat(account.getId(), is("acct_2Cmdexb7J2r78rz"));
        assertThat(account.isChargeEnabled(), is(false));
        assertThat(account.isDetailsSubmitted(), is(false));
        assertThat(account.getEmail(), is("test-me@example.com"));
        assertThat(account.getStatementDescriptor(), nullValue());

        List<String> list = account.getCurrenciesSupported();
        assertThat(list.size(), is(1));
        assertThat(list.get(0), is("jpy"));
    }

    @Test
    public void testDeleteAllData() throws Exception {
        stubFor(delete("/v1/account/data")
                .willReturn(response("account/delete")));
        assertThat(client.account.deleteData(), is(true));
    }
}
