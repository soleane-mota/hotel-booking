package decola_tech_2025.desafio_api.hotel_booking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import decola_tech_2025.desafio_api.hotel_booking.model.entity.User;
import decola_tech_2025.desafio_api.hotel_booking.service.UserService;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserAlreadyExistsException;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserInvalidFieldException;
import decola_tech_2025.desafio_api.hotel_booking.service.exceptions.UserNotFoundException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @MockitoBean
  private UserService userService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("1 - Verifica requisição e resposta de sucesso na criação de um usuário")
  void testCreateUser() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("Maria Silva");
    user.setEmail("maria@email.com");
    user.setPassword("senha123");

    when(userService.create(any(User.class))).thenReturn(user);

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(user)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Maria Silva"))
        .andExpect(jsonPath("$.email").value("maria@email.com"));
  }

  @Test
  @DisplayName("2 - Verifica resposta de sucesso na busca por um usuário")
  void testGetUserById() throws Exception {
    // Arrange
    User user = new User();
    user.setId(1L);
    user.setName("Maria Silva");
    user.setEmail("maria@email.com");
    user.setPassword("senha123");

    when(userService.getById(1L)).thenReturn(user);

    // Act & Assert
    mockMvc.perform(get("/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Maria Silva"))
        .andExpect(jsonPath("$.email").value("maria@email.com"));
  }

  @Test
  @DisplayName("3 - Verifica resposta de sucesso na busca pela lista de usuários")
  void testGetAllUsers() throws Exception {
    // Arrange
    User user1 = new User();
    user1.setId(1L);
    user1.setName("Maria Silva");
    user1.setEmail("maria@email.com");
    user1.setPassword("senha123");

    User user2 = new User();
    user2.setId(2L);
    user2.setName("Mary Smith");
    user2.setEmail("mary@email.com");
    user2.setPassword("senha456");

    List<User> users = Arrays.asList(user1, user2);

    when(userService.getAll()).thenReturn(users);

    // Act & Assert
    mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Maria Silva"))
        .andExpect(jsonPath("$[1].id").value(2L))
        .andExpect(jsonPath("$[1].name").value("Mary Smith"));
  }

  @Test
  @DisplayName("4 - Verifica requisição e resposta de sucesso na atualização de um usuário")
  void testUpdateUser() throws Exception {
    // Arrange
    User updatedUser = new User();
    updatedUser.setName("Maria Silva");
    updatedUser.setEmail("maria@email.com");

    when(userService.update(eq(1L), any(User.class))).thenReturn(updatedUser);

    // Act & Assert
    mockMvc.perform(put("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(updatedUser)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Maria Silva"))
        .andExpect(jsonPath("$.email").value("maria@email.com"));
  }

  @Test
  @DisplayName("5 - Verifica resposta de sucesso ao deletar um usuário")
  void testDeleteUser() throws Exception {
    doReturn("Usuário removido com sucesso").when(userService).delete(1L);

    mockMvc.perform(delete("/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("Usuário removido com sucesso"));
  }

  @Test
  @DisplayName("6 - Verifica resposta de usuário com campos nulos ou vazios ao tentar cadastrar")
  void testCreateUser_BadRequest() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("Maria Silva");
    user.setEmail("maria@email.com");
    user.setPassword("senha123");

    when(userService.create(any(User.class))).thenThrow(new UserInvalidFieldException());

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(user)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("7 - Verifica resposta de usuário já existentes ao tentar cadastrar")
  void testCreateUser_Conflict() throws Exception {
    User user = new User();
    user.setId(1L);
    user.setName("Maria Silva");
    user.setEmail("maria@email.com");
    user.setPassword("senha123");

    when(userService.create(any(User.class))).thenThrow(new UserAlreadyExistsException());

    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(user)))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("8 - Verifica resposta de usuário não encontrado ao buscar por id")
  void testGetUserById_NotFound() throws Exception {
    when(userService.getById(1L)).thenThrow(new UserNotFoundException());

    mockMvc.perform(get("/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("9 - Verifica resposta de usuário não encontrado ao tentar atualizar")
  void testUpdateUser_NotFound() throws Exception {
    User updatedUser = new User();
    updatedUser.setName("Maria Silva");
    updatedUser.setEmail("maria@email.com");
    updatedUser.setPassword("senha456");

    when(userService.update(eq(1L), any(User.class))).thenThrow(new UserNotFoundException());

    mockMvc.perform(put("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(updatedUser)))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("10 - Verifica resposta de usuário não encontrado ao tentar deletar")
  void testDeleteUser_NotFound() throws Exception {
    doThrow(new UserNotFoundException()).when(userService).delete(1L);

    mockMvc.perform(delete("/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
