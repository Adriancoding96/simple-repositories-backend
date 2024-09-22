package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.FileDTO;

public interface FileService {
  FileDTO createFile(FileDTO fileDTO);
  List<FileDTO> createFiles(List<FileDTO> fileDTOs, Long folderId);
  List<FileDTO> getAllFiles();
  List<FileDTO> getAllFilesByFolder(Long folderId);
  FileDTO getFileById(Long fileId);
  FileDTO updateFile(FileDTO fileDTO);
  FileDTO updateFileContent(byte[] content, Long fileId);
  void deleteFileById(Long fileId);
}
