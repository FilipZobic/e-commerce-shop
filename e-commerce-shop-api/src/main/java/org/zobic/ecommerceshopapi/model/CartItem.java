package org.zobic.ecommerceshopapi.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Table(name = "cart")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

  @EmbeddedId
  private CartItemId id;

  @Column(nullable = false)
  private Integer amount;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartItem cartItem = (CartItem) o;
    return id.equals(cartItem.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Embeddable
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class CartItemId implements Serializable {

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    @Column(name = "user_id")
    private UUID userId;

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    @Column(name = "laptop_id")
    private UUID laptopId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CartItemId that = (CartItemId) o;
      return userId.equals(that.userId) && laptopId.equals(that.laptopId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(userId, laptopId);
    }
  }
}
