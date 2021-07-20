package org.zobic.ecommerceshopapi.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.model.Privilege;
import org.zobic.ecommerceshopapi.model.Role;
import org.zobic.ecommerceshopapi.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

  private final UserDetailsHelperService userService;

  private final RoleService roleService;

  public UserDetailServiceImplementation(UserDetailsHelperService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = this.userService.findUserByUsername(username).orElse(null);
    if (user == null) {
      return null;
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,true,true,true,getAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(
    Collection<Role> roles) {
    return getGrantedAuthorities(getPrivileges(roles));
  }

  private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }
    return authorities;
  }

  private List<String> getPrivileges(Collection<Role> roles) {

    List<String> privileges = new ArrayList<>();
    List<Privilege> collection = new ArrayList<>();
    for (Role role : roles) {
      privileges.add(role.getTitle());
      collection.addAll(role.getPrivileges());
    }
    for (Privilege item : collection) {
      privileges.add(item.getTitle());
    }
    return privileges;
  }
}
