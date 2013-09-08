package jp.webpay.api;

import jp.webpay.model.Charge;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class WebPayClient {
    public static final String DEFAULT_BASE = "https://api.webpay.jp/v1";

    private final String apiKey;
    private final String apiBase;

    WebPayClient(String apiKey) {
        this(apiKey, DEFAULT_BASE);
    }

    WebPayClient(String apiKey, String apiBase) {
        this.apiKey = apiKey;
        this.apiBase = apiBase;
    }

    public Charge createCharge(Form form) {
        SSLContext ssl = SslConfigurator.newInstance().createSSLContext();
        Client client = ClientBuilder.newBuilder().sslContext(ssl).build();
        client.register(new HttpBasicAuthFilter(apiKey, ""));

        WebTarget target = client.target(apiBase).path("/charges");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.form(form));

        int status = response.getStatus();
        if (status >= 200 && status < 300)  {
            return Charge.fromJsonResponse(response.readEntity(String.class));
        } else {
            throw new RuntimeException("Request failed: " + status + "\n" + response.readEntity(String.class));
        }
    }
}
