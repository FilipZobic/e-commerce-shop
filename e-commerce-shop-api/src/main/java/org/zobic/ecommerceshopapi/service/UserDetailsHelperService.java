package org.zobic.ecommerceshopapi.service;

import org.zobic.ecommerceshopapi.model.User;

import java.util.Optional;

public interface UserDetailsHelperService {
  public Optional<User> findUserByEmail(String email);
}
