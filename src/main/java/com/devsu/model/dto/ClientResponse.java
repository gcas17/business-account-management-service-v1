package com.devsu.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ClientResponse {

  private Long id;

  private String name;

  private String address;

  private String phone;

  private String password;

  private Boolean status;

}
