package org.zobic.ecommerceshopapi.service;

import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.model.Privilege;
import org.zobic.ecommerceshopapi.repository.PrivilegeRepository;
import org.zobic.ecommerceshopapi.repository.PrivilegeRepositoryPostgreSqlImplementation;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class PrivilegeServiceImplementation implements PrivilegeService {

  private PrivilegeRepository privilegeRepository;

  public PrivilegeServiceImplementation(PrivilegeRepositoryPostgreSqlImplementation privilegeRepository) {
    this.privilegeRepository = privilegeRepository;
  }

  @Override
  public Privilege findOrCreateIfNotFound(String title) {
    Optional<Privilege> privilege = this.privilegeRepository.findByTitle(title);
    if (privilege.isPresent()) {
      return privilege.get();
    } else {
      return this.privilegeRepository.save(new Privilege(title));
    }
//    return this.privilegeRepository.findByTitle(title).orElse(this.privilegeRepository.save(new Privilege(title)));
  }
}
