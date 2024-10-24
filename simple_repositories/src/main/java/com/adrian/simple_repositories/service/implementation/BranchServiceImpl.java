package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.exception.BranchNotFoundException;
import com.adrian.simple_repositories.mapper.BranchMapper;
import com.adrian.simple_repositories.model.Branch;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.repository.BranchRepository;
import com.adrian.simple_repositories.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {


  //TODO Figure out if i have to inject project service in to branch service
  private final BranchRepository branchRepository;
  private final BranchMapper branchMapper;

  @Autowired
  public BranchServiceImpl(BranchRepository branchRepository, BranchMapper branchMapper) {
    this.branchRepository = branchRepository;
    this.branchMapper = branchMapper;
  } 

  public Branch createBranch(BranchDTO branchDTO, Project project) {
    Branch branch = branchMapper.toEntity(branchDTO);
    branch.setProject(project);
    return branchRepository.save(branch);
  }

  public Branch getBranchById(Long branchId) {
    return branchRepository.findById(branchId)
      .orElseThrow(() -> new BranchNotFoundException("Branch with id: " + branchId + " not found")); 
  }
}
