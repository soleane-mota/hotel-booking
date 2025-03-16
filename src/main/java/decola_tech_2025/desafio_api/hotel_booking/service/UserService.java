package decola_tech_2025.desafio_api.hotel_booking.service;

import decola_tech_2025.desafio_api.hotel_booking.model.entity.User;
import decola_tech_2025.desafio_api.hotel_booking.model.repository.UserRepository;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserAlreadyExistsException;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserInvalidFieldException;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserNotFoundException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User create(User user) {
    if (isBlank(user.getName()) || isBlank(user.getEmail()) || isBlank(user.getPassword())) {
      throw new UserInvalidFieldException();
    }
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new UserAlreadyExistsException();
    }
    return userRepository.save(user);
  }

  private boolean isBlank(String str) {
    return Objects.requireNonNullElse(str, "").isBlank();
  }

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User getById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(UserNotFoundException::new);
  }

  public User update(Long id, User newUser) {
    if (userRepository.existsById(id)) {
      throw new UserAlreadyExistsException();
    }
    User user = getById(id);
    return userRepository.save(newUser);
  }

  public String delete(Long id) {
    try {
      userRepository.deleteById(id);
      return "Usuário removido com sucesso";
    } catch (UserNotFoundException e) {
      return e.getMessage();
    }
  }
}
