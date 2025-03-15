package decola_tech_2025.desafio_api.hotel_booking.service.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException() {
    super("O usuário já existe.");
  }
}
