package org.zobic.ecommerceshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.dto.UserDtoUpdate;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.repository.UserRepositoryPostgreSql;
import org.zobic.ecommerceshopapi.service.RoleService;
import org.zobic.ecommerceshopapi.service.UserService;
import org.zobic.ecommerceshopapi.util.Utility;
import org.zobic.ecommerceshopapi.util.UtilitySecurity;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Validated
@RequestMapping(path = "/api/users")
public class UserController {

  private UserService userService;

  @Autowired
  private UserRepositoryPostgreSql userRepositoryPostgreSql;

  @Autowired
  RoleService roleService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(path = "/page")
  public ResponseEntity<?> getUsersPage() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<?> getUsers(
    @RequestParam(required = false)String email,
    @RequestParam(required = false)String role,
    @RequestParam(required = false, defaultValue = "email")String sortByProperty,
    @RequestParam(required = false, defaultValue = "50") Integer size,
    @RequestParam(required = false, defaultValue = "0") Integer page) throws ResourceNotFoundException {

    Pageable pageable = PageRequest.of(page, size, Sort.by(sortByProperty));

    if (email != null) {
      email = email.toUpperCase();
    }
    Page<UserDto> dtos = this.userRepositoryPostgreSql.findAllByEmailLikeAndRoles_Title(email, role, pageable).map(Utility::userToDto);

    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable(name = "id")UUID id) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = this.userService.findUserById(id);
    // Maybe put this logic in findUser
    if (!user.getEmail().equals(authentication.getName()) && !UtilitySecurity.userHasAdminRole()) {
      return new ResponseEntity<>("User doesn't have the permissions to request another users information", HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>(Utility.userToDtoWithCartItems(user), HttpStatus.OK);
  }

  @PostMapping()
  public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) throws Exception {
    return new ResponseEntity<>(Utility.userToDto(this.userService.createUser(userDto)), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@PathVariable(name = "id") UUID id, @Valid @RequestBody UserDtoUpdate userDto) throws Exception {
    return new ResponseEntity<>(Utility.userToDto(this.userService.updateUser(userDto, id)), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable(name = "id") UUID id) throws Exception {
    this.userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

//  @PutMapping("/{id}/restore")
//  public ResponseEntity<?> restoreUser(@PathVariable(name = "id") UUID id) {
//    this.userService.deleteUser(id);
//    return new ResponseEntity<>(HttpStatus.OK);
//  }
}
