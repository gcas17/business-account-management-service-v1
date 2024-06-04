package com.devsu.controllers;

import lombok.AllArgsConstructor;
import org.openapitools.api.ReportApi;
import org.openapitools.model.ReportResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class ReportController implements ReportApi {

  @Override
  public Mono<ResponseEntity<ReportResponse>> generateReport(LocalDate from, LocalDate to, Long clientId, ServerWebExchange exchange) {
    return ReportApi.super.generateReport(from, to, clientId, exchange);
  }
}
