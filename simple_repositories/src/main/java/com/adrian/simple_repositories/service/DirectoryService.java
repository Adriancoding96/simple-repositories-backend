package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryUpdateDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryDTO;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Repo;

public interface DirectoryService {
  Directory createDirectoryFromPush(DirectoryFullDTO directoryDTO, Repo repo);
  Directory assembleRootDirectoryFromPush(DirectoryFullDTO directoryDTO, Repo repo);
  Directory assembleDirectoryFromPush(DirectoryFullDTO directoryDTO, Repo repo);
  Directory getDirectoryById(Long directoryId);
  Directory getDirectoryByUuid(String uuid);
  DirectoryFullDTO getDirectoryAsDTOByUuid(String uuid);
  List<Directory> getAllDirectoriesByRepoUuid(String repoUuid);
  List<DirectoryDTO> getAllDirectoriesAsDTOsByRepoUuid(String repoUuid);
  DirectoryDTO updateDirectory(String uuid, DirectoryUpdateDTO updateDTO);
  void deleteDirectoryByUuid(String uuid);
}
