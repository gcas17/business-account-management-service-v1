package com.devsu.model.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@Table("Movement")
public class Movement {

  @Id
  @Column("id")
  private Long id;

  @Column("date")
  private LocalDate date;

  @Column("movement_type")
  private String movementType;

  @Column("value")
  private BigDecimal value;

  @Column("available_balance")
  private BigDecimal availableBalance;

  @Column("account_id")
  private Long accountId;

}
