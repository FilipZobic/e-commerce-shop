package org.zobic.ecommerceshopapi.service;

import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.model.Privilege;
import org.zobic.ecommerceshopapi.model.Role;
import org.zobic.ecommerceshopapi.repository.RoleRepository;
import org.zobic.ecommerceshopapi.repository.RoleRepositoryPostgreSqlImplementation;

import java.util.Collection;
import java.util.Optional;

@Service
public class RoleServiceImplementation implements RoleService{

  private final RoleRepository roleRepository;

  public RoleServiceImplementation(RoleRepositoryPostgreSqlImplementation roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role findOrCreateIfNotFound(String title, Collection<Privilege> privileges) {

    Optional<Role> role = this.roleRepository.findByTitle(title);

    if (role.isPresent()) {
      return role.get();
    } else {
      return this.roleRepository.save(new Role(title, privileges));
    }

//    return this.roleRepository.findByTitle(title).orElse(this.roleRepository.save(new Role(title, privileges)));
  }

  @Override
  public Role findByTitle(String title) {
    return this.roleRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Role not found"));
  }
}
