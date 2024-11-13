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
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.repository.FileRepository;
import com.adrian.simple_repositories.service.FileService;
import com.adrian.simple_repositories.util.FilePathUtil;

import jakarta.transaction.Transactional;

import com.adrian.simple_repositories.exception.FileNotFoundException;;

/*
 * Implementation of FileService
 *
 * Implements methods to handle database operations on files table.
 *
 * Injects FileRepository for database operations on files table, FileAssembler to assemble file from push,
 * FileMapper to convert files to DTOs, FilePathUtil containing helper methods to handle file path alteration.
 */
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

  /*
   * Creates file binds it to parent directory and persists it to the datbase from file dto
   *
   * @param fileDTO: DTO containing file data
   * @param parentDirectory: Directory entity that fileDTO belongs to
   */
  @Override
  public File createFileFromPush(FileDTO fileDTO, Directory parentDirectory) {
    File file = fileAssembler.assemble(fileDTO);
    file.setDirectory(parentDirectory);
    File savedFile = fileRepository.save(file);
    return savedFile;
  }

  @Override
  public File assemleFileFromPush(FileDTO fileDTO, Directory parentDirectory) {
    File file = fileAssembler.assemble(fileDTO);
    file.setDirectory(parentDirectory);
    return file;
  }

  /*
   * Retrieves file from database by ID
   *
   * @param fileId: ID of file
   * @return file: returns file fetched from database
   * @throws FileNotFoundException: throws exception if file was not found by ID
   */
  @Override
  public File getFileById(Long fileId) {
    return fileRepository.findById(fileId)
      .orElseThrow(() -> new FileNotFoundException("Could not find file with id: " + fileId));
  }

  /*
   * Retrieves file from database by UUID
   *
   * @param uuid: UUID of file
   * @return file: returns file fetched from database
   * @throws FileNotFoundException: throws exception if file was not found by UUID
   */
  public File getFileByUuid(String uuid) {
    return fileRepository.findByUuid(uuid)
      .orElseThrow(() -> new FileNotFoundException("Could not find file with uuid: " + uuid));
  }

  /*
   * Retrieves file from database by UUID and converts it to a file DTO
   *
   * @param uuid: file UUID
   * @return fileDTO: returns DTO containing file data
   * @throws FileNotFoundException: throws exception if file was not found by UUID
   */
  @Override
  public FileDTO getFileAsDTOByUuid(String uuid) {
    return fileMapper.toDTO(getFileByUuid(uuid));
  }

  /*
   * Retrieves all files by repo UUID
   *
   * @param repoUuid: repo UUID
   * @return fileDTOs: returns list of DTOs containing file data
   * @throws FileNotFoundException: throws exception if no files was found by repo uuid
   */
  @Override
  public List<File> getAllFilesByRepoUuid(String repoUuid) {
    List<File> files = fileRepository.findAllByRepoUuid(repoUuid);
    if(files.isEmpty()) {
      throw new FileNotFoundException("Could not find files related to repo with uuid: " + repoUuid);
    }
    return files;
  }


  /*
   * Retrieves all files as DTOs by repo UUID
   *
   * @param repoUuid: repo UUID
   * @return fileDTOs: returns list of DTOs containing file data
   * @throws FileNotFoundException: throws exception if no files was found by repo UUID
   */
  @Override
  public List<FileDTO> getAllFilesAsDTOsByRepoUuid(String repoUuid) {
    return getAllFilesByRepoUuid(repoUuid).stream()
      .map(fileMapper::toDTO)
      .collect(Collectors.toList());
  }

  /*
   * TODO split method in to smaller methods with higher SOC
   *
   * Updates file with new data from file DTO, fetches file from database by UUID, returns file as DTO
   *
   * @param updateDTO: DTO containing data to be used for file update
   * @param uuid: file UUID
   * @return fileDTO: returns DTO containing updated file data
   * @throws FileNotFoundException: throws exception if file was not found by UUID
   */

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

  /*
   * TODO change input parameter updateDTO to String fileName?
   *
   * Helper method to update file extension if present in update DTO
   *
   * @param file: file entity that will be updated with new extension
   * @param updateDTO: DTO containing file update data
   */
  private void setExtension(File file, FileUpdateDTO updateDTO) {
    file.setExtension("." + getFileExtensionFromFileName(updateDTO.getFileName()));
  }

  /*
   * Helper method to check if extension has been changed in file name but not directly in extenssion attribute,
   * if changed sets extension to match the one in file name
   *
   * @param originalFileName: original name of file
   * @param newFileName: new name of file
   * @return hasFileNameChanged: if file name has changed return true, else false 
   */
  private boolean hasExtensionChangedInFileName(String originalFileName, String newFileName) {
    String orgExtension = getFileExtensionFromFileName(originalFileName);
    String newExtenstion = getFileExtensionFromFileName(newFileName);
    //TODO Ensure extensions exist
    return !orgExtension.equals(newExtenstion);
  }

  /*
   * Helper method to extract extension from file name
   *
   * @param fileName: name of file
   * @return extension: extension extracted from file name
   */
 private String getFileExtensionFromFileName(String fileName) {
    if(fileName == null) return null; //TODO Implement exception logic
    int dotIndex = fileName.lastIndexOf('.');
    if(dotIndex > 0 && dotIndex < fileName.length() - 1) {
      return fileName.substring(dotIndex + 1);
    }     
    return null;
  }

  /*
   * Deletes file by UUID
   *
   * @param uuid: file UUID
   */
  @Override
  @Transactional
  public void deleteFileByUuid(String uuid) {
    if(!fileRepository.existsByUuid(uuid)) {
      throw new FileNotFoundException("Could not find file with uuid: " + uuid);
    }
    fileRepository.deleteByUuid(uuid);
  }
}
