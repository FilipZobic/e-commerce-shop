package org.zobic.ecommerceshopapi.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.zobic.ecommerceshopapi.jackson.WhiteSpaceRemovalDeserializer;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDtoUpdate {

  private UUID id;

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty(message = "Username is required")
  private String fullName;

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @Size(min = 10, message = "Password must contain at least 10 characters")
  @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one number")
  private String password;

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty(message = "Email is required")
  private String email;

  @Pattern(regexp = "ROLE_[A-Z]+")
  private String role;

  @Valid
  @JsonProperty("address")
  private AddressDto address;

  private Boolean isEnabled;
}
