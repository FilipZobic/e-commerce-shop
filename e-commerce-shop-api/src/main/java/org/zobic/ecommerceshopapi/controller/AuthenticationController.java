package org.zobic.ecommerceshopapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zobic.ecommerceshopapi.dto.CheckIfEmailTakenDto;
import org.zobic.ecommerceshopapi.session.SessionService;
import org.zobic.ecommerceshopapi.dto.CredentialsDto;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.event.OnRegistrationCompleteEvent;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.exception.TokenExpiredException;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.security.AuthenticationSessionFilter;
import org.zobic.ecommerceshopapi.service.UserService;
import org.zobic.ecommerceshopapi.util.Utility;
import org.zobic.ecommerceshopapi.util.UtilitySecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@RestController
@Validated
public class AuthenticationController {

  private UserService userService;

  private UtilitySecurity utilitySecurity;

  private ApplicationEventPublisher eventPublisher;

  private SessionService sessionService;

  public AuthenticationController(UserService userService, UtilitySecurity utilitySecurity, ApplicationEventPublisher eventPublisher, SessionService sessionService) {
    this.userService = userService;
    this.utilitySecurity = utilitySecurity;
    this.eventPublisher = eventPublisher;
    this.sessionService = sessionService;
  }

  @PostMapping(path = "api/isEmailTaken")
  public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody CheckIfEmailTakenDto validationDto) throws Exception {
    Optional<User> user = this.userService.findUserByEmail(validationDto.getEmail());
    Map<String, Object> response = new HashMap<>();
    response.put("isEmailTaken", user.isPresent());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping(path = "api/register")
  public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto, HttpSession session, HttpServletRequest request) throws Exception {
    User user = this.userService.registerUser(userDto);
    this.eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), request.getContextPath()));
    this.utilitySecurity.updateSecurityContext(user.getEmail());
    return new ResponseEntity<>(Utility.userToDto(user), HttpStatus.OK);
  }

  @PostMapping(path = "api/login")
  public ResponseEntity<?> login(@RequestBody CredentialsDto credentialsDto, HttpServletResponse response) {
    Optional<User> userOptional = this.userService.findUserByEmail(credentialsDto.getEmail());

    if (userOptional.isPresent()){
      User user = userOptional.get();
      response.setHeader(AuthenticationSessionFilter.X_ACCESS_TOKEN, sessionService.generateNewSession(user.getEmail(),user.getId()).getId());
      return new ResponseEntity<>(Utility.userToDto(userOptional.get()), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @DeleteMapping("api/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request) {
    System.out.println("Logout: " + SecurityContextHolder.getContext().getAuthentication().getName() + "\nTime logout: " + new Date(System.currentTimeMillis())); // command line runner Sf4j
    sessionService.deleteSessionById(request.getHeader(AuthenticationSessionFilter.X_ACCESS_TOKEN));
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
