package com.adrian.simple_repositories.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.service.RepoService;
import com.adrian.simple_repositories.service.UserService;
import com.adrian.simple_repositories.service.BranchService;

@Component
public class PullFacade {

  private final RepoService repoService;
  private final UserService userService;
  private final BranchService BranchService;

  @Autowired
  public PullFacade(RepoService repoService, UserService userService, BranchService branchService) {
    this.repoService = repoService;
    this.userService = userService;
    this.BranchService = branchService;
  }

  //Method that checks if repo needs to be pulled based on latest push to branch
   
  //Method to fetch repo


}
