package decola_tech_2025.desafio_api.hotel_booking.controller.advice;

import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserAlreadyExistsException;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserInvalidFieldException;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleNotFound(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<String> handleConflict(UserAlreadyExistsException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }

  @ExceptionHandler(UserInvalidFieldException.class)
  public ResponseEntity<String> handleBadRequest(UserInvalidFieldException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
}
