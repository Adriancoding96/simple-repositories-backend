package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.model.Branch;
import com.adrian.simple_repositories.model.Repo;

public interface BranchService {
  Branch createMainBranch(Repo repo);
  Branch createBranch(String branchName, Repo repo);
  Branch getBranchById(Long branchId);
  Branch getBranchByNameAndRepoUuid(String branchName, String repoUuid);
  boolean doesBranchExistByNameAndRepoUuid(String branchName, String repoUuid);
}
