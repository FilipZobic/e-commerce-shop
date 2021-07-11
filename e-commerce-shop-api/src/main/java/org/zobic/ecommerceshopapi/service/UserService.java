package org.zobic.ecommerceshopapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

  User registerUser(UserDto user);

  User updateUser(User user, UUID id);

  void deleteUser(UUID id);

  Optional<User> findUserById(UUID id);

  Optional<User> findUserByUsername(String username);

  Optional<User> findUserByEmail(String email);

  Iterable<User> findAllUsers();

  Page<User> findAllUsersPageable(Pageable pageable);

  void save(User user);
}
