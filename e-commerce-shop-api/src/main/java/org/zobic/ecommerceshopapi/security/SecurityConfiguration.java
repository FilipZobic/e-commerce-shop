package org.zobic.ecommerceshopapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.zobic.ecommerceshopapi.service.UserDetailServiceImplementation;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private UserAuthenticationEntryPoint userAuthenticationEntryPoint;

  private UserDetailServiceImplementation userDetailServiceImplementation;

  private PasswordEncoder passwordEncoder;

  public SecurityConfiguration(UserAuthenticationEntryPoint userAuthenticationEntryPoint, UserDetailServiceImplementation userDetailServiceImplementation, PasswordEncoder passwordEncoder) {
    this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
    this.userDetailServiceImplementation = userDetailServiceImplementation;
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
      .userDetailsService(userDetailServiceImplementation)
      .passwordEncoder(passwordEncoder)
      .and().jdbcAuthentication();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
      .and()
      .addFilterBefore(new UsernamePasswordAuthFilter(), BasicAuthenticationFilter.class)
      .csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
      .and()
      .authorizeRequests()

      .antMatchers(HttpMethod.PUT, "/api/users/{id}").hasAuthority("MODIFY_SELF_USER_DATA_PRIVILEGE")
      .antMatchers(HttpMethod.GET, "/api/users/{id}").hasAuthority("READ_SELF_USER_DATA_PRIVILEGE")
      .antMatchers(HttpMethod.DELETE, "/api/users/{id}").hasAuthority("MODIFY_ALL_USER_DATA_PRIVILEGE")
      .antMatchers(HttpMethod.GET, "/api/users").hasAuthority("READ_ALL_USER_DATA_PRIVILEGE")
      .antMatchers(HttpMethod.POST, "/api/login", "/api/register").permitAll()
      .anyRequest().authenticated();
  }


  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    String hierarchy = "ROLE_ADMIN > ROLE_USER \n ROLE_USER > ROLE_GUEST";
    roleHierarchy.setHierarchy(hierarchy);
    return roleHierarchy;
  }

  @Bean
  public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
    DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
    expressionHandler.setRoleHierarchy(roleHierarchy());
    return expressionHandler;
  }
}