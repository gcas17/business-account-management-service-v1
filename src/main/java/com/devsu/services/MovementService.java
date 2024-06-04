package com.devsu.services;

import org.openapitools.model.CreateMovementRequest;
import org.openapitools.model.MovementResponse;
import org.openapitools.model.ReplaceMovementRequest;
import org.openapitools.model.UpdateMovementRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {

  Mono<MovementResponse> createMovement(Mono<CreateMovementRequest> createMovementRequest);

  Mono<Void> deleteMovement(Long id);

  Mono<MovementResponse> getMovement(Long id);

  Mono<MovementResponse> replaceMovement(Mono<ReplaceMovementRequest> replaceMovementRequest);

  Mono<MovementResponse> updateMovement(Mono<UpdateMovementRequest> updateMovementRequest);

  Flux<MovementResponse> listMovements();
  
}
