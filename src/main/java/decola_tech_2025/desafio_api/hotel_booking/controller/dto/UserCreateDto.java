package decola_tech_2025.desafio_api.hotel_booking.controller.dto;

import decola_tech_2025.desafio_api.hotel_booking.model.entity.Role;
import decola_tech_2025.desafio_api.hotel_booking.model.entity.User;

public record UserCreateDto(String name, String email, String password, Role role) {
  public User toEntity() {
    User user = new User();
    user.setName(name);
    user.setEmail(email);
    user.setPassword(password);
    user.setRole(role);
    return user;
  }
}
