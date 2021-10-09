package org.zobic.ecommerceshopapi.model;

import lombok.*;

import javax.persistence.*;

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
}
