package com.adrian.simple_repositories.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.service.FileService;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.dto.FileDTO;
import com.adrian.simple_repositories.exception.FolderNotFoundException;
import com.adrian.simple_repositories.mapper.FileMapper;
import com.adrian.simple_repositories.repository.FileRepository;
import com.adrian.simple_repositories.repository.FolderRepository;

@Service
public class FileServiceImpl implements FileService {

  @Autowired
  private FileRepository fileRepository;
  private FolderRepository folderRepository;
  private FileMapper fileMapper;

  public FileServiceImpl(FileRepository fileRepository, FolderRepository folderRepository, FileMapper fileMapper) {
    this.fileRepository = fileRepository;
    this.folderRepository = folderRepository;
    this.fileMapper = fileMapper;
  }

  public FileDTO createFile(FileDTO fileDTO) {
    File file = fileMapper.toEntity(fileDTO);
    Folder folder = folderRepository.findById(fileDTO.getFolderId())
      .orElseThrow(() -> new FolderNotFoundException("Could not find folder with id: " + fileDTO.getFolderId()));
    file.setFolder(folder);
    try {
      File savedFile = fileRepository.save(file);
      return fileMapper.toDTO(savedFile);
    } catch(Exception e) {
      throw new RuntimeException("Could not save file to database: " + file);
    }
  }

  public List<FileDTO> createFiles(List<FileDTO> fileDTOs, Long folderId) {
    Folder folder = folderRepository.findById(folderId)
      .orElseThrow(() -> new FolderNotFoundException("Could not find folder with id: " + folderId));

    File file;
    List<File> files = new ArrayList<>();
    for(FileDTO fileDTO : fileDTOs) {
      file = fileMapper.toEntity(fileDTO);
      file.setFolder(folder);
      files.add(file);
    }

    try {
      List<File> savedFiles = fileRepository.saveAll(files);
      return savedFiles.stream()
        .map(fileMapper::toDTO)
        .collect(Collectors.toList());
    } catch (Exception e) {
      throw new RuntimeException("Could not save collection of files: "+ files);
    }
  }

  public List<FileDTO> getAllFiles() {
    List<File> files = fileRepository.findAll();
    if(files.isEmpty()) {
      throw new RuntimeException("Could not fetch files from database");
    }
    return files.stream()
      .map(fileMapper::toDTO)
      .collect(Collectors.toList());
  }

  public List<FileDTO> getAllFilesByFolder(Long folderId) {
    List<File> files = fileRepository.findAllByFolderId(folderId) //TODO change query to not return Optional
      .orElseThrow(() -> new RuntimeException("Could not find files with folder id: " + folderId));
    
    if(files.isEmpty()) { //TODO Fix FileNotFoundException
      throw new RuntimeException("Could not find files with folder id: " + folderId);
    }
    return files.stream()
      .map(fileMapper::toDTO)
      .collect(Collectors.toList());
  }

  public FileDTO getFileById(Long fileId) {
    File file = fileRepository.findById(fileId) //TODO Fix FileNotFoundException
      .orElseThrow(() -> new RuntimeException("Could not find file with id: " + fileId));
    return fileMapper.toDTO(file);
  }

  public FileDTO updateFile(FileDTO fileDTO) {
    File file = fileRepository.findById(fileDTO.getId()) //TODO Fix FileNotFoundException
      .orElseThrow(() -> new RuntimeException("Could not find file with id: "+ fileDTO.getId()));

    file.setFileName(fileDTO.getFileName());
    file.setExtension(fileDTO.getExtension());
    file.setContent(fileDTO.getContent());
    file.setFolder(getFolderById(fileDTO.getFolderId()));
    
    try {
      File updatedFile = fileRepository.save(file);
      return fileMapper.toDTO(updatedFile);
    } catch (Exception e) {
      throw new RuntimeException("Could not update file: " + file);
    } 
  }

  public FileDTO updateFileContent(byte[] content, Long fileId) {
    File file = fileRepository.findById(fileId) //TODO Fix FileNotFoundException
      .orElseThrow(() -> new RuntimeException("Could not find file with id: " + fileId));
    
    file.setContent(content);
    try {
      File updatedFile = fileRepository.save(file);
      return fileMapper.toDTO(updatedFile);
    } catch (Exception e) { 
      throw new RuntimeException("Could not update file: " + file); 
    }
  }

  public void deleteFileById(Long fileId) {
    try {
      fileRepository.deleteById(fileId);
    } catch (Exception e) {
      throw new RuntimeException("Could not delete file with id: " + fileId);
    }
  }

  private Folder getFolderById(Long folderId) {
    Folder folder = folderRepository.findById(folderId)
      .orElseThrow(() -> new FolderNotFoundException("Could not find folder with id: " + folderId));
    return folder;
  }
}
