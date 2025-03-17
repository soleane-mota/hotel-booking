package decola_tech_2025.desafio_api.hotel_booking.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(unique = true)
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private Role role = Role.USER;

  @PrePersist
  public void setDefaultRole() {
    if (this.role == null) {
      this.role = Role.USER;
    }
  }
}

