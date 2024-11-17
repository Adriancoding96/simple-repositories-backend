package com.adrian.simple_repositories.service.implementation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.exception.BranchNotFoundException;
import com.adrian.simple_repositories.mapper.BranchMapper;
import com.adrian.simple_repositories.model.Branch;
import com.adrian.simple_repositories.model.Repo;
import com.adrian.simple_repositories.repository.BranchRepository;
import com.adrian.simple_repositories.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {


  //TODO Figure out if i have to inject repo service in to branch service
  private final BranchRepository branchRepository;
  private final BranchMapper branchMapper;

  @Autowired
  public BranchServiceImpl(BranchRepository branchRepository, BranchMapper branchMapper) {
    this.branchRepository = branchRepository;
    this.branchMapper = branchMapper;
  } 

  @Override
  public Branch createMainBranch(Repo repo) {
    Branch branch = new Branch();
    branch.setBranchName("main");
    branch.setLatestPushToBranch(LocalDateTime.now());
    branch.setRepo(repo);
    return branchRepository.save(branch);
  }

  @Override
  public Branch createBranch(BranchDTO branchDTO, Repo repo) {
    Branch branch = branchMapper.toEntity(branchDTO);
    branch.setRepo(repo);
    return branchRepository.save(branch);
  }

  @Override
  public Branch getBranchById(Long branchId) {
    return branchRepository.findById(branchId)
      .orElseThrow(() -> new BranchNotFoundException("Branch with id: " + branchId + " not found")); 
  }
}
