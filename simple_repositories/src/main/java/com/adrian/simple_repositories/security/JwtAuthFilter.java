package com.adrian.simple_repositories.security;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.adrian.simple_repositories.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private UserDetailsService userDetailsService;
  private ObjectMapper objectMapper;

  @Autowired
  public JwtAuthFilter(UserDetailsService userDetailsService, ObjectMapper objectMapper) {
    this.userDetailsService = userDetailsService;
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException {
    try {
      String authHeader = request.getHeader("Authorization");
      String token = null;
      String username = null;
      if(authHeader != null && authHeader.startsWith("Bearer ")) {
        System.out.println("Autherheader recived");
        token = authHeader.substring(7);
        username = JwtHelper.extractUsername(token);
      }

      if(token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        System.out.println("Test two");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(JwtHelper.validateToken(token, userDetails)) {
          System.out.println("Token recieved, Username: " + userDetails.getUsername());
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    
      filterChain.doFilter(request, response);
    } catch (AccessDeniedException e) {
      ApiErrorResponse errorResponse = new ApiErrorResponse(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      writeErrorResponse(response, errorResponse);
    } catch (IOException e) {
      ApiErrorResponse errorResponse = new ApiErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "I/O error occurred");
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      writeErrorResponse(response, errorResponse);
    }
  }
  
  private void writeErrorResponse(HttpServletResponse response, ApiErrorResponse errorResponse) {
      try {
          response.getWriter().write(toJson(errorResponse));
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  private String toJson(ApiErrorResponse response) {
    try {
      return objectMapper.writeValueAsString(response);
    } catch (Exception e) {
      return "";
    }
  }
}
