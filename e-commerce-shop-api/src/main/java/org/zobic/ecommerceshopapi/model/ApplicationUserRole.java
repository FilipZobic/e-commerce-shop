package org.zobic.ecommerceshopapi.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "application_user_role")
@Entity
public class ApplicationUserRole {

  @EmbeddedId
  private ApplicationUserRoleKey id;

  @Embeddable
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class ApplicationUserRoleKey implements Serializable {

    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "user_id")
    private UUID userId;
  }
}
