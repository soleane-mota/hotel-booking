package decola_tech_2025.desafio_api.hotel_booking.controller.dto;

import decola_tech_2025.desafio_api.hotel_booking.model.entity.Role;
import decola_tech_2025.desafio_api.hotel_booking.model.entity.User;

public record UserDto(Long id, String name, String email, Role role) {
  public static UserDto fromEntity(User user) {
    return new UserDto(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getRole()
    );
  }
}
