package org.zobic.ecommerceshopapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.zobic.ecommerceshopapi.model.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryPostgreSql extends PagingAndSortingRepository <User, UUID> {

  @Query("SELECT u FROM User u WHERE u.email LIKE :email")
  Optional<User> findUserByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.id = :id")
  Optional<User> findUserByIdEnableFilters(UUID id);
}
