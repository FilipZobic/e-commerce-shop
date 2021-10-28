package org.zobic.ecommerceshopapi.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE address SET deleted = true WHERE id=?")
public class Address extends GenericUuidModel {

    @Column(columnDefinition = "TEXT", nullable = false)
    private String address;

    @Column(nullable = false, columnDefinition = "VARCHAR(32)")
    private String zipCode;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String cityName;

    @Column(columnDefinition = "CHAR(2)", nullable = false)
    private String countryCode;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean deleted = false;

    public Address(UUID id, String address, String zipCode, String cityName, String countryCode) {
        super(id);
        this.address = address;
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.countryCode = countryCode;
    }

  public Address(String address, String zipCode, String cityName, String countryCode) {
    this.address = address;
    this.zipCode = zipCode;
    this.cityName = cityName;
    this.countryCode = countryCode;
  }
}
