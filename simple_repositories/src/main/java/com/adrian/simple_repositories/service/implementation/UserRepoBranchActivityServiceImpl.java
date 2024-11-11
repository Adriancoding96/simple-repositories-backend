package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.model.UserRepoBranchActivity;
import com.adrian.simple_repositories.repository.UserRepoBranchActivityRepository;
import com.adrian.simple_repositories.service.UserRepoBranchActivityService;

@Service
public class UserRepoBranchActivityServiceImpl implements UserRepoBranchActivityService {

  private final UserRepoBranchActivityRepository userRepoBranchActivityRepository;

  @Autowired
  public UserRepoBranchActivityServiceImpl(UserRepoBranchActivityRepository userRepoBranchActivityRepository) {
    this.userRepoBranchActivityRepository = userRepoBranchActivityRepository;
  }

  @Override
  public UserRepoBranchActivity createUserRepoBranchActivity(UserRepoBranchActivity activity) {
    UserRepoBranchActivity savedActivity = userRepoBranchActivityRepository.save(activity);
    return savedActivity;
  }
  

}
