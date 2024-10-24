package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.model.Branch;
import com.adrian.simple_repositories.model.Project;

public interface BranchService {
  Branch createBranch(BranchDTO branchDTO, Project project);
  Branch getBranchById(Long branchId);
}
