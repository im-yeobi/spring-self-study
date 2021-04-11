package io.yeobi.springwebclient.webclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class WebClientCreator {

    public WebClient createDefaultWebClient() {
        // default WebClient instantiation
        WebClient webClient = WebClient.create();

        return webClient;
    }

    public WebClient createUriBaseWebClient() {
        // base URI WebClient instantiation
        WebClient webClient = WebClient.create("http://localhost:8080");

        return webClient;
    }

    public WebClient createBuilderWebClient() {
        // DefaultWebClientBuilder
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient;
    }

    public WebClient createTimeoutConfiguredWebClient() {
        // timeout WebClient instantiation
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)     // 원격지 서버 커넥션 대기시간 설정
                .responseTimeout(Duration.ofMillis(5000))   // 응답 대기시간 설정
                .doOnConnected(conn ->  // 원격지 서버와 연결된 후에 설정
                        // read timeout : 클라이언트에서 서버의 응답을 받기 위해 대기하는 시간
                        // write timeout : 클라이언트에서 서버로 보낸 요청이 정상 송신되기까지 대기하는 시간
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS)) // 응답 대기시간 예외 발생
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))   // 요청 대기시간 예외 발생
                );
        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return webClient;
    }
}
