package com.devsu.controllers;

import com.devsu.services.ReportService;
import lombok.AllArgsConstructor;
import org.openapitools.api.ReportApi;
import org.openapitools.model.ReportResponseInner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ReportController implements ReportApi {

  private final ReportService reportService;

  @Override
  public Mono<ResponseEntity<Flux<ReportResponseInner>>> generateReport(LocalDate from, LocalDate to, Long clientId, ServerWebExchange exchange) {
    Flux<ReportResponseInner> reportFlux = reportService.generateReport(from, to, clientId);
    return Mono.just(ResponseEntity.ok(reportFlux));
  }
}
