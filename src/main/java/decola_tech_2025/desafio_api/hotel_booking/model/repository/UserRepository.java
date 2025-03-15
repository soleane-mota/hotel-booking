package decola_tech_2025.desafio_api.hotel_booking.model.repository;

import decola_tech_2025.desafio_api.hotel_booking.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByEmail(String email);
}
