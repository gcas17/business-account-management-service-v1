package com.devsu.repositories;

import com.devsu.model.entities.Movement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {

  Mono<Movement> findFirstByAccountIdOrderByIdDesc(Long accountId);

  Flux<Movement> findByAccountIdAndDateBetweenOrderByIdDesc(Long accountId, LocalDate from, LocalDate to);


}
