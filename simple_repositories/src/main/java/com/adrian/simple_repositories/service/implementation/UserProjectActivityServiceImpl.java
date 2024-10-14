package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.model.UserProjectActivity;
import com.adrian.simple_repositories.repository.UserProjectActivityRepository;
import com.adrian.simple_repositories.service.UserProjectActivityService;

@Service
public class UserProjectActivityServiceImpl implements UserProjectActivityService {

  private final UserProjectActivityRepository userProjectActivityRepository;

  @Autowired
  public UserProjectActivityServiceImpl(UserProjectActivityRepository userProjectActivityRepository) {
    this.userProjectActivityRepository = userProjectActivityRepository;
  }

  @Override
  public UserProjectActivity createUserProjectActivity(UserProjectActivity activity) {
    UserProjectActivity savedActivity = userProjectActivityRepository.save(activity);
    return savedActivity;
  }
  

}
