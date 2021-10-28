package org.zobic.ecommerceshopapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zobic.ecommerceshopapi.jackson.WhiteSpaceRemovalDeserializer;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerDto {

  private UUID id;

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty(message = "Email is required")
  private String name;

  private int numberOfProducts;
}
