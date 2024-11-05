package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryUpdateDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryDTO;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Project;

public interface DirectoryService {
  Directory createDirectoryFromPush(DirectoryFullDTO directoryDTO, Project project);
  Directory getDirectoryById(Long directoryId);
  Directory getDirectoryByUuid(String uuid);
  DirectoryFullDTO getDirectoryAsDTOByUuid(String uuid);
  List<Directory> getAllDirectoriesByProjectUuid(String projectUuid);
  List<DirectoryDTO> getAllDirectoriesAsDTOsByProjectUuid(String projectUuid);
  DirectoryDTO updateDirectory(String uuid, DirectoryUpdateDTO updateDTO);
  void deleteDirectoryByUuid(String uuid);
}
