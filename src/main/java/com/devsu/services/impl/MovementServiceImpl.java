package com.devsu.services.impl;

import com.devsu.mappers.MovementMapper;
import com.devsu.model.entities.Account;
import com.devsu.model.entities.Movement;
import com.devsu.repositories.AccountRepository;
import com.devsu.repositories.MovementRepository;
import com.devsu.services.MovementService;
import lombok.AllArgsConstructor;
import org.openapitools.model.CreateMovementRequest;
import org.openapitools.model.MovementResponse;
import org.openapitools.model.ReplaceMovementRequest;
import org.openapitools.model.UpdateMovementRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.math.BigDecimal;
import java.util.function.Predicate;

import static com.devsu.utilities.enums.ApiException.*;

@Service
@AllArgsConstructor
public class MovementServiceImpl implements MovementService {

  private final MovementRepository movementRepository;
  private final AccountRepository accountRepository;

  @Override
  public Mono<MovementResponse> createMovement(Mono<CreateMovementRequest> createMovementRequest) {
    return createMovementRequest
        .flatMap(request -> {
          if (!validateCreateMovementRequest().test(request)) {
            return Mono.error(ACC0008.getException());
          }
          Movement movement = MovementMapper.INSTANCE.createMovementRequestToMovement(request);
          return accountRepository.findById(request.getAccountId())
              .switchIfEmpty(Mono.error(ACC0001.getException()))
              .map(account -> Tuples.of(account, movement));
        })
        .flatMap(accountMovementPair -> {
          Account account = accountMovementPair.getT1();
          Movement movement = accountMovementPair.getT2();
          return movementRepository.findFirstByAccountIdOrderByIdDesc(account.getId())
              .flatMap(lastMovement -> {
                BigDecimal newBalance = lastMovement.getAvailableBalance().add(movement.getValue());
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                  return Mono.error(ACC0010.getException());
                }
                movement.setAvailableBalance(newBalance);
                return Mono.just(Tuples.of(account, movement));
              })
              .defaultIfEmpty(Tuples.of(account, movement));
        })
        .flatMap(accountMovementPair -> {
          Account account = accountMovementPair.getT1();
          Movement movement = accountMovementPair.getT2();
          if (movement.getAvailableBalance() == null) {
            BigDecimal newBalance = account.getInitialBalance().add(movement.getValue());
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
              return Mono.error(ACC0010.getException());
            }
            movement.setAvailableBalance(newBalance);
          }
          return movementRepository.save(movement)
              .flatMap(savedMovement -> Mono.just(MovementMapper.INSTANCE.movementToMovementResponse(savedMovement, account)));
        });
  }

  @Override
  public Mono<MovementResponse> replaceMovement(Mono<ReplaceMovementRequest> replaceMovementRequest) {
    return replaceMovementRequest
        .flatMap(request -> {
          if (!validateReplaceMovementRequest().test(request)) {
            return Mono.error(ACC0007.getException());
          }
          return movementRepository.findById(request.getId())
              .switchIfEmpty(Mono.defer(() -> Mono.error(ACC0009.getException())))
              .flatMap(existingMovement -> accountRepository.findById(request.getAccountId())
                  .switchIfEmpty(Mono.defer(() -> Mono.error(ACC0001.getException())))
                  .flatMap(account -> {
                    Movement updatedMovement = MovementMapper.INSTANCE.replaceMovementRequestToMovement(request);
                    updatedMovement.setId(existingMovement.getId());
                    return movementRepository.save(updatedMovement);
                  })
                  .flatMap(updatedMovement -> accountRepository.findById(updatedMovement.getAccountId())
                      .switchIfEmpty(Mono.error(ACC0001.getException()))
                      .map(account -> MovementMapper.INSTANCE.movementToMovementResponse(updatedMovement, account))));
        });
  }

  @Override
  public Mono<MovementResponse> updateMovement(Mono<UpdateMovementRequest> updateMovementRequest) {
    return updateMovementRequest
        .flatMap(request -> {
          if (!validateUpdateMovementRequest().test(request)) {
            return Mono.error(ACC0006.getException());
          }
          return movementRepository.findById(request.getId())
              .switchIfEmpty(Mono.defer(() -> Mono.error(ACC0009.getException())))
              .flatMap(existingMovement -> {
                MovementMapper.INSTANCE.updateMovementRequestToMovement(request, existingMovement);
                if (existingMovement.getAccountId() != null) {
                  return accountRepository.findById(existingMovement.getAccountId())
                      .switchIfEmpty(Mono.defer(() -> Mono.error(ACC0001.getException())))
                      .then(movementRepository.save(existingMovement));
                }
                return movementRepository.save(existingMovement);
              })
              .flatMap(updatedMovement -> Mono.justOrEmpty(updatedMovement.getAccountId())
                  .flatMap(accountRepository::findById)
                  .switchIfEmpty(Mono.error(ACC0001.getException()))
                  .map(account -> MovementMapper.INSTANCE.movementToMovementResponse(updatedMovement, account))
                  .defaultIfEmpty(MovementMapper.INSTANCE.movementToMovementResponse(updatedMovement, null)));
        });
  }

  @Override
  public Flux<MovementResponse> listMovements() {
    return movementRepository.findAll()
        .flatMap(movement -> accountRepository.findById(movement.getAccountId())
            .switchIfEmpty(Mono.error(ACC0001.getException()))
            .map(account -> MovementMapper.INSTANCE.movementToMovementResponse(movement, account)));
  }

  @Override
  public Mono<MovementResponse> getMovement(Long id) {
    return movementRepository.findById(id)
        .flatMap(movement -> accountRepository.findById(movement.getAccountId())
            .switchIfEmpty(Mono.error(ACC0001.getException()))
            .map(account -> MovementMapper.INSTANCE.movementToMovementResponse(movement, account)));
  }

  @Override
  public Mono<Void> deleteMovement(Long id) {
    return movementRepository.findById(id)
        .switchIfEmpty(Mono.defer(() -> Mono.error(ACC0009.getException())))
        .flatMap(movementRepository::delete);
  }

  private Predicate<UpdateMovementRequest> validateUpdateMovementRequest() {
    return updateClientRequest -> updateClientRequest.getId() != null;
  }

  private Predicate<ReplaceMovementRequest> validateReplaceMovementRequest() {
    return replaceClientRequest -> replaceClientRequest.getId() != null &&
        replaceClientRequest.getProcessDate() != null &&
        replaceClientRequest.getMovementType() != null &&
        replaceClientRequest.getAmount() != null &&
        replaceClientRequest.getAccountId() != null;
  }

  private Predicate<CreateMovementRequest> validateCreateMovementRequest() {
    return createClientRequest -> createClientRequest.getProcessDate() != null &&
        createClientRequest.getMovementType() != null &&
        createClientRequest.getAmount() != null &&
        createClientRequest.getAccountId() != null;
  }

}
