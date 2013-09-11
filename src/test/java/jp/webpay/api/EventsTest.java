package jp.webpay.api;

import jp.webpay.model.Event;
import jp.webpay.model.EventList;
import jp.webpay.request.ListRequest;
import org.junit.Test;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.instanceOf;
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

    @Test
    public void testAllCustomersWithoutParams() throws Exception {
        String path = "/v1/events?count=10&offset=0";
        stubFor(get(path).willReturn(response("events/all_with_type")));
        client.events.all();
        verify(getRequestedFor(urlEqualTo(path)));
    }

    @Test
    public void testAllCustomersWithType() throws Exception {
        String path = "/v1/events?count=10&offset=0&type=*.created";
        stubFor(get(path).willReturn(response("events/all_with_type")));
        client.events.all("*.created");
        verify(getRequestedFor(urlEqualTo(path)));
    }

    @Test
    public void testRetrieveEvent() throws Exception {
        stubFor(get("/v1/events/evt_39o9oUevb5NCeM1")
                .willReturn(response("events/retrieve")));

        Event event = client.events.retrieve("evt_39o9oUevb5NCeM1");

        assertThat(event.getId(), is("evt_39o9oUevb5NCeM1"));
        assertThat(event.getPendingWebhooks(), is(0));
        assertThat((String)event.getData().getObject().get("id"),
                is("cus_39o9oU1N1dRm4LZ"));
        assertThat(event.getData().getObject().get("active_card"), instanceOf(Map.class));
    }
}
