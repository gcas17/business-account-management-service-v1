package com.devsu.services;

import org.openapitools.model.ReportResponseInner;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface ReportService {

  Flux<ReportResponseInner> generateReport(LocalDate from, LocalDate to, Long clientId);

}