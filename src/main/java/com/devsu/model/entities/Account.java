package com.devsu.model.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Table("Account")
public class Account {

  @Id
  @Column("id")
  private Long id;

  @Column("account_number")
  private String accountNumber;

  @Column("account_type")
  private String accountType;

  @Column("initial_balance")
  private BigDecimal initialBalance;

  @Column("status")
  private String status;

  @Column("client_id")
  private Long clientId;

}
