package com.devsu.mappers;

import com.devsu.model.entities.Account;
import com.devsu.model.entities.Movement;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.CreateMovementRequest;
import org.openapitools.model.MovementResponse;
import org.openapitools.model.ReplaceMovementRequest;
import org.openapitools.model.UpdateMovementRequest;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovementMapper {

  MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

  @Mapping(source = "processDate", target = "date")
  @Mapping(source = "movementType", target = "movementType")
  @Mapping(source = "amount", target = "value")
  @Mapping(source = "accountId", target = "accountId")
  Movement createMovementRequestToMovement(CreateMovementRequest createMovementRequest);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "processDate", target = "date")
  @Mapping(source = "movementType", target = "movementType")
  @Mapping(source = "amount", target = "value")
  @Mapping(source = "accountId", target = "accountId")
  Movement replaceMovementRequestToMovement(ReplaceMovementRequest replaceMovementRequest);

  @Mapping(source = "movement.id", target = "id")
  @Mapping(source = "account.accountNumber", target = "accountNumber")
  @Mapping(source = "account.accountType", target = "accountType")
  @Mapping(source = "account.initialBalance", target = "initialBalance")
  @Mapping(source = "account.status", target = "status")
  @Mapping(target = "movementDetail", expression = "java(formatMovementDetail(movement))")
  MovementResponse movementToMovementResponse(Movement movement, Account account);

  @Mapping(source = "processDate", target = "date")
  @Mapping(source = "movementType", target = "movementType")
  @Mapping(source = "amount", target = "value")
  @Mapping(source = "accountId", target = "accountId")
  void updateMovementRequestToMovement(UpdateMovementRequest updateMovementRequest, @MappingTarget Movement movement);

  default String formatMovementDetail(Movement movement) {
    if (movement.getMovementType() != null && !movement.getMovementType().isEmpty()) {
      String movementType = movement.getMovementType();
      String capitalizedMovementType = Character.toUpperCase(movementType.charAt(0)) + movementType.substring(1).toLowerCase();
      return capitalizedMovementType + " de " + movement.getValue().abs();
    }
    return null;
  }

}
