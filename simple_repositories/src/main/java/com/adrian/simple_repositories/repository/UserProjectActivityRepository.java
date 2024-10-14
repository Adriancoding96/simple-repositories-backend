package com.adrian.simple_repositories.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrian.simple_repositories.model.UserProjectActivity;

public interface UserProjectActivityRepository extends JpaRepository<UserProjectActivity, Long> {

}
