package org.zobic.ecommerceshopapi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manufacturer extends GenericUuidModel {

  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "manufacturer", fetch = FetchType.EAGER)
  private List<Laptop> laptops;

  public boolean hasAnyRelation() {
    if (this.laptops != null) {
      return !laptops.isEmpty();
    }
    return false;
  }

  public String getReason() {
    if (hasAnyRelation()) {
      return "Manufacturer can not be deleted because it has relations to products";
    }
    return "This entity can be deleted";
  }

  public int getNumberOfProducts() {
    if (this.laptops != null) {
      return laptops.size();
    }
    return 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Manufacturer that = (Manufacturer) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
