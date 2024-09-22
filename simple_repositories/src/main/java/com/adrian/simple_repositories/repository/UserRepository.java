package com.adrian.simple_repositories.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrian.simple_repositories.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
