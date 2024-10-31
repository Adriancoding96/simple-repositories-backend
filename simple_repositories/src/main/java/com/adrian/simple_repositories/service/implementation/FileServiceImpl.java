package com.adrian.simple_repositories.service.implementation;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.assembler.FileAssembler;
import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.file.FileUpdateDTO;
import com.adrian.simple_repositories.mapper.FileMapper;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.repository.FileRepository;
import com.adrian.simple_repositories.service.FileService;
import com.adrian.simple_repositories.util.FilePathUtil;

import jakarta.transaction.Transactional;

import com.adrian.simple_repositories.exception.FileNotFoundException;;


@Service
public class FileServiceImpl implements FileService {

  private final FileRepository fileRepository;
  private final FileAssembler fileAssembler;
  private final FileMapper fileMapper;
  private final FilePathUtil filePathUtil; 

  @Autowired
  public FileServiceImpl (FileRepository fileRepository, FileAssembler fileAssembler, FileMapper fileMapper, FilePathUtil filePathUtil) {
    this.fileRepository = fileRepository;
    this.fileAssembler = fileAssembler;
    this.fileMapper = fileMapper;
    this.filePathUtil = filePathUtil;
  }

  @Override
  public File createFileFromPush(FileDTO fileDTO, Folder parentFolder) {
    File file = fileAssembler.assemble(fileDTO);
    file.setFolder(parentFolder);
    File savedFile = fileRepository.save(file);
    return savedFile;
  }

  @Override
  public File getFileById(Long fileId) {
    return fileRepository.findById(fileId)
      .orElseThrow(() -> new FileNotFoundException("Could not find file with id: " + fileId));
  }

  public File getFileByUuid(String uuid) {
    return fileRepository.findByUuid(uuid)
      .orElseThrow(() -> new FileNotFoundException("Could not find file with uuid: " + uuid));
  }

  @Override
  public FileDTO getFileAsDTOByUuid(String uuid) {
    return fileMapper.toDTO(getFileByUuid(uuid));
  }

  @Override
  public List<File> getAllFilesByProjectUuid(String projectUuid) {
    List<File> files = fileRepository.findAllByProjectUuid(projectUuid);
    if(files.isEmpty()) {
      throw new FileNotFoundException("Could not find files related to project with uuid: " + projectUuid);
    }
    return files;
  }

  @Override
  public List<FileDTO> getAllFilesAsDTOsByProjectUuid(String projectUuid) {
    return getAllFilesByProjectUuid(projectUuid).stream()
      .map(fileMapper::toDTO)
      .collect(Collectors.toList());
  }

  //TODO Split in to more methods?
  @Override
  public FileDTO updateFile(FileUpdateDTO updateDTO, String uuid) {
    File file = getFileByUuid(uuid);
    if(updateDTO.getPath() != null) file.setPath(updateDTO.getPath()); 
    if(updateDTO.getExtension() != null) file.setExtension(updateDTO.getExtension()); 
    if(hasExtensionChangedInFileName(updateDTO.getFileName(), file.getFileName())) setExtension(file, updateDTO);
    if(!file.getFileName().equals(updateDTO.getFileName())) {
      file.setFileName(updateDTO.getFileName());
      file.setPath(filePathUtil.setNewFilePath(file.getPath(), file.getFileName()));
    }
    File updatedFile = fileRepository.save(file);
    return fileMapper.toDTO(updatedFile);
  }

  private void setExtension(File file, FileUpdateDTO updateDTO) {
    file.setExtension("." + getFileExtensionFromFileName(updateDTO.getFileName()));
  }

  private boolean hasExtensionChangedInFileName(String originalFileName, String newFileName) {
    String orgExtension = getFileExtensionFromFileName(originalFileName);
    String newExtenstion = getFileExtensionFromFileName(newFileName);
    //TODO Ensure extensions exist
    return !orgExtension.equals(newExtenstion);
  }

 private String getFileExtensionFromFileName(String fileName) {
    if(fileName == null) return null; //TODO Implement exception logic
    int dotIndex = fileName.lastIndexOf('.');
    if(dotIndex > 0 && dotIndex < fileName.length() - 1) {
      return fileName.substring(dotIndex + 1);
    }     
    return null;
  }

  @Override
  @Transactional
  public void deleteFileByUuid(String uuid) {
    if(!fileRepository.existsByUuid(uuid)) {
      throw new FileNotFoundException("Could not find file with uuid: " + uuid);
    }
    fileRepository.deleteByUuid(uuid);
  }
}
