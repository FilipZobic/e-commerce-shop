package org.zobic.ecommerceshopapi.util;

import org.springframework.mail.SimpleMailMessage;
import org.zobic.ecommerceshopapi.dto.AddressDto;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.model.Address;
import org.zobic.ecommerceshopapi.model.User;

import java.util.HashSet;
import java.util.Set;

public class Utility {
  public static UserDto userToDto(User user) {

    Address address = user.getAddress();
    AddressDto addressDto = null;
    if (address != null) {
      addressDto = AddressDto.builder()
        .addressValue(address.getAddress())
        .cityName(address.getCityName())
        .zipCode(address.getZipCode())
        .countryAlpha2Code(address.getCountryCode())
        .build();
    }

    Boolean isDeleted = null;

    if (UtilitySecurity.userHasAdminRole()) {
      isDeleted = user.getDeleted();
    }

    Set<String> grantedAuthorities = new HashSet<>();

    user.getRoles().forEach(role -> {
      grantedAuthorities.add(role.getTitle());
      role.getPrivileges().forEach(privilege -> {
        grantedAuthorities.add(privilege.getTitle());
      });
    });

    return UserDto.builder()
      .id(user.getId())
      .address(addressDto)
      .email(user.getEmail())
      .grantedAuthorities(grantedAuthorities)
      .isDeleted(isDeleted)
      .isEnabled(user.getEnabled())
      .fullName(user.getFullName())
      .build();
  }

  public static SimpleMailMessage generateMailMessage(String subject, String message, String recipient, String sender) {
    SimpleMailMessage emailMessage = new SimpleMailMessage();
    emailMessage.setTo(recipient);
    emailMessage.setSubject(subject);
    emailMessage.setText(message);
    emailMessage.setFrom(sender);
    return  emailMessage;
  }
}
