package decola_tech_2025.desafio_api.hotel_booking;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import decola_tech_2025.desafio_api.hotel_booking.model.entity.Role;
import decola_tech_2025.desafio_api.hotel_booking.model.entity.User;
import decola_tech_2025.desafio_api.hotel_booking.model.repository.UserRepository;
import decola_tech_2025.desafio_api.hotel_booking.service.UserService;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserAlreadyExistsException;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserInvalidFieldException;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class UserServiceTest {

  @MockitoBean
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Test
  @DisplayName("1 - Cria usuário")
  void testCreateUser() {
    User mockUser = new User();
    mockUser.setId(1L);
    mockUser.setName("John Lennon");
    mockUser.setEmail("john@example.com");
    mockUser.setPassword("yoko1969");

    when(userRepository.save(any())).thenReturn(mockUser);

    User user = new User();
    user.setName(mockUser.getName());
    user.setEmail(mockUser.getEmail());
    user.setPassword(mockUser.getPassword());

    // Act
    User createdUser = userService.create(user);

    assertNotNull(createdUser);
    assertEquals(mockUser.getName(), createdUser.getName());
    assertEquals(mockUser.getEmail(), createdUser.getEmail());
    assertEquals(mockUser.getPassword(), createdUser.getPassword());
    assertEquals(Role.USER, createdUser.getRole());
    verify(userRepository, times(1)).save(any()); // any(User.class)?
  }

  @Test
  @DisplayName("2 - Busca usuário por id")
  void testGetUserById() {
    User user = new User();
    user.setId(1L);
    user.setName("John Lennon");
    user.setEmail("john@example.com");
    user.setPassword("yoko1969");

    when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));

    // Act
    User foundUser = userService.getById(1L);

    assertNotNull(foundUser);
    assertEquals(user.getId(), foundUser.getId());
    assertEquals(user.getName(), foundUser.getName());
    assertEquals(user.getEmail(), foundUser.getEmail());
    verify(userRepository, times(1)).findById(eq(1L));
  }

  @Test
  @DisplayName("3 - Busca todos os usuários")
  void testGetAllUsers() {
    User user1 = new User();
    user1.setId(1L);
    user1.setName("John Lennon");
    user1.setEmail("john@example.com");
    user1.setPassword("yoko1969");

    User user2 = new User();
    user2.setId(2L);
    user2.setName("Anita Malfatti");
    user2.setEmail("anita@example.com");
    user2.setPassword("arte1922");

    when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

    // Act
    List<User> users = userService.getAll();

    assertEquals(2, users.size());
    assertEquals("John Lennon", users.get(0).getName());
    assertEquals("Anita Malfatti", users.get(1).getName());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  @DisplayName("4 - Atualiza dados de um usuário")
  void testUpdateUser() {
    User existingUser = new User();
    existingUser.setId(1L);
    existingUser.setName("John Lennon");
    existingUser.setEmail("john@example.com");
    existingUser.setPassword("yoko1969");

    User updatedUser = new User();
    updatedUser.setId(1L);
    updatedUser.setName("John Lennon");
    updatedUser.setEmail("john@example.com");
    updatedUser.setPassword("yoko1969");
    updatedUser.setRole(Role.ADMIN);

    when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
    when(userRepository.save(any())).thenReturn(updatedUser);

    // Act
    User result = userService.update(1L);

    assertNotNull(result);
    assertEquals(updatedUser.getName(), result.getName());
    assertEquals(updatedUser.getEmail(), result.getEmail());
    assertEquals(updatedUser.getRole(), result.getRole());
    verify(userRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("5 - Deleta um usuário")
  void testDeleteUser() {
    when(userRepository.existsById(eq(1L))).thenReturn(true);

    assertDoesNotThrow(() -> userService.delete(1L));
    verify(userRepository, times(1)).deleteById(eq(1L));
  }

  @Test
  @DisplayName("6 - Testa ocorrência de exceção ao cadastrar usuário já existente")
  void testUserAlreadyExistsException() {
    User user = new User();
    user.setId(1L);
    user.setName("John Lennon");
    user.setEmail("john@example.com");
    user.setPassword("yoko1969");

    when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

    assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));
    verify(userRepository, never()).save(user);
  }

  @Test
  @DisplayName("7 - Testa ocorrência de exceção ao buscar usuário inesistente")
  void testUserNotFoundException() {
//    when(userRepository.findById(1L)).thenReturn(Optional.empty());
    when(userRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.getById(1L));
    verify(userRepository, times(1)).findById(eq(1L));
  }

  @Test
  @DisplayName("8 - Testa ocorrência de exceção ao cadastrar usuário sem os campos obrigatórios")
  void testUserInvalidFieldException() {
    User user = new User();
    user.setName("John Lennon");

    assertThrows(UserInvalidFieldException.class, () -> userService.create(user));
  }
}
