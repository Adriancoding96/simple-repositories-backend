package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.UniqueIdentifierDTO;
import com.adrian.simple_repositories.dto.repo.RepoDTO;
import com.adrian.simple_repositories.dto.repo.RepoFullDTO;
import com.adrian.simple_repositories.dto.repo.RepoIdentifierRequestDTO;
import com.adrian.simple_repositories.dto.repo.RepoInformationDTO;
import com.adrian.simple_repositories.dto.repo.RepoSetupDTO;
import com.adrian.simple_repositories.dto.repo.RepoUpdateDTO;
import com.adrian.simple_repositories.model.Repo;
import com.adrian.simple_repositories.model.User;

public interface RepoService {

  RepoDTO createEmptyRepo(RepoSetupDTO setupDTO);

  Repo createRepoFromPush(RepoFullDTO repoDTO, User user);

  Repo getRepoById(Long repoId);
 
  Repo getRepoByUuid(String uuid);

  RepoFullDTO getRepoAsDTOByUuid(String uuid);

  List<RepoInformationDTO> getAllReposAsInfoDTOs();

  Repo getRepoByRepoNameAndUserEmail(RepoIdentifierRequestDTO request);

  UniqueIdentifierDTO getRepoIdentiferByRepoNameAndUserEmail(RepoIdentifierRequestDTO request);

  RepoDTO updateRepo(RepoUpdateDTO updateDTO, String uuid);

  void deleteRepoByUuid(String uuid);
}
