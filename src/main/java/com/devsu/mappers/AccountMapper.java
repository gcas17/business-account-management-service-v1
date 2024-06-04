package com.devsu.mappers;

import com.devsu.model.dto.ClientResponse;
import com.devsu.model.entities.Account;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.AccountResponse;
import org.openapitools.model.CreateAccountRequest;
import org.openapitools.model.ReplaceAccountRequest;
import org.openapitools.model.UpdateAccountRequest;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

  AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

  @Mapping(source = "accountNumber", target = "accountNumber")
  @Mapping(source = "accountType", target = "accountType")
  @Mapping(source = "initialBalance", target = "initialBalance")
  @Mapping(source = "status", target = "status")
  Account createAccountRequestToAccount(CreateAccountRequest createAccountRequest);

  @Mapping(source = "accountNumber", target = "accountNumber")
  @Mapping(source = "accountType", target = "accountType")
  @Mapping(source = "initialBalance", target = "initialBalance")
  @Mapping(source = "status", target = "status")
  AccountResponse accountToAccountResponse(Account account, @Context ClientResponse client);

  @AfterMapping
  default void setClientName(@MappingTarget AccountResponse accountResponse, @Context ClientResponse client) {
    if (client != null) {
      accountResponse.setClientName(client.getName());
    }
  }

  @Mapping(source = "accountNumber", target = "accountNumber")
  @Mapping(source = "accountType", target = "accountType")
  @Mapping(source = "initialBalance", target = "initialBalance")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "clientId", target = "clientId")
  void updateAccountRequestToAccount(UpdateAccountRequest updateAccountRequest, @MappingTarget Account existingAccount);

  @Mapping(source = "accountNumber", target = "accountNumber")
  @Mapping(source = "accountType", target = "accountType")
  @Mapping(source = "initialBalance", target = "initialBalance")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "clientId", target = "clientId")
  Account replaceAccountRequestToAccount(ReplaceAccountRequest replaceAccountRequest);

}
