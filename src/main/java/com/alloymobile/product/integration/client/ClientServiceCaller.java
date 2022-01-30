package com.alloymobile.product.integration.client;

import com.alloymobile.product.integration.client.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ClientServiceCaller {
    private final WebClient webClient;
    private final String clientUrl;
    public final ClientMapper clientMapper;

    public ClientServiceCaller(Environment env, final WebClient.Builder webclient,ClientMapper clientMapper) {
        this.clientUrl = env.getProperty("product.client-service-url");
        this.webClient = webclient.build();
        this.clientMapper = clientMapper;
    }

    public Mono<Client> getClientById(final String clientId, String token){
        return webClient.get().uri(this.clientUrl+"/clients/"+clientId)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(Client.class);
    }
}
