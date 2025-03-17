package decola_tech_2025.desafio_api.hotel_booking.service.exceptions;

public class UserNotFoundException extends RuntimeException{

  public UserNotFoundException() {
    super("Usuário não encontrado.");
  }
}
