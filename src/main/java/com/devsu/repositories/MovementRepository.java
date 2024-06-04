package com.devsu.repositories;

import com.devsu.model.entities.Movement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {

}
