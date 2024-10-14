package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.dto.FolderFullDTO;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;

public interface FolderService {
  Folder createFolderFromPush(FolderFullDTO folderDTO, Project project);
  Folder getFolderById(Long folderId);
}
