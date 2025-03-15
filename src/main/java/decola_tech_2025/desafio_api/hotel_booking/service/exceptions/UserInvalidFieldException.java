package decola_tech_2025.desafio_api.hotel_booking.service.exceptions;

public class UserInvalidFieldException extends RuntimeException{

  public UserInvalidFieldException() {
    super("Preencha os campos de e-mail, nome e senha corretament.");
  }
}
