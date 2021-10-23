package org.zobic.ecommerceshopapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Laptop extends GenericUuidModel {

  @Column(nullable = false)
  private String name;

  @ManyToOne(optional = false)
  @JoinColumn(name = "manufacturer_id")
  private Manufacturer manufacturer;

  @Column(nullable = false)
  private Double price;

  @Column(nullable = false)
  private Float diagonal;

  @Column(nullable = false)
  private String panelType;

  @Column(nullable = false)
  private Ram ram;

  @Column(nullable = false)
  private Integer stock;

  @Column(nullable = false)
  private String coverImagePath;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Laptop laptop = (Laptop) o;
    return name.equals(laptop.name) && manufacturer.equals(laptop.manufacturer) && price.equals(laptop.price) && diagonal.equals(laptop.diagonal) && panelType.equals(laptop.panelType) && ram == laptop.ram && stock.equals(laptop.stock);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, manufacturer, price, diagonal, panelType, ram, stock);
  }
}
