package com.devsu.mappers;

import com.devsu.model.dto.ClientResponse;
import com.devsu.model.entities.Account;
import com.devsu.model.entities.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.ReportResponseInner;

@Mapper
public interface ReportMapper {

  ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

  @Mapping(source = "account.accountNumber", target = "accountNumber")
  @Mapping(source = "account.accountType", target = "accountType")
  @Mapping(source = "account.initialBalance", target = "initialBalance")
  @Mapping(source = "account.status", target = "status")
  @Mapping(source = "client.name", target = "clientName")
  @Mapping(source = "movement.date", target = "processDate")
  @Mapping(source = "movement.value", target = "amount")
  @Mapping(source = "movement.availableBalance", target = "availableBalance")
  ReportResponseInner entitiesToReportResponse(Account account, ClientResponse client, Movement movement);

}
