package com.devsu.controllers;

import com.devsu.services.MovementService;
import lombok.AllArgsConstructor;
import org.openapitools.api.MovementApi;
import org.openapitools.model.CreateMovementRequest;
import org.openapitools.model.MovementResponse;
import org.openapitools.model.ReplaceMovementRequest;
import org.openapitools.model.UpdateMovementRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class MovementController implements MovementApi {

  private final MovementService movementService;

  @Override
  public Mono<ResponseEntity<MovementResponse>> createMovement(Mono<CreateMovementRequest> createMovementRequest, ServerWebExchange exchange) {
    return movementService.createMovement(createMovementRequest)
        .map(movementResponse -> ResponseEntity.ok().body(movementResponse))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteMovement(Long id, ServerWebExchange exchange) {
    return movementService.deleteMovement(id)
        .then(Mono.just(new ResponseEntity<>(HttpStatus.OK)));
  }

  @Override
  public Mono<ResponseEntity<MovementResponse>> getMovement(Long id, ServerWebExchange exchange) {
    return movementService.getMovement(id)
        .map(movementResponse -> ResponseEntity.ok().body(movementResponse))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @Override
  public Mono<ResponseEntity<Flux<MovementResponse>>> listMovements(ServerWebExchange exchange) {
    return movementService
        .listMovements()
        .collectList()
        .map(movementList -> ResponseEntity.ok().body(Flux.fromIterable(movementList)))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @Override
  public Mono<ResponseEntity<MovementResponse>> replaceMovement(Mono<ReplaceMovementRequest> replaceMovementRequest, ServerWebExchange exchange) {
    return movementService.replaceMovement(replaceMovementRequest)
        .map(movementResponse -> ResponseEntity.ok().body(movementResponse))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @Override
  public Mono<ResponseEntity<MovementResponse>> updateMovement(Mono<UpdateMovementRequest> updateMovementRequest, ServerWebExchange exchange) {
    return movementService.updateMovement(updateMovementRequest)
        .map(movementResponse -> ResponseEntity.ok().body(movementResponse))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

}
