package com.adrian.simple_repositories.service.implementation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.adrian.simple_repositories.assembler.FolderAssembler;
import com.adrian.simple_repositories.dto.folder.FolderFullDTO;
import com.adrian.simple_repositories.dto.folder.FolderUpdateDTO;
import com.adrian.simple_repositories.dto.folder.FolderDTO;
import com.adrian.simple_repositories.exception.FolderNotFoundException;
import com.adrian.simple_repositories.mapper.FolderMapper;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.repository.FolderRepository;
import com.adrian.simple_repositories.service.FolderService;
import com.adrian.simple_repositories.util.FilePathUtil;

import jakarta.transaction.Transactional;

@Service
public class FolderServiceImpl implements FolderService {

  private final FolderRepository folderRepository;
  private final FolderAssembler folderAssembler;
  private final FolderMapper folderMapper;
  private final FilePathUtil filePathUtil;

  public FolderServiceImpl(FolderRepository folderRepository, FolderAssembler folderAssembler, FolderMapper folderMapper, FilePathUtil filePathUtil) {
    this.folderRepository = folderRepository;
    this.folderAssembler = folderAssembler;
    this.folderMapper = folderMapper;
    this.filePathUtil = filePathUtil;
  }

  @Override
  @Transactional
  public Folder createFolderFromPush(FolderFullDTO folderDTO, Project project) {
    Folder parentFolder = getFolderById(folderDTO.getParentFolderId());
    Folder folder = folderAssembler.assemble(folderDTO, project, parentFolder);
    Folder savedFolder = folderRepository.save(folder);
    return savedFolder;
  }

  @Override
  public Folder getFolderById(Long folderId) {
    return folderRepository.findById(folderId)
      .orElseThrow(() -> new FolderNotFoundException("Folder could not be fetched with id: " + folderId));
  }

  @Override
  public Folder getFolderByUuid(String uuid) {
    return folderRepository.findFolderByUuid(uuid)
      .orElseThrow(() -> new FolderNotFoundException("Folder could not be fetched with uuid: " + uuid));
  }

  @Override
  public FolderFullDTO getFolderAsDTOByUuid(String uuid) {
    return folderMapper.toFullDTO(getFolderByUuid(uuid));
  }

  @Override
  public List<Folder> getAllFoldersByProjectUuid(String projectUuid) {
    List<Folder> folders = folderRepository.findAllFoldersByProjectUuid(projectUuid);
    if(folders.isEmpty()) {
      throw new FolderNotFoundException("Could not find folders belonging to project with uuid: " + projectUuid);
    }
    return folders;
  }
  
  @Override
  public List<FolderDTO> getAllFoldersAsDTOsByProjectUuid(String projectUuid) {
    return getAllFoldersByProjectUuid(projectUuid).stream()
      .map(folderMapper::toDTO)
      .collect(Collectors.toList());
  }

  //TODO Rethink path setting logic does updateDTOs need to contain a path property? 
  @Override
  public FolderDTO updateFolder(String uuid, FolderUpdateDTO updateDTO) {
    Folder folder = getFolderByUuid(uuid);
    if(!folder.getPath().equals(updateDTO.getFilePath())) folder.setPath(updateDTO.getFilePath());
    if(!folder.getFolderName().equals(updateDTO.getFolderName())) {
      folder.setFolderName(updateDTO.getFolderName());
      folder.setPath(filePathUtil.setNewFilePath(folder.getPath(), folder.getFolderName()));
    }
    Folder updatedFolder = folderRepository.save(folder);
    return folderMapper.toDTO(updatedFolder);
  }

  @Override
  public void deleteFolderByUuid(String uuid) {
    if(!folderRepository.existsByUuid(uuid)) {
      throw new FolderNotFoundException("Could not find folder with uuid: " + uuid);
    }
    folderRepository.deleteByUuid(uuid);
  }

}
