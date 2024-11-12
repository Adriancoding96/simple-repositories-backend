package com.adrian.simple_repositories.service.implementation;

import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.dto.pull.PullRequestDTO;
import com.adrian.simple_repositories.dto.pull.PullResponseDTO;
import com.adrian.simple_repositories.facade.PullFacade;
import com.adrian.simple_repositories.service.PullService;

@Service
public class PullServiceImpl implements PullService {

  private final PullFacade pullFacade;

  public PullServiceImpl(PullFacade pullFacade) {
    this.pullFacade = pullFacade;
  }

  @Override
  public PullResponseDTO pullRepo(PullRequestDTO requestDTO) {
    return pullFacade.pullRepo(requestDTO);
  }
}
