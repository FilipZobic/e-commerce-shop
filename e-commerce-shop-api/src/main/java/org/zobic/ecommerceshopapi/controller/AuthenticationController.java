package org.zobic.ecommerceshopapi.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.event.OnRegistrationCompleteEvent;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.exception.TokenExpiredException;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.service.UserService;
import org.zobic.ecommerceshopapi.service.UserServiceImplementation;
import org.zobic.ecommerceshopapi.util.Utility;
import org.zobic.ecommerceshopapi.util.UtilitySecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@RestController
@Validated
public class AuthenticationController {

  private UserService userService;

  private UtilitySecurity utilitySecurity;

  private ApplicationEventPublisher eventPublisher;

  public AuthenticationController(UserServiceImplementation userService, UtilitySecurity utilitySecurity, ApplicationEventPublisher eventPublisher) {
    this.userService = userService;
    this.utilitySecurity = utilitySecurity;
    this.eventPublisher = eventPublisher;
  }

  // This logic should go to the service
  @PostMapping(path = "api/register")
  public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto, HttpSession session, HttpServletRequest request) throws Exception {
    User user = this.userService.registerUser(userDto);
    if (session.getAttribute("inCart") != null) {

    }
    this.eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), request.getContextPath()));
    this.utilitySecurity.updateSecurityContext(user.getUsername());
    return new ResponseEntity<>(Utility.userToDto(user), HttpStatus.OK);
  }

  @PostMapping(path = "api/login")
  public ResponseEntity<?> login(HttpSession session) {
    System.out.println("Login: " + SecurityContextHolder.getContext().getAuthentication().getName() + "\nTime login: " + new Date(System.currentTimeMillis())); // command line runner Sf4j
    System.out.println();
    if (session.getAttribute("inCart") != null) {
      // fill out database
    }
    System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    return new ResponseEntity<>("login", HttpStatus.OK);
  }

  @PostMapping("api/logout")
  public ResponseEntity<Void> logout(HttpSession session) {
    System.out.println("Logout: " + SecurityContextHolder.getContext().getAuthentication().getName() + "\nTime logout: " + new Date(System.currentTimeMillis())); // command line runner Sf4j
    SecurityContextHolder.clearContext();
    session.invalidate();
    return ResponseEntity.noContent().build();
  }

  @PostMapping(path = "api/registrationConfirmation")
  public ResponseEntity<?> confirmUser(@RequestParam(required = false) UUID token, HttpServletRequest request) throws ResourceNotFoundException, TokenExpiredException {
    // after activation delete token, add resend verification token, everyone can see if account is active,
    this.userService.confirmUser(token);
    Map<String, String> responseMessage = new HashMap<>();
    responseMessage.put("body", "User has been successfully verified.");
    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
  }

  @PostMapping(path = "api/users/{id}/requestNewConfirmation")
  public ResponseEntity<?> reSendToken(@PathVariable(name = "id") UUID userId, HttpServletRequest request) throws Exception {
    this.userService.destroyOldAndCreateNewVerificationToken(userId);
    Map<String, String> responseMessage = new HashMap<>();
    responseMessage.put("body", "New token sent successfully");
    return new ResponseEntity<>(responseMessage, HttpStatus.OK);
  }
}
