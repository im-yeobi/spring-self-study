package io.yeobi.compareresttemplatewebclient.resttemplate;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class RestTemplateTest {

    @Test
    public void callOriginServer() {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create("http://localhost:8080/health");

        System.out.println("[Server call] : start");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        stopWatch.stop();

        System.out.println("[Status code] : " + response.getStatusCode() + ", [Response body] : " + response.getBody());
        System.out.println("[Server call] : end");
        System.out.println("[Time] : " + stopWatch.getTotalTimeMillis() + " ms");
    }
}
