package org.zobic.ecommerceshopapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "application_user")
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE application_user SET deleted = true WHERE id=?")
@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedUserFilter", condition = "deleted = :isDeleted")
//@Table(name = "application_user", uniqueConstraints = {
//  @UniqueConstraint(name = "UC_email", columnNames = { "email" } ),
//  @UniqueConstraint(name = "UC_username", columnNames =  { "username" } ) })

public class User extends GenericUuidModel {

  @Column(nullable = false, unique = false)
  private String fullName;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private Boolean deleted = false;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private Boolean enabled = false;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
  @JoinTable(
    name = "application_user_role",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
  )
  private Collection<Role> roles;
//
//  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
//  @JoinTable(
//    name = "cart",
//    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//    inverseJoinColumns = @JoinColumn(name = "laptop_id", referencedColumnName = "id")
//  )
//  private List<Laptop> cart;

  @OneToOne(optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "address_id")
  private Address address = null;

  public User(UUID id, String fullName, String password, String email, Collection<Role> roles) {
    super(id);
    this.fullName = fullName;
    this.password = password;
    this.email = email;
    this.roles = roles;
  }

  public User(String fullName, String password, String email, Collection<Role>  roles, Address address, Boolean isEnabled) {
    this.fullName = fullName;
    this.password = password;
    this.email = email;
    this.roles = roles;
    this.address = address;
    this.enabled = isEnabled;
  }
}
