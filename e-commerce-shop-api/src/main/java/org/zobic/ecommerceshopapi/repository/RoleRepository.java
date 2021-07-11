package org.zobic.ecommerceshopapi.repository;

import org.zobic.ecommerceshopapi.model.Role;

import java.util.Optional;


public interface RoleRepository {

  Optional<Role> findByTitle(String title);

  Role save(Role role);
}
