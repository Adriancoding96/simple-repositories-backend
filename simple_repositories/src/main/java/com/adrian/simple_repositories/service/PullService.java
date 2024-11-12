package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.dto.pull.PullRequestDTO;
import com.adrian.simple_repositories.dto.pull.PullResponseDTO;

public interface PullService {
  PullResponseDTO pullRepo(PullRequestDTO requestDTO);
}
