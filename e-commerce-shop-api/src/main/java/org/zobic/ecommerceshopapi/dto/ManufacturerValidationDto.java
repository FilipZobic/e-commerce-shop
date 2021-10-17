package org.zobic.ecommerceshopapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.zobic.ecommerceshopapi.jackson.WhiteSpaceRemovalDeserializer;

import javax.validation.constraints.NotEmpty;

@Data
public class ManufacturerValidationDto {

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty(message = "Email is required")
  private String name;
}
