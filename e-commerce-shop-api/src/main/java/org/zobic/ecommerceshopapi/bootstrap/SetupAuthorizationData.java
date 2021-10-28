package org.zobic.ecommerceshopapi.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.zobic.ecommerceshopapi.dto.AddressDto;
import org.zobic.ecommerceshopapi.dto.UserDto;
import org.zobic.ecommerceshopapi.model.Privilege;
import org.zobic.ecommerceshopapi.model.Role;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
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

    Optional<User> initialAdmin = userServiceImplementation.findUserByEmail("admin@gmail.com");

    if (initialAdmin.isEmpty()) {
      log.warn("Default admin does not exist attempting to create");
      try {
        this.userServiceImplementation.createUser(
          UserDto.builder()
            .fullName("Admin admin")
            .address(AddressDto.builder()
              .addressValue("Example street")
              .cityName("Example city name")
              .countryAlpha2Code("RS")
              .cityName("Novi Sad")
              .zipCode("21000")
              .build())
            .role("ROLE_ADMIN")
            .email("admin@gmail.com")
            .password("123456")
            .isEnabled(true)
            .build()
        );
        log.info("Default admin created successfully");
      } catch (Exception e){
        log.error("Failed creating default admin");
      }
    }

    Optional<User> initialUser = userServiceImplementation.findUserByEmail("user@gmail.com");

    if (initialUser.isEmpty()) {
      log.warn("Default user does not exist attempting to create");
      try {
        this.userServiceImplementation.createUser(
          UserDto.builder()
            .fullName("User user")
            .address(AddressDto.builder()
              .addressValue("Example street")
              .cityName("Example city name")
              .countryAlpha2Code("RS")
              .cityName("Novi Sad")
              .zipCode("21000")
              .build())
            .role("ROLE_USER")
            .email("user@gmail.com")
            .password("123456")
            .isEnabled(true)
            .build()
        );
        log.info("Default user created successfully");
      } catch (Exception e){
        log.error("Failed creating default user");
      }
    }

    alreadySetup = true;
  }
}
