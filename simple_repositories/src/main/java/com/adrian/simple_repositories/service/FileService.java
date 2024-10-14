package com.adrian.simple_repositories.service;


import com.adrian.simple_repositories.dto.FileDTO;
import com.adrian.simple_repositories.dto.PushResponseDTO;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.File;

public interface FileService {

  File createFileFromPush(FileDTO fileDTO, Folder parentFolder);
}
