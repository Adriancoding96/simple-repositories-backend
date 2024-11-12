package com.adrian.simple_repositories.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.service.RepoService;
import com.adrian.simple_repositories.service.UserService;
import com.adrian.simple_repositories.dto.pull.PullResponseDTO;
import com.adrian.simple_repositories.dto.repo.RepoFullDTO;
import com.adrian.simple_repositories.mapper.ResponseMapper;
import com.adrian.simple_repositories.dto.pull.PullRequestDTO;
import com.adrian.simple_repositories.service.BranchService;

@Component
public class PullFacade {

  private final RepoService repoService;
  private final UserService userService;
  private final BranchService BranchService;
  private final ResponseMapper responseMapper;

  @Autowired
  public PullFacade(RepoService repoService, UserService userService, BranchService branchService, ResponseMapper responseMapper) {
    this.repoService = repoService;
    this.userService = userService;
    this.BranchService = branchService;
    this.responseMapper = responseMapper;
  }

  //Getting latest push by branch is gonne be handled by push facade 

  public PullResponseDTO pullRepo(PullRequestDTO requestDTO) {
    System.out.println("IMPORTANT UUID: " + requestDTO.getRepoUuid());
    RepoFullDTO repoDTO = repoService.getRepoAsDTOByUuidForPullRequest(requestDTO.getRepoUuid());
    return responseMapper.toPullResponseFromRepo(repoDTO);
  }


}
