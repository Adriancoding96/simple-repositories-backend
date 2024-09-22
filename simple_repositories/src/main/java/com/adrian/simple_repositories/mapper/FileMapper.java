package com.adrian.simple_repositories.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.repository.FolderRepository;
import com.adrian.simple_repositories.util.ValidationUtils;
import com.adrian.simple_repositories.model.File; 
import com.adrian.simple_repositories.model.Folder; 
import com.adrian.simple_repositories.dto.FileDTO;
import com.adrian.simple_repositories.exception.FolderNotFoundException;

@Service
public class FileMapper {

  private FolderRepository folderRepository;

  @Autowired
  public FileMapper(FolderRepository folderRepository) {
    this.folderRepository = folderRepository;
  }

  public FileDTO toDTO(File file) {
    ValidationUtils.validateObject(file);

    FileDTO fileDTO = new FileDTO();
    fileDTO.setId(file.getId());
    fileDTO.setFileName(file.getFileName());
    fileDTO.setExtension(file.getExtension());
    fileDTO.setContent(file.getContent());
    if(file.getFolder().getId() != null) {
      fileDTO.setFolderId(file.getFolder().getId());
    }
    return fileDTO;
  }

  public File toEntity(FileDTO fileDTO) {
    ValidationUtils.validateObject(fileDTO);
    
    File file = new File();
    file.setId(fileDTO.getId());
    file.setFileName(fileDTO.getFileName());
    file.setExtension(fileDTO.getExtension());
    file.setContent(fileDTO.getContent());
    /*
    if(fileDTO.getFolderId() != null) {
      file.setFolder(getFolder(fileDTO.getFolderId()));
    }*/
    return file;
  }

  private Folder getFolder(Long folderId) {
    Folder folder = folderRepository.findById(folderId)
      .orElseThrow(() -> new FolderNotFoundException("Folder not found with id: " + folderId));
    return folder;
  }

}
