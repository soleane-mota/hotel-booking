package decola_tech_2025.desafio_api.hotel_booking.controller;

import decola_tech_2025.desafio_api.hotel_booking.controller.dto.UserDto;
import decola_tech_2025.desafio_api.hotel_booking.controller.dto.UserCreateDto;
import decola_tech_2025.desafio_api.hotel_booking.model.entity.User;
import decola_tech_2025.desafio_api.hotel_booking.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto create(@RequestBody UserCreateDto userCreateDto) {
    User user = userService.create(userCreateDto.toEntity());
    return UserDto.fromEntity(user);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserDto> getAll() {
    List<User> users = userService.getAll();
    return users.stream().map(UserDto::fromEntity).toList();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDto getById(@PathVariable("id") Long id) {
    User user = userService.getById(id);
    return UserDto.fromEntity(user);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDto update(@PathVariable("id") Long id, @RequestBody UserCreateDto userCreateDto){
    User user = userService.update(id, userCreateDto.toEntity());
    return UserDto.fromEntity(user);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public String delete(@PathVariable("id") Long id) {
    return userService.delete(id);
  }
}
