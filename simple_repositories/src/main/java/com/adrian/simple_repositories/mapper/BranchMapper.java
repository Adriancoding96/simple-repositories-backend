package com.adrian.simple_repositories.mapper;

import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.model.Branch;

@Component
public class BranchMapper {

  public BranchDTO toDTO(Branch branch) {
    if(branch == null) return null;
    
    BranchDTO dto = new BranchDTO();
    dto.setId( branch.getId());
    dto.setBranchName(branch.getBranchName()); 
    dto.setRepoUuid(branch.getRepo().getUuid());
    if(branch.getLatestPushToBranch() != null) {
      dto.setLatestPushToBranch(branch.getLatestPushToBranch());
    }

    return dto;
  }

  public Branch toEntity(BranchDTO dto) {
    if(dto == null) return null;

    Branch branch = new Branch();
    branch.setId(dto.getId());
    branch.setBranchName(branch.getBranchName());
    if(dto.getLatestPushToBranch() != null) {
      branch.setLatestPushToBranch(dto.getLatestPushToBranch());
    }
    
    return branch;
  }
}
