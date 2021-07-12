package org.zobic.ecommerceshopapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.zobic.ecommerceshopapi.jackson.WhiteSpaceRemovalDeserializer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDto {

  private UUID id;

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty(message = "Username is required")
  private String username;

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty
  @Size(min = 10, message = "Password must contain at least 10 characters")
  @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one number")
  private String password;

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty(message = "Email is required")
  private String email;

  private Set<String> grantedAuthorities;
}
