package org.zobic.ecommerceshopapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.repository.UserRepository;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
// add throwable exceptions
@Service
public class UserServiceImplementation implements UserService {

  private UserRepository userRepository;

  private PasswordEncoder passwordEncoder;

  private RoleService roleService;

  public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleServiceImplementation roleService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleService = roleService;
  }

  @Override
  public User registerUser(UserDto userDto) {

    String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
    User newUser = new User(userDto.getUsername(), encryptedPassword, userDto.getEmail(), Arrays.asList(this.roleService.findByTitle("ROLE_USER")));

    return this.userRepository.registerUser(newUser);
  }

  @Override
  public User updateUser(User user, UUID id) {
    return null;
  }

  @Override
  public void deleteUser(UUID id) {
    this.userRepository.deleteUser(id);
  }

  @Override
  public Optional<User> findUserById(UUID id) {
    return this.userRepository.findUserById(id);
  }

  @Override
  public Optional<User> findUserByUsername(String username) {
    return this.userRepository.findUserByUsername(username);
  }

  @Override
  public Optional<User> findUserByEmail(String email) {
    return this.userRepository.findUserByEmail(email);
  }

  @Override
  public Iterable<User> findAllUsers() {
    return this.userRepository.findAllUsers();
  }

  @Override
  public Page<User> findAllUsersPageable(Pageable pageable) {
    return this.userRepository.findAllUsersPageable(pageable);
  }

  @Override
  public void save(User user) {
    this.userRepository.registerUser(user);
  }
}
