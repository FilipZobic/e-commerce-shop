package org.zobic.ecommerceshopapi.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.zobic.ecommerceshopapi.service.UserDetailServiceImplementation;

import javax.servlet.http.HttpServletRequest;

@Component
public class UtilitySecurity {
  private UserDetailsService userDetailsService;

  private HttpServletRequest servletRequest;

  public UtilitySecurity(UserDetailServiceImplementation userDetailsService, HttpServletRequest servletRequest) {
    this.userDetailsService = userDetailsService;
    this.servletRequest = servletRequest;
  }

  public static boolean userHasAdminRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
  }

  public void updateSecurityContext(String username) {
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken authentication =
      new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    authentication
      .setDetails(new WebAuthenticationDetailsSource()
        .buildDetails(servletRequest));

    SecurityContextHolder
      .getContext()
      .setAuthentication(authentication);

  }
}
