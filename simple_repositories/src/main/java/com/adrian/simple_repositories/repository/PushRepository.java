package com.adrian.simple_repositories.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrian.simple_repositories.model.Push;

public interface PushRepository extends JpaRepository<Push, Long> {

}
