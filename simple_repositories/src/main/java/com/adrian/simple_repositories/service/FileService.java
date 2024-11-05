package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.file.FileUpdateDTO;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.File;

public interface FileService {

  File createFileFromPush(FileDTO fileDTO, Directory parentDirectory);
  File getFileById(Long fileId);
  File getFileByUuid(String uuid);
  FileDTO getFileAsDTOByUuid(String uuid);
  List<File> getAllFilesByProjectUuid(String projectUuid);
  List<FileDTO> getAllFilesAsDTOsByProjectUuid(String projectUuid);
  FileDTO updateFile(FileUpdateDTO updateDTO, String uuid);
  void deleteFileByUuid(String uuid);
}
