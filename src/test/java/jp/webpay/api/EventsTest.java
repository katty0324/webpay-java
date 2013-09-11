package jp.webpay.api;

import jp.webpay.model.EventList;
import jp.webpay.request.ListRequest;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventsTest extends ApiTestFixture {
    @Test
    public void testAllEvent() throws Exception {
        stubFor(get("/v1/events?count=5&offset=0&type=*.created")
                .willReturn(response("events/all_with_type")));

        EventList events = client.events.all(new ListRequest().count(5), "*.created");

        assertThat(events.getCount(), is(5));
        assertThat(events.getUrl(), is("/v1/events"));
        assertThat(events.getData().size(), is(5));
        assertThat(events.getData().get(0).getId(), is("evt_39o9oUevb5NCeM1"));
        assertThat((String)events.getData().get(0).getData().getObject().get("id"),
                is("cus_39o9oU1N1dRm4LZ"));
    }
}
