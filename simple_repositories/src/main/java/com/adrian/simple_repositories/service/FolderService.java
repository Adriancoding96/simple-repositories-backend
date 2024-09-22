package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.FolderDTO;

public interface FolderService {
  
  FolderDTO createFolder(FolderDTO folderDTO, Long projectId);
  List<FolderDTO> createFolders(List<FolderDTO> folders, Long projectId);
  List<FolderDTO> getAllFolders();
  List<FolderDTO> getAllSubFolders(Long parentFolderId);
  List<FolderDTO> getAllFoldersByProject(Long projectId);
  FolderDTO getFolderById(Long folderId);
  FolderDTO updateFolder(FolderDTO folderDTO);
  void deleteFolderById(Long id);

}
