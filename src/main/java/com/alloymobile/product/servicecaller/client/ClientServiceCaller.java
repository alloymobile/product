package com.alloymobile.product.servicecaller.client;

import com.alloymobile.product.servicecaller.client.model.Client;
import com.alloymobile.product.servicecaller.BaseServiceCaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ClientServiceCaller extends BaseServiceCaller {
    private final WebClient webClient;

    public ClientServiceCaller(final WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public Mono<Client> getClientById(final String clientId){
        return webClient.get().uri(productProperties.getClientServiceUrl()+"/clients/"+clientId)
                .header(HttpHeaders.AUTHORIZATION, this.productProperties.getToken())
                .retrieve()
                .bodyToMono(Client.class);
    }
}
