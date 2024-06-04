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

  @Mapping(source = "accountInformation.number", target = "accountNumber")
  @Mapping(source = "accountInformation.type", target = "accountType")
  @Mapping(source = "accountInformation.initialBalance", target = "initialBalance")
  @Mapping(source = "accountInformation.status", target = "status")
  Account createAccountRequestToAccount(CreateAccountRequest createAccountRequest);

  @Mapping(source = "accountNumber", target = "accountInformation.number")
  @Mapping(source = "accountType", target = "accountInformation.type")
  @Mapping(source = "initialBalance", target = "accountInformation.initialBalance")
  @Mapping(source = "status", target = "accountInformation.status")
  AccountResponse accountToAccountResponse(Account account, @Context ClientResponse client);

  @AfterMapping
  default void setClientName(@MappingTarget AccountResponse accountResponse, @Context ClientResponse client) {
    if (client != null) {
      accountResponse.setClientName(client.getName());
    }
  }

  @Mapping(source = "id", target = "id")
  @Mapping(source = "accountInformation.number", target = "accountNumber")
  @Mapping(source = "accountInformation.type", target = "accountType")
  @Mapping(source = "accountInformation.initialBalance", target = "initialBalance")
  @Mapping(source = "accountInformation.status", target = "status")
  Account updateAccountRequestToAccount(UpdateAccountRequest updateAccountRequest);


  @Mapping(source = "id", target = "id")
  @Mapping(source = "accountInformation.number", target = "accountNumber")
  @Mapping(source = "accountInformation.type", target = "accountType")
  @Mapping(source = "accountInformation.initialBalance", target = "initialBalance")
  @Mapping(source = "accountInformation.status", target = "status")
  Account replaceAccountRequestToAccount(ReplaceAccountRequest replaceAccountRequest);

}
