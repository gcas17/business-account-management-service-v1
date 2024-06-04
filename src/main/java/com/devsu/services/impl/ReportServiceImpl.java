package com.devsu.services.impl;

import com.devsu.clients.ClientServiceClient;
import com.devsu.mappers.ReportMapper;
import com.devsu.repositories.AccountRepository;
import com.devsu.repositories.MovementRepository;
import com.devsu.services.ReportService;
import lombok.AllArgsConstructor;
import org.openapitools.model.ReportResponseInner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final ClientServiceClient clientServiceClient;
  private final AccountRepository accountRepository;
  private final MovementRepository movementRepository;

  @Override
  public Flux<ReportResponseInner> generateReport(LocalDate from, LocalDate to, Long clientId) {
    return clientServiceClient.getClientById(clientId)
        .flatMapMany(client -> accountRepository.findByClientId(client.getId())
            .flatMapMany(account -> movementRepository.findByAccountIdAndDateBetweenOrderByIdDesc(account.getId(), from, to)
                .map(movement -> ReportMapper.INSTANCE.entitiesToReportResponse(account, client, movement))));
  }

}
