package com.devsu.services.impl;

import com.devsu.clients.ClientServiceClient;
import com.devsu.mappers.AccountMapper;
import com.devsu.model.entities.Account;
import com.devsu.repositories.AccountRepository;
import com.devsu.services.AccountService;
import lombok.AllArgsConstructor;
import org.openapitools.model.AccountResponse;
import org.openapitools.model.CreateAccountRequest;
import org.openapitools.model.ReplaceAccountRequest;
import org.openapitools.model.UpdateAccountRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

import static com.devsu.utilities.enums.ApiException.*;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final ClientServiceClient clientServiceClient;
  private final AccountRepository accountRepository;

  @Override
  public Mono<AccountResponse> createAccount(Mono<CreateAccountRequest> createAccountRequest) {
    return createAccountRequest
        .flatMap(request -> {
          if (!validateCreateAccountRequest().test(request)) {
            return Mono.error(ACC0005.getException());
          }
          return clientServiceClient.getClientById(request.getClientId())
              .switchIfEmpty(Mono.error(ACC0002.getException()))
              .flatMap(client -> {
                Account account = AccountMapper.INSTANCE.createAccountRequestToAccount(request);
                account.setClientId(client.getId());
                return accountRepository.save(account)
                    .map(savedAccount -> AccountMapper.INSTANCE.accountToAccountResponse(savedAccount, client));
              });
        });
  }

  @Override
  public Mono<Void> deleteAccount(Long id) {
    return accountRepository.findById(id)
        .switchIfEmpty(Mono.error(ACC0001.getException()))
        .flatMap(account -> accountRepository.deleteById(id));
  }

  @Override
  public Mono<AccountResponse> getAccount(Long id) {
    return accountRepository.findById(id)
        .flatMap(account -> clientServiceClient.getClientById(account.getClientId())
            .map(client -> AccountMapper.INSTANCE.accountToAccountResponse(account, client)));
  }

  @Override
  public Mono<AccountResponse> updateAccount(Mono<UpdateAccountRequest> updateAccountRequest) {
    return updateAccountRequest
        .flatMap(request -> {
          if (!validateUpdateAccountRequest().test(request)) {
            return Mono.error(ACC0003.getException());
          }
          return accountRepository.findById(request.getId())
              .switchIfEmpty(Mono.error(ACC0001.getException()))
              .flatMap(existingAccount -> {
                Account updatedAccount = AccountMapper.INSTANCE.updateAccountRequestToAccount(request);
                updatedAccount.setId(existingAccount.getId());
                updatedAccount.setClientId(existingAccount.getClientId());
                return accountRepository.save(updatedAccount);
              })
              .flatMap(updatedAccount -> clientServiceClient.getClientById(updatedAccount.getClientId())
                  .map(client -> AccountMapper.INSTANCE.accountToAccountResponse(updatedAccount, client)));
        });
  }

  @Override
  public Mono<AccountResponse> replaceAccount(Mono<ReplaceAccountRequest> replaceAccountRequest) {
    return replaceAccountRequest
        .flatMap(request -> {
          if (!validateReplaceAccountRequest().test(request)) {
            return Mono.error(ACC0004.getException());
          }
          return accountRepository.findById(request.getId())
              .switchIfEmpty(Mono.error(ACC0001.getException()))
              .flatMap(existingAccount -> {
                Account updatedAccount = AccountMapper.INSTANCE.replaceAccountRequestToAccount(request);
                updatedAccount.setId(existingAccount.getId());
                updatedAccount.setClientId(existingAccount.getClientId());
                return accountRepository.save(updatedAccount);
              })
              .flatMap(updatedAccount -> clientServiceClient.getClientById(updatedAccount.getClientId())
                  .map(client -> AccountMapper.INSTANCE.accountToAccountResponse(updatedAccount, client)));
        });
  }

  @Override
  public Flux<AccountResponse> listAccounts() {
    return accountRepository.findAll()
        .flatMap(account -> clientServiceClient.getClientById(account.getClientId())
            .map(client -> AccountMapper.INSTANCE.accountToAccountResponse(account, client)));
  }

  private Predicate<UpdateAccountRequest> validateUpdateAccountRequest() {
    return updateAccountRequest -> updateAccountRequest.getId() != null;
  }

  private Predicate<ReplaceAccountRequest> validateReplaceAccountRequest() {
    return replaceAccountRequest -> replaceAccountRequest.getId() != null &&
        replaceAccountRequest.getAccountInformation() != null &&
        replaceAccountRequest.getAccountInformation().getStatus() != null &&
        replaceAccountRequest.getAccountInformation().getType() != null &&
        replaceAccountRequest.getAccountInformation().getNumber() != null &&
        replaceAccountRequest.getAccountInformation().getInitialBalance() != null &&
        replaceAccountRequest.getClientId() != null;
  }

  private Predicate<CreateAccountRequest> validateCreateAccountRequest() {
    return createAccountRequest -> createAccountRequest.getAccountInformation() != null &&
        createAccountRequest.getAccountInformation().getStatus() != null &&
        createAccountRequest.getAccountInformation().getType() != null &&
        createAccountRequest.getAccountInformation().getNumber() != null &&
        createAccountRequest.getAccountInformation().getInitialBalance() != null &&
        createAccountRequest.getClientId() != null;
  }

}
