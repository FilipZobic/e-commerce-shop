package org.zobic.ecommerceshopapi.repository;

import org.springframework.stereotype.Repository;
import org.zobic.ecommerceshopapi.model.Role;

import java.util.Optional;

@Repository
public class RoleRepositoryPostgreSqlImplementation implements RoleRepository {

  private final RoleRepositoryPostgreSql roleRepositoryPostgreSql;

  public RoleRepositoryPostgreSqlImplementation(RoleRepositoryPostgreSql roleRepositoryPostgreSql) {
    this.roleRepositoryPostgreSql = roleRepositoryPostgreSql;
  }

  @Override
  public Optional<Role> findByTitle(String title) {
    return this.roleRepositoryPostgreSql.findByTitle(title);
  }

  @Override
  public Role save(Role role) {
    return this.roleRepositoryPostgreSql.save(role);
  }
}
