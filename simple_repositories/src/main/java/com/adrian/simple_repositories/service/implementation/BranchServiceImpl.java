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
  public Branch createBranch(String branchName, Repo repo) {
    Branch branch = new Branch();
    branch.setBranchName(branchName);
    branch.setRepo(repo);
    branch.setLatestPushToBranch(LocalDateTime.now());
    return branchRepository.save(branch);
  }

  @Override
  public Branch getBranchById(Long branchId) {
    return branchRepository.findById(branchId)
      .orElseThrow(() -> new BranchNotFoundException("Branch with id: " + branchId + " not found")); 
  }

  @Override
  public Branch getBranchByNameAndRepoUuid(String branchName, String repoUuid) {
    return branchRepository.findBranchByNameAndRepoUuid(branchName, repoUuid)
      .orElseThrow(() -> new BranchNotFoundException("Could not find branch by name and repoUuid"));
  }

  @Override
  public boolean doesBranchExistByNameAndRepoUuid(String branchName, String repoUuid) {
    long count = branchRepository.countByBranchNameAndRepoUuid(branchName, repoUuid); //TODO implement custom exception
    if(count > 1) throw new RuntimeException("Multiple branches with the same name pointing to the same repository");
    if(count == 0) return false; 
    return true;
  }
}
