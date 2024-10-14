package com.adrian.simple_repositories.service.implementation;

import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.assembler.FolderAssembler;
import com.adrian.simple_repositories.dto.FolderFullDTO;
import com.adrian.simple_repositories.exception.FolderNotFoundException;
import com.adrian.simple_repositories.mapper.FolderMapper;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.repository.FolderRepository;
import com.adrian.simple_repositories.service.FolderService;

import jakarta.transaction.Transactional;

@Service
public class FolderServiceImpl implements FolderService {

  private final FolderRepository folderRepository;
  private final FolderAssembler folderAssembler;
  private final FolderMapper folderMapper;

  public FolderServiceImpl(FolderRepository folderRepository, FolderAssembler folderAssembler, FolderMapper folderMapper) {
    this.folderRepository = folderRepository;
    this.folderAssembler = folderAssembler;
    this.folderMapper = folderMapper;
  }

  @Override
  @Transactional
  public Folder createFolderFromPush(FolderFullDTO folderDTO, Project project) {
    Folder parentFolder = getFolderById(folderDTO.getParentFolderId());
    Folder folder = folderAssembler.assemble(folderDTO, project, parentFolder);
    Folder savedFolder = folderRepository.save(folder);
    return savedFolder;
  }

  public Folder getFolderById(Long folderId) {
    return folderRepository.findById(folderId)
      .orElseThrow(() -> new FolderNotFoundException("Folder could not be fetched with id: " + folderId));
  }
}
