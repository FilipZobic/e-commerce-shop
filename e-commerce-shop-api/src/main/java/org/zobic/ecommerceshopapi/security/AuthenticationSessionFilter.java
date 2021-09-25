package org.zobic.ecommerceshopapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.zobic.ecommerceshopapi.session.Session;
import org.zobic.ecommerceshopapi.session.SessionService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationSessionFilter extends UsernamePasswordAuthenticationFilter {

  @Autowired
  private SessionService sessionService;

  public static final String X_ACCESS_TOKEN = "X-ACCESS-TOKEN";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest)request;

    String header = servletRequest.getHeader(X_ACCESS_TOKEN);

    if (header != null) {
      Session session = sessionService.getSessionById(header);
      if (session != null) {
        UserDetails userDetails = session.getUserDetails();

        if ((userDetails != null) && (SecurityContextHolder.getContext().getAuthentication() == null)) {
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
    }
    super.doFilter(request, response, chain);
  }
}
