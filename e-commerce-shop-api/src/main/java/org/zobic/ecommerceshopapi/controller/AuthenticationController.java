package org.zobic.ecommerceshopapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.service.UserService;
import org.zobic.ecommerceshopapi.service.UserServiceImplementation;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@RestController
@Validated
public class AuthenticationController {

  private UserService userService;

  public AuthenticationController(UserServiceImplementation userService) {
    this.userService = userService;
  }

  public UserDto userToDto(User user) {

    Set<String> grantedAuthorities = new HashSet<>();

    user.getRoles().forEach(role -> {
      grantedAuthorities.add(role.getTitle());
      role.getPrivileges().forEach(privilege -> {
        grantedAuthorities.add(privilege.getTitle());
      });
    });

    return UserDto.builder()
      .id(user.getId())
      .email(user.getEmail())
      .username(user.getUsername())
      .grantedAuthorities(grantedAuthorities)
      .build();
  }

  @PostMapping(path = "api/register")
  public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {

    return new ResponseEntity<>(this.userToDto(this.userService.registerUser(userDto)), HttpStatus.OK);
  }

  @PostMapping(path = "api/login")
  public ResponseEntity<?> login(HttpSession session) {
    System.out.println("Login: " + SecurityContextHolder.getContext().getAuthentication().getName() + "\nTime logout: " + new Date(System.currentTimeMillis())); // command line runner Sf4j

    return new ResponseEntity<>("login", HttpStatus.OK);
  }

  @PostMapping("api/logout")
  public ResponseEntity<Void> logout(HttpSession session) {
    System.out.println("Logout: " + SecurityContextHolder.getContext().getAuthentication().getName() + "\nTime logout: " + new Date(System.currentTimeMillis())); // command line runner Sf4j
    SecurityContextHolder.clearContext();
    session.invalidate();
    return ResponseEntity.noContent().build();
  }
}
