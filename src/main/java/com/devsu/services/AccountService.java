package com.devsu.services;

import org.openapitools.model.AccountResponse;
import org.openapitools.model.CreateAccountRequest;
import org.openapitools.model.ReplaceAccountRequest;
import org.openapitools.model.UpdateAccountRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

  Mono<AccountResponse> createAccount(Mono<CreateAccountRequest> createClientRequest);

  Mono<Void> deleteAccount(Long id);

  Mono<AccountResponse> getAccount(Long id);

  Mono<AccountResponse> replaceAccount(Mono<ReplaceAccountRequest> replaceClientRequest);

  Mono<AccountResponse> updateAccount(Mono<UpdateAccountRequest> updateClientRequest);

  Flux<AccountResponse> listAccounts();

}
