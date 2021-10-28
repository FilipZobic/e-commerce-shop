package org.zobic.ecommerceshopapi.repository;

import org.zobic.ecommerceshopapi.model.Privilege;

import java.util.Optional;

public interface PrivilegeRepository {

  Optional<Privilege> findByTitle(String title);

  Privilege save(Privilege privilege);
}
