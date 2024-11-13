package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.exception.RepoVersionNotFoundException;
import com.adrian.simple_repositories.model.RepoVersion;
import com.adrian.simple_repositories.repository.RepoVersionRepository;
import com.adrian.simple_repositories.service.RepoVersionService;

@Service
public class RepoVersionServiceImpl implements RepoVersionService {

  private final RepoVersionRepository repoVersionRepository;

  @Autowired
  public RepoVersionServiceImpl(RepoVersionRepository repoVersionRepository) {
    this.repoVersionRepository = repoVersionRepository;
  }

  public RepoVersion getRepoVersionByOldUuid(String oldUuid) {
    return repoVersionRepository.findByOldUuid(oldUuid)
      .orElseThrow(() -> new RepoVersionNotFoundException("RepoVersion not found by uuid: " + oldUuid));
  }

  public boolean checkIfRepoExistsByOldUuid(String oldUuid) {
    return repoVersionRepository.existsByOldUuid(oldUuid);
  }
}
