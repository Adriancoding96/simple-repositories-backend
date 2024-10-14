package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.assembler.FileAssembler;
import com.adrian.simple_repositories.dto.FileDTO;
import com.adrian.simple_repositories.mapper.FileMapper;
import com.adrian.simple_repositories.mapper.ResponseMapper;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.repository.FileRepository;
import com.adrian.simple_repositories.service.FileService;

@Service
public class FileServiceImpl implements FileService {

  private final FileRepository fileRepository;
  private final FileAssembler fileAssembler;
  private final FileMapper fileMapper;
  private final ResponseMapper responseMapper;

  @Autowired
  public FileServiceImpl (FileRepository fileRepository, FileAssembler fileAssembler, FileMapper fileMapper, ResponseMapper responseMapper) {
    this.fileRepository = fileRepository;
    this.fileAssembler = fileAssembler;
    this.fileMapper = fileMapper;
    this.responseMapper = responseMapper;
  }


  public File createFileFromPush(FileDTO fileDTO, Folder parentFolder) {
    File file = fileAssembler.assemble(fileDTO);
    file.setFolder(parentFolder);
    File savedFile = fileRepository.save(file);
    return savedFile;
  }
}
