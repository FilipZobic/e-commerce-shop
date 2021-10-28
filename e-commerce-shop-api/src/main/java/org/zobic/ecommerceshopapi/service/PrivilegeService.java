package org.zobic.ecommerceshopapi.service;

import org.zobic.ecommerceshopapi.model.Privilege;

public interface PrivilegeService {

  Privilege findOrCreateIfNotFound(String title);
}
