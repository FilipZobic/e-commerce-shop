package org.zobic.ecommerceshopapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "application_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends GenericUuidModel {

  public User(UUID id, String username, String password, String email, Collection<Role> roles) {
    super(id);
    this.username = username;
    this.password = password;
    this.email = email;
    this.roles = roles;
  }

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "application_user_role",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
  )
  private Collection<Role> roles;
}
