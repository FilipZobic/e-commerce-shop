package org.zobic.ecommerceshopapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.zobic.ecommerceshopapi.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryPostgreSqlImplementation implements UserRepository {

  private UserRepositoryPostgreSql userRepositoryPostgreSql;

  public UserRepositoryPostgreSqlImplementation(UserRepositoryPostgreSql userRepositoryPostgreSql) {
    this.userRepositoryPostgreSql = userRepositoryPostgreSql;
  }

  @Override
  public User registerUser(User user) {
    return this.userRepositoryPostgreSql.save(user);
  }

  @Override
  public User updateUser(User user, UUID id) {
    user.setId(id);
    return this.userRepositoryPostgreSql.save(user);
  }

  @Override
  public void deleteUser(UUID id) {
    this.userRepositoryPostgreSql.deleteById(id);
  }

  @Override
  public Optional<User> findUserById(UUID id) {
    return this.userRepositoryPostgreSql.findUserByIdEnableFilters(id);
  }

  @Override
  public Optional<User> findUserByEmail(String email) {
    return this.userRepositoryPostgreSql.findUserByEmail(email);
  }

  @Override
  public Iterable<User> findAllUsers() {
    return this.userRepositoryPostgreSql.findAll();
  }

  @Override
  public Page<User> findAllUsersPageable(Pageable pageable) {
    return this.userRepositoryPostgreSql.findAll(pageable);
  }
}
