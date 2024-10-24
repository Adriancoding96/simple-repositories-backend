package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.folder.FolderFullDTO;
import com.adrian.simple_repositories.dto.folder.FolderUpdateDTO;
import com.adrian.simple_repositories.dto.folder.FolderDTO;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;

public interface FolderService {
  Folder createFolderFromPush(FolderFullDTO folderDTO, Project project);
  Folder getFolderById(Long folderId);
  Folder getFolderByUuid(String uuid);
  FolderFullDTO getFolderAsDTOByUuid(String uuid);
  List<Folder> getAllFoldersByProjectUuid(String projectUuid);
  List<FolderDTO> getAllFoldersAsDTOsByProjectUuid(String projectUuid);
  FolderDTO updateFolder(String uuid, FolderUpdateDTO updateDTO);
  void deleteFolderByUuid(String uuid);
}
