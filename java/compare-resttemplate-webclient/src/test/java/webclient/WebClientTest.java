package webclient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@SpringBootTest(classes = {WebClient.class})
public class WebClientTest {

    @Test
    public void callOriginServerUsingWebClient() {
        WebClient webClient = WebClient.create();
        URI uri = URI.create("http://localhost:8080/health");

        System.out.println("[Server call] : start");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        System.out.println("[First request]");
        Mono<String> firstResponse = webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);

        System.out.println("[Second request]");
        Mono<String> secondResponse = webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);

        firstResponse.subscribe(res -> {    // 첫 번째 요청 결과 수신 후 처리
            System.out.println("[First response body] : " + res);
        });
        secondResponse.subscribe(res -> {   // 두 번째 요청 결과 수신 후 처리
            stopWatch.stop();
            System.out.println("[Second response body] : " + res);
            System.out.println("[Time] : " + stopWatch.getTotalTimeMillis() + " ms");
        });

        System.out.println("[Server call] : end");  // Non-Blocking 처리로 요청 결과를 수신하기 전에 먼저 실행된다.
        secondResponse.block(); // 두번째 응답 수신 대기
    }
}
