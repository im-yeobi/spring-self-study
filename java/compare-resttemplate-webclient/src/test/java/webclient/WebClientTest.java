package io.yeobi.compareresttemplatewebclient.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

public class WebClientTest {

    @Test
    public void callOriginServer() throws InterruptedException {
        WebClient webClient = WebClient.create();
        URI uri = URI.create("http://localhost:8080/health");

        System.out.println("[Server call] : start");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Mono<String> response = webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
        stopWatch.stop();

        response.subscribe(res -> System.out.println(res));
//        System.out.println("[status code] : " + response.() + ", [response body] : " + response.getBody());
        System.out.println("[Server call] : end");
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
