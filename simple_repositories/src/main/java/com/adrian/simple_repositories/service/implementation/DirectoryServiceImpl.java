package com.adrian.simple_repositories.service.implementation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.adrian.simple_repositories.assembler.DirectoryAssembler;
import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryUpdateDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryDTO;
import com.adrian.simple_repositories.exception.DirectoryNotFoundException;
import com.adrian.simple_repositories.mapper.DirectoryMapper;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Repo;
import com.adrian.simple_repositories.repository.DirectoryRepository;
import com.adrian.simple_repositories.service.DirectoryService;
import com.adrian.simple_repositories.util.FilePathUtil;

import jakarta.transaction.Transactional;

@Service
public class DirectoryServiceImpl implements DirectoryService {

  private final DirectoryRepository directoryRepository;
  private final DirectoryAssembler directoryAssembler;
  private final DirectoryMapper directoryMapper;
  private final FilePathUtil filePathUtil;

  public DirectoryServiceImpl(DirectoryRepository directoryRepository, DirectoryAssembler directoryAssembler, DirectoryMapper directoryMapper, FilePathUtil filePathUtil) {
    this.directoryRepository = directoryRepository;
    this.directoryAssembler = directoryAssembler;
    this.directoryMapper = directoryMapper;
    this.filePathUtil = filePathUtil;
  }

  @Override
  @Transactional
  public Directory createDirectoryFromPush(DirectoryFullDTO directoryDTO, Repo repo) {
    Directory parentDirectory = getDirectoryById(directoryDTO.getParentDirectoryId());
    Directory directory = directoryAssembler.assemble(directoryDTO, repo, parentDirectory);
    Directory savedDirectory = directoryRepository.save(directory);
    return savedDirectory;
  }

  @Override
  public Directory getDirectoryById(Long directoryId) {
    return directoryRepository.findById(directoryId)
      .orElseThrow(() -> new DirectoryNotFoundException("Directory could not be fetched with id: " + directoryId));
  }

  @Override
  public Directory getDirectoryByUuid(String uuid) {
    return directoryRepository.findDirectoryByUuid(uuid)
      .orElseThrow(() -> new DirectoryNotFoundException("Directory could not be fetched with uuid: " + uuid));
  }

  @Override
  public DirectoryFullDTO getDirectoryAsDTOByUuid(String uuid) {
    return directoryMapper.toFullDTO(getDirectoryByUuid(uuid));
  }

  @Override
  public List<Directory> getAllDirectoriesByRepoUuid(String repoUuid) {
    List<Directory> directorys = directoryRepository.findAllDirectoriesByRepoUuid(repoUuid);
    if(directorys.isEmpty()) {
      throw new DirectoryNotFoundException("Could not find directorys belonging to repo with uuid: " + repoUuid);
    }
    return directorys;
  }
  
  @Override
  public List<DirectoryDTO> getAllDirectoriesAsDTOsByRepoUuid(String repoUuid) {
    return getAllDirectoriesByRepoUuid(repoUuid).stream()
      .map(directoryMapper::toDTO)
      .collect(Collectors.toList());
  }

  //TODO Rethink path setting logic does updateDTOs need to contain a path property? 
  @Override
  public DirectoryDTO updateDirectory(String uuid, DirectoryUpdateDTO updateDTO) {
    Directory directory = getDirectoryByUuid(uuid);
    if(!directory.getPath().equals(updateDTO.getFilePath())) directory.setPath(updateDTO.getFilePath());
    if(!directory.getDirectoryName().equals(updateDTO.getDirectoryName())) {
      directory.setDirectoryName(updateDTO.getDirectoryName());
      directory.setPath(filePathUtil.setNewFilePath(directory.getPath(), directory.getDirectoryName()));
    }
    Directory updatedDirectory = directoryRepository.save(directory);
    return directoryMapper.toDTO(updatedDirectory);
  }

  @Override
  public void deleteDirectoryByUuid(String uuid) {
    if(!directoryRepository.existsByUuid(uuid)) {
      throw new DirectoryNotFoundException("Could not find directory with uuid: " + uuid);
    }
    directoryRepository.deleteByUuid(uuid);
  }

}
