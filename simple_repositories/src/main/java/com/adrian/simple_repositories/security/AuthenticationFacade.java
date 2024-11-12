package com.adrian.simple_repositories.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/*
 * AuthenticationFacade handles SecurityContext data
 */
@Component
public class AuthenticationFacade {

  /*
   *  Gets Authentication object from SecurityContext
   *
   *  @return authentication: Authentication object containing auth information
   */
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  } 
}
