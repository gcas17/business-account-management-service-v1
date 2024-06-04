package com.devsu.clients;

import com.devsu.config.ApplicationProperties;
import com.devsu.model.dto.ClientResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ClientServiceClient {

  private final ApplicationProperties applicationProperties;
  private final WebClient webClient;

  @Autowired
  public ClientServiceClient(ApplicationProperties applicationProperties, WebClient.Builder webClientBuilder) {
    this.applicationProperties = applicationProperties;
    this.webClient = webClientBuilder.baseUrl(applicationProperties.getClientServiceUrl()).build();
  }

  public Mono<ClientResponse> getClientById(Long id) {
    return webClient.get()
      .uri("/clientes/{id}", id)
      .retrieve()
      .bodyToMono(ClientResponse.class);
  }
}
