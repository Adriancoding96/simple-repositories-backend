package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.model.Branch;
import com.adrian.simple_repositories.model.Repo;

public interface BranchService {
  Branch createBranch(BranchDTO branchDTO, Repo repo);
  Branch getBranchById(Long branchId);
}
