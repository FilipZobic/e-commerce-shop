package org.zobic.ecommerceshopapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.zobic.ecommerceshopapi.jackson.WhiteSpaceRemovalDeserializer;
import org.zobic.ecommerceshopapi.util.ValidationPatterns;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class CheckIfEmailTakenDto {

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty(message = "Email is required")
  @Pattern(regexp = ValidationPatterns.EMAIL_PATTERN, message = "Bad email format")
  private String email;

}
