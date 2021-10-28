package org.zobic.ecommerceshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.zobic.ecommerceshopapi.jackson.WhiteSpaceRemovalDeserializer;
import org.zobic.ecommerceshopapi.model.CartItem;
import org.zobic.ecommerceshopapi.util.ValidationPatterns;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
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
  @NotEmpty(message = "Full name is required")
  private String fullName;

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty
  @Size(min = 6, message = "Password must contain at least 6 characters")
  @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one number")
  private String password;

  @JsonDeserialize(using= WhiteSpaceRemovalDeserializer.class)
  @NotEmpty(message = "Email is required")
  @Pattern(regexp = ValidationPatterns.EMAIL_PATTERN, message = "Bad email format")
  private String email;

  @Pattern(regexp = "ROLE_[A-Z]+")
  private String role;

  @Valid
  @NotNull
  @JsonProperty("address")
  private AddressDto address;

  private Boolean isDeleted;

  private Boolean isEnabled;

  private UUID verificationToken;

  private Set<String> grantedAuthorities;

  private Set<CartItem> cart;
}
