package org.zobic.ecommerceshopapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.zobic.ecommerceshopapi.jackson.WhiteSpaceRemovalDeserializer;
import org.zobic.ecommerceshopapi.model.Laptop;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Data
public class UpdateLaptopDto {

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty(message = "Product name is required")
  private String name;

  @NotEmpty(message = "Product image is required")
  private String image;

  @NotNull
  private UUID manufacturerId;

  @NotNull(message = "Price is required")
  @Min(value = 0, message = "Value can't be negative")
  private Double price;

  @NotNull(message = "Diagonal is required")
  @Min(value = 0, message = "Value can't be negative")
  private Float diagonal;

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty(message = "Panel type is required")
  private String panelType;

  @NotNull(message = "Ram is required")
  @Min(value = 0, message = "Ram can't be a negative value")
  private Integer ram;

  @NotNull(message = "Stock is required")
  @Min(value = 0, message = "Stock can't be a negative value")
  private Integer stock;
}
