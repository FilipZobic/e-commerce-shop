package org.zobic.ecommerceshopapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.service.UserService;
import org.zobic.ecommerceshopapi.service.UserServiceImplementation;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class AuthenticationController {

  private UserService userService;

  public AuthenticationController(UserServiceImplementation userService) {
    this.userService = userService;
  }

  @PostMapping(path = "api/register")
  public ResponseEntity<?> register(@RequestBody UserDto userDto) {

    System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

    return new ResponseEntity<>(this.userService.registerUser(userDto), HttpStatus.OK);
  }

  @PostMapping(path = "api/test")
  public ResponseEntity<?> test(HttpSession session) {

    System.out.println("PRINTING");
    System.out.println(session.getAttribute("CartItems"));
    System.out.println("SETTING");
    session.setAttribute("CartItems",new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6)));

    return new ResponseEntity<>("Auth Works", HttpStatus.OK);
  }

  @PostMapping(path = "api/login")
  public ResponseEntity<?> login(HttpSession session) {

    session.setAttribute("CartItems",new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6)));
    System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
    System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

    return new ResponseEntity<>("login", HttpStatus.OK);
  }

  @PostMapping("api/logout")
  public ResponseEntity<Void> logout(HttpSession session) {
    SecurityContextHolder.clearContext();
    session.invalidate();
    return ResponseEntity.noContent().build();
  }
}
