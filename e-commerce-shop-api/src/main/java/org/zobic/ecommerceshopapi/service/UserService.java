package org.zobic.ecommerceshopapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.dto.UserDtoUpdate;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.exception.TokenExpiredException;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.model.VerificationToken;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

  User registerUser(UserDto user) throws Exception;

  User updateUser(UserDtoUpdate userDto, UUID id) throws Exception;

  void deleteUser(UUID id) throws Exception;

  User findUserById(UUID id) throws Exception;

  Optional<User> findUserByUsername(String username);

  Optional<User> findUserByEmail(String email);

  Iterable<User> findAllUsers();

  Page<User> findAllUsersPageable(Pageable pageable);

  User createUser(UserDto userDto) throws Exception;

  VerificationToken createVerificationToken(User user);

  void confirmUser(UUID token) throws ResourceNotFoundException, TokenExpiredException;

  void destroyOldAndCreateNewVerificationToken(UUID expiredToken) throws Exception;
}
