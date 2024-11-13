package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.model.RepoVersion;

public interface RepoVersionService {
  RepoVersion getRepoVersionByOldUuid(String oldUuid);
  boolean checkIfRepoExistsByOldUuid(String oldUuid);
}
