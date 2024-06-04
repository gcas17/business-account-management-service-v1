package com.devsu.utilities.enums;

import com.devsu.utilities.exception.CustomApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@AllArgsConstructor
@Getter
public enum ApiException {
  ACC0001("ACC0001", "Cuenta no encontrada", BAD_REQUEST),
  ACC0002("ACC0002", "Cliente no encontrado", BAD_REQUEST),
  ACC0003("ACC0003", "Campos obligatorios: id", BAD_REQUEST),
  ACC0004("ACC0004", "Campos obligatorios: id, accountNumber, accountType, initialBalance, status, clientId", BAD_REQUEST),
  ACC0005("ACC0005", "Campos obligatorios: accountNumber, accountType, initialBalance, status, clientId", BAD_REQUEST),
  ACC0006("ACC0006", "Campos obligatorios: id", BAD_REQUEST),
  ACC0007("ACC0007", "Campos obligatorios: id, processDate, movementType, amount, accountId", BAD_REQUEST),
  ACC0008("ACC0008", "Campos obligatorios: processDate, movementType, amount, accountId", BAD_REQUEST),
  ACC0009("ACC0009", "Movimiento no encontrado", BAD_REQUEST);

  private final String code;
  private final String description;
  private final HttpStatus status;
  private static final List<ApiException> list = new ArrayList<>();

  private static final Map<String, ApiException> lookup = new HashMap<>();

  static {
    for (ApiException s : EnumSet.allOf(ApiException.class)) {
      list.add(s);
      lookup.put(s.getCode(), s);
    }
  }

  public static ApiException get(String code) {
    return lookup.get(code);
  }

  public CustomApiException getException() {
    return new CustomApiException(this.getCode(), this.getDescription(), this.getStatus());
  }
}
