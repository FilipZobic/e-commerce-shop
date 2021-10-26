package org.zobic.ecommerceshopapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zobic.ecommerceshopapi.model.Laptop;
import org.zobic.ecommerceshopapi.model.Role;
import org.zobic.ecommerceshopapi.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryPostgreSql extends PagingAndSortingRepository <User, UUID> {

  @Query("SELECT u FROM User u WHERE u.email LIKE :email")
  Optional<User> findUserByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.id = :id")
  Optional<User> findUserByIdEnableFilters(UUID id);

  @Query("SELECT DISTINCT user FROM User user " +
    "LEFT OUTER JOIN ApplicationUserRole aur ON user.id = aur.id.userId " +
    "LEFT OUTER JOIN Role role ON role.id = aur.id.roleId " +
    "WHERE (:email IS NULL OR UPPER(user.email) LIKE %:email% ) " +
    "AND (:title IS NUll OR role.title = :title)"
  )
  Page<User> findAllByEmailLikeAndRoles_Title(String email, String title, Pageable pageable);

//    @Query("SELECT DISTINCT user FROM User user WHERE " +
//    "(:email IS NULL OR UPPER(user.email) LIKE %:email% )"
//  )
//  Page<User> findAllByEmailLike(String email, Pageable pageable);

//  "select * from application_user user0_ left outer join application_user_role roles1_ on user0_.id=roles1_.user_id left outer join role role2_ on roles1_.role_id=role2_.id where (user0_.email like ? escape ?) and role2_.title=?"
//  Hibernate: select * as email3_1_, user0_.enabled as enabled4_1_, user0_.full_name as full_nam5_1_, user0_.password as password6_1_
//  from application_user user0_ left outer join application_user_role applicatio1_ on (user0_.id=applicatio1_.user_id) left outer join role role2_ on (role2_.id=applicatio1_.role_id) where (? is null or upper(user0_.email) like ?) and (? is null or role2_.title=?) order by user0_.email asc limit ?

}

// = :role in user.roles OR
