package jp.webpay.api;

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
    private final Client client;

    public final Charges charges;

    WebPayClient(String apiKey) {
        this(apiKey, DEFAULT_BASE);
    }

    WebPayClient(String apiKey, String apiBase) {
        this.apiKey = apiKey;
        this.apiBase = apiBase;

        SSLContext ssl = SslConfigurator.newInstance().createSSLContext();
        client = ClientBuilder.newBuilder().sslContext(ssl).build();
        client.register(new HttpBasicAuthFilter(apiKey, ""));

        charges = new Charges(this);
    }

    String post(String path, Form form) {
        WebTarget target = client.target(apiBase).path(path);
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.form(form));
        return processErrorResponse(response);
    }

    private String processErrorResponse(Response response) {
        int status = response.getStatus();
        if (status >= 200 && status < 300)  {
            return response.readEntity(String.class);
        } else {
            throw new RuntimeException("Request failed: " + status + "\n" + response.readEntity(String.class));
        }
    }
}
