package io.yeobi.springwebclient.webclient;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;

@Service
public class RequestPrepareService {

    public UriSpec<RequestBodySpec> prepareClientMethod(WebClient webClient) {
        UriSpec<RequestBodySpec> uriSpec = webClient.method(HttpMethod.POST);

        return uriSpec;
    }

    public UriSpec<RequestBodySpec> preparePost(WebClient webClient) {
        UriSpec<RequestBodySpec> uriSpec = webClient.post();

        return uriSpec
    }
}
