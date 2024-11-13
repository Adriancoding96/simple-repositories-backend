package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.file.FileUpdateDTO;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.File;

public interface FileService {

  File createFileFromPush(FileDTO fileDTO, Directory parentDirectory);
  File assemleFileFromPush(FileDTO fileDTO, Directory parentDirectory);
  File getFileById(Long fileId);
  File getFileByUuid(String uuid);
  FileDTO getFileAsDTOByUuid(String uuid);
  List<File> getAllFilesByRepoUuid(String repoUuid);
  List<FileDTO> getAllFilesAsDTOsByRepoUuid(String repoUuid);
  FileDTO updateFile(FileUpdateDTO updateDTO, String uuid);
  void deleteFileByUuid(String uuid);
}
