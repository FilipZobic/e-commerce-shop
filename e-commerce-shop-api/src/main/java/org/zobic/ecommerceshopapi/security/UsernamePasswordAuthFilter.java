package org.zobic.ecommerceshopapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zobic.ecommerceshopapi.dto.CredentialsDto;
import org.zobic.ecommerceshopapi.service.UserDetailServiceImplementation;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  public void setUserDetailsService(UserDetailServiceImplementation userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse,
    FilterChain filterChain) throws ServletException, IOException {


    if ("/api/login".equals(httpServletRequest.getServletPath()) && HttpMethod.POST.matches(httpServletRequest.getMethod())) {
      CredentialsDto credentialsDto = MAPPER.readValue(httpServletRequest.getInputStream(), CredentialsDto.class);

        SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken(credentialsDto.getEmail(), credentialsDto.getPassword()));

    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
