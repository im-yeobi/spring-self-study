package resttemplate;

import io.yeobi.compareresttemplatewebclient.resttemplate.RestTemplateClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootTest(classes = {RestTemplateClient.class})
public class RestTemplateTest {

    @Test
    public void callOriginServerUsingRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create("http://localhost:8080/health");

        System.out.println("[Server call] : start");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        System.out.println("[First request]");
        ResponseEntity<String> firstResponse = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        System.out.println("[First response body] : " + firstResponse.getBody());

        System.out.println("[Second request]");
        ResponseEntity<String> secondResponse = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        stopWatch.stop();
        System.out.println("[Second response body] : " + secondResponse.getBody());
        System.out.println("[Time] : " + stopWatch.getTotalTimeMillis() + " ms");

        System.out.println("[Server call] : end");  // Blocking 처리로 요청 결과 수신 후 맨마지막에 실행된다.
    }
}
