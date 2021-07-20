package org.zobic.ecommerceshopapi.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.zobic.ecommerceshopapi.model.Privilege;
import org.zobic.ecommerceshopapi.model.Role;
import org.zobic.ecommerceshopapi.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SetupAuthorizationData implements ApplicationListener<ContextRefreshedEvent> {

  boolean alreadySetup = false;

  private final PrivilegeService privilegeService;

  private final RoleService roleService;

  @Autowired
  private UserServiceImplementation userServiceImplementation;

  public SetupAuthorizationData(PrivilegeServiceImplementation privilegeService, RoleServiceImplementation roleService) {
    this.privilegeService = privilegeService;
    this.roleService = roleService;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    if (alreadySetup) {
      return;
    }

    Privilege readAllUserDataPrivilege = this.privilegeService.findOrCreateIfNotFound("READ_ALL_USER_DATA_PRIVILEGE");
    Privilege writeAllUserDataPrivilege = this.privilegeService.findOrCreateIfNotFound("MODIFY_ALL_USER_DATA_PRIVILEGE");

    Privilege readSelfUserDataPrivilege = this.privilegeService.findOrCreateIfNotFound("READ_SELF_USER_DATA_PRIVILEGE");
    Privilege writeSelfUserDataPrivilege = this.privilegeService.findOrCreateIfNotFound("MODIFY_SELF_USER_DATA_PRIVILEGE");

    List<Privilege> adminPrivileges = Arrays.asList(readAllUserDataPrivilege, writeAllUserDataPrivilege);
    Role roleAdmin = this.roleService.findOrCreateIfNotFound("ROLE_ADMIN", adminPrivileges);

    List<Privilege> userPrivileges = Arrays.asList(readSelfUserDataPrivilege, writeSelfUserDataPrivilege);
    Role roleUser = this.roleService.findOrCreateIfNotFound("ROLE_USER", userPrivileges);

    List<Privilege> guestPrivileges = new ArrayList<>();
    Role guestRole = this.roleService.findOrCreateIfNotFound("ROLE_GUEST", guestPrivileges);
    alreadySetup = true;
  }
}
