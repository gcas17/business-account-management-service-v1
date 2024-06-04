package com.devsu.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationProperties {

  @Value("${microservices.business-client-management-service-v1}")
  private String clientServiceUrl;

}
