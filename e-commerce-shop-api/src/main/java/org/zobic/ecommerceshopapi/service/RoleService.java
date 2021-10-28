package org.zobic.ecommerceshopapi.service;

import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.Privilege;
import org.zobic.ecommerceshopapi.model.Role;

import java.util.Collection;

public interface RoleService {

  Role findOrCreateIfNotFound(String title, Collection<Privilege> privileges);

  Role findByTitle(String title) throws ResourceNotFoundException;
}
