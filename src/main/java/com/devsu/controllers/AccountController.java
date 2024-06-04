package com.devsu.controllers;

import com.devsu.services.AccountService;
import lombok.AllArgsConstructor;
import org.openapitools.api.AccountApi;
import org.openapitools.model.AccountResponse;
import org.openapitools.model.CreateAccountRequest;
import org.openapitools.model.ReplaceAccountRequest;
import org.openapitools.model.UpdateAccountRequest;
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
public class AccountController implements AccountApi {

  private final AccountService accountService;

  @Override
  public Mono<ResponseEntity<AccountResponse>> createAccount(Mono<CreateAccountRequest> createAccountRequest, ServerWebExchange exchange) {
    return accountService.createAccount(createAccountRequest)
        .map(accountResponse -> ResponseEntity.ok().body(accountResponse))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteAccount(Long id, ServerWebExchange exchange) {
    return accountService.deleteAccount(id)
        .then(Mono.just(new ResponseEntity<>(HttpStatus.OK)));
  }

  @Override
  public Mono<ResponseEntity<AccountResponse>> getAccount(Long id, ServerWebExchange exchange) {
    return accountService.getAccount(id)
        .map(accountResponse -> ResponseEntity.ok().body(accountResponse))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @Override
  public Mono<ResponseEntity<Flux<AccountResponse>>> listAccounts(ServerWebExchange exchange) {
    return accountService
        .listAccounts()
        .collectList()
        .map(accountList -> ResponseEntity.ok().body(Flux.fromIterable(accountList)))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @Override
  public Mono<ResponseEntity<AccountResponse>> replaceAccount(Mono<ReplaceAccountRequest> replaceAccountRequest, ServerWebExchange exchange) {
    return accountService.replaceAccount(replaceAccountRequest)
        .map(accountResponse -> ResponseEntity.ok().body(accountResponse))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

  @Override
  public Mono<ResponseEntity<AccountResponse>> updateAccount(Mono<UpdateAccountRequest> updateAccountRequest, ServerWebExchange exchange) {
    return accountService.updateAccount(updateAccountRequest)
        .map(accountResponse -> ResponseEntity.ok().body(accountResponse))
        .defaultIfEmpty(ResponseEntity.noContent().build());
  }

}
