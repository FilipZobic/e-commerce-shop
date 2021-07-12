package org.zobic.ecommerceshopapi.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Privilege extends GenericUuidModel{

  @Column(unique = true)
  private String title;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "role_privilege",
    joinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
  )
  private Collection<Role> roles;

  public Privilege(String title) {
    this.title = title;
  }
}
