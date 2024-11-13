package com.adrian.simple_repositories.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.assembler.RepoAssembler;
import com.adrian.simple_repositories.dto.UniqueIdentifierDTO;
import com.adrian.simple_repositories.dto.repo.RepoDTO;
import com.adrian.simple_repositories.dto.repo.RepoFullDTO;
import com.adrian.simple_repositories.dto.repo.RepoIdentifierRequestDTO;
import com.adrian.simple_repositories.dto.repo.RepoInformationDTO;
import com.adrian.simple_repositories.dto.repo.RepoSetupDTO;
import com.adrian.simple_repositories.dto.repo.RepoUpdateDTO;
import com.adrian.simple_repositories.exception.RepoNotFoundException;
import com.adrian.simple_repositories.mapper.RepoMapper;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Repo;
import com.adrian.simple_repositories.model.RepoVersion;
import com.adrian.simple_repositories.model.User;
import com.adrian.simple_repositories.repository.RepoRepository;
import com.adrian.simple_repositories.service.RepoService;
import com.adrian.simple_repositories.service.RepoVersionService;
import com.adrian.simple_repositories.service.UserService;
import com.adrian.simple_repositories.security.AuthenticationFacade;

import jakarta.transaction.Transactional;

/*
 * Implementation of RepoService interface
 *
 * Injects RepoService to handle database operations on the repos table,
 * RepoAssembler to construct repo entity containing nested entities before persisting
 * to database, RepoMapper to map repos to DTOs.
 */
@Service
public class RepoServiceImpl implements RepoService {

  private final RepoRepository repoRepository;
  private final RepoAssembler repoAssembler;
  private final RepoMapper repoMapper;
  private final UserService userService;
  private final RepoVersionService repoVersionService;
  private final AuthenticationFacade authenticationFacade;

  @Autowired
  public RepoServiceImpl(RepoRepository repoRepository, RepoAssembler repoAssembler, RepoMapper repoMapper,
                          UserService userService, RepoVersionService repoVersionService, AuthenticationFacade authenticationFacade) {
    this.repoRepository = repoRepository;
    this.repoAssembler = repoAssembler;
    this.repoMapper = repoMapper;
    this.userService = userService;
    this.repoVersionService = repoVersionService;
    this.authenticationFacade = authenticationFacade;
  }

  /*
   * Creates a new repo from data in RepoSetupDTO
   *
   * @param setupDTO: contains repo data
   * @return repoDTO: contains information of created repo
   */
  @Override
  public RepoDTO createEmptyRepo(RepoSetupDTO setupDTO) {
    Repo repo = repoMapper.toEntityFromSetupDTO(setupDTO);
    User user = userService.getUserByEmail(setupDTO.getOwnerEmail());
    repo.setUser(user);
    Repo savedRepo = repoRepository.save(repo);
    return repoMapper.toDTO(savedRepo);
  }
  
  /*
   *  TODO Change method name to createRepoFromFullDTO ?
   *
   * Creates a new repo from RepoFullDTO and assiciating it with given user
   *
   * @param repoFullDTO: contains repo data
   * @param user: contains user data of repo owner
   * @return: persisted repo entity
   */
  @Override
  @Transactional
  public Repo createRepoFromPush(RepoFullDTO repoDTO, User user) {
    Repo repo = repoAssembler.assemble(repoDTO, user);
    Repo savedRepo = repoRepository.save(repo);
    return savedRepo;
  }

  /*
   * Retrieves repo by ID
   *
   * @param repoId: ID of repo 
   * @return repo: returns repo entity fetched from the database by ID
   * @throws RepoNotFoundException: throws exception if repo not found by ID
   */
  @Override
  public Repo getRepoById(Long repoId) {
    return repoRepository.findById(repoId)
      .orElseThrow(() -> new RepoNotFoundException("Poject with id: " + repoId + " not found"));
  }

  /*
   * Retrieves repo by UUID
   *
   * @param uuid: UUID of repo 
   * @return repo: returns repo entity fetched from the database by UUID 
   */
  @Override
  public Repo getRepoByUuid(String uuid) {
    String email = authenticationFacade.getAuthentication().getName();
    return repoRepository.findRepoByUuidAndUserEmail(uuid, email)
      .orElse(null); //Not throwing exception here because we need to check for older versions of repo
  }


  /*
   * Checks if old version of repo exists by old repo uuid
   *
   * @param oldUuid: possibly old uuid of a repo
   * @return exists: returns true or false if previous version of repo has had uuid
   */
  @Override
  public boolean checkIfRepoExistsByOldUuid(String oldUuid) {
    return repoVersionService.checkIfRepoExistsByOldUuid(oldUuid);
  }
  
  @Override
  public String getNewUuidOfRepoByOldUuid(String oldUuid) {
    RepoVersion repoVersion = repoVersionService.getRepoVersionByOldUuid(oldUuid);
    Repo repo = repoRepository.findById(repoVersion.getId())
      .orElseThrow(() -> new RepoNotFoundException("Could not find repo with id: " + repoVersion.getId())); 
    return repo.getUuid();
  }

  /*
   * Methods tries to fetch repo from database based on uuid, if that fails it tries to fetch RepoVersion
   * to check if the uuid is for a old version of the repo, if RepoVersion is found method fetches the new
   * version of the repo with repo PK 
   *
   * @param uuid: contains uuid for fetching repo
   * @return repoDTO: returns repo as DTO
   */
  @Override
  public RepoFullDTO getRepoAsDTOByUuidForPullRequest(String uuid) {
    String email = authenticationFacade.getAuthentication().getName();
    Optional<Repo> optionalRepo = repoRepository.findRepoByUuidAndUserEmail(uuid, email);
    if(optionalRepo.isPresent()) return repoMapper.toFullDTO(optionalRepo.get()); 
    
    RepoVersion repoVersion = repoVersionService.getRepoVersionByOldUuid(uuid);
    Repo repo = repoRepository.findById(repoVersion.getId())
      .orElseThrow(() -> new RepoNotFoundException("Could not find repo by id: " + repoVersion.getId()));
    return repoMapper.toFullDTO(repo);
  }

  /*
   * Retrieves repo by UUID and converts it to RepoFullDTO
   *
   * @param uuid: UUID of repo 
   * @return repo: returns repo entity fetched from database
   * @throws RepoNotFoundException: throws exception if repo not found by uuid
   */
  @Override
  public RepoFullDTO getRepoAsDTOByUuid(String uuid) {
    return repoMapper.toFullDTO(getRepoByUuid(uuid));
  }

  /*
   * TODO split in to two methods, one for fetching all repos and one for converting repos to information DTOs
   *
   * Retrieves all repos and converts them to information DTOs
   *
   * @return pushInfoDTOs: returns list of DTOs containing push information
   */
  @Override
  public List<RepoInformationDTO> getAllReposAsInfoDTOs() {
    List<Repo> repos = repoRepository.findAll();
    if(repos == null || repos.isEmpty()) {
      throw new RepoNotFoundException("Could not fetch all repos from database");
    }

    List<RepoInformationDTO> infoDTOs = converToRepoInfoDTOs(repos); 
    for(int i = 0, n = repos.size(); i < n; i++) {
      List<String> extensions = getAllFileExtionsFromRepo(repos.get(i));
      infoDTOs.get(i).addAllFileTypes(extensions);
    }

    return infoDTOs;
  }

  /*
   * Converts a list of repos to repo information DTOs
   *
   * @param repos: list of repos 
   * @return repoInfoDTOs: returns list of repo information DTOs
   */
  private List<RepoInformationDTO> converToRepoInfoDTOs(List<Repo> repos) {
    return repos.stream()
      .map(repoMapper::toInfoDTO)
      .collect(Collectors.toList());
  }

  /*
   * Initializing file system traversal for extracting extensions of files nested in repo 
   *
   * @param repo: repo entity
   * @returns extension: list of strings containing file extensions
   */
  private List<String> getAllFileExtionsFromRepo(Repo repo) {
    List<String> extensions = new ArrayList<>();
    for(Directory directory : repo.getDirectories()) {
      walkThroughDirectoryTree(directory, extensions);
    }

    return extensions;
  }

  /*
   *  Recursivly walk through directory tree, calling helper method to extract file extensions of current directory
   *
   *  @param directory: directory of current recursive itteration
   *  @param extension: list of strings containing extensions from passed iterations (will be empty during first iteration)
   *  @return void: no need to return anything since we are updating content of a ArrayList stored on the heap
   */
  private void walkThroughDirectoryTree(Directory directory, List<String> extensions) {
    if(directory.getFiles() != null || !directory.getFiles().isEmpty()) {
      getFileExtensionsFromDirectory(directory, extensions);
    } 

    if(directory.getDirectories() == null || directory.getDirectories().isEmpty()) return;
    for(Directory subDirectory : directory.getDirectories()) {
      walkThroughDirectoryTree(subDirectory, extensions);
    }
  } 

  /*
   * Helper method to extract file extension from directory and store it in a list
   *
   * @param directory: directory containng files
   * @param extension: list of strings containing extensions from passed iterations from calling method (will be empty during first iteration)
   * @return void: no need to return anything since we are updating content of a ArrayList stored on the heap
   */
  private void getFileExtensionsFromDirectory(Directory directory, List<String> extensions) {
    for(File file : directory.getFiles()) {
      extensions.add(file.getExtension());
    }
  }

  /*
   * Retrieves repo from database by repo identifier request DTO containg repo uuid and user details
   *
   * @param request: DTO containing repo uuid and user details
   * @return repos : list of repos fetched from database by uuid and user details
   * @throws RepoNotFoundException: throws exception if repo was not found by uuid and user details
   */
  @Override
  public Repo getRepoByRepoNameAndUserEmail(RepoIdentifierRequestDTO request) {
    return repoRepository.findRepoByRepoNameAndUserEmail(request.getRepoName(), request.getEmail())
      .orElseThrow(() -> new RepoNotFoundException("Could not fetch repo created by user: " + request.getEmail() + " with repo name: " + request.getRepoName()));
  }

  /*
   * Retrieves repo uuid from database by repo identifier request DTO and converts it to a unique identifier DTO
   * 
   * @param request: DTO cotaining repo uuid and user details
   * @return uniqueIdentifierDTO: DTO containing repo uuid
   */
  @Override
  public UniqueIdentifierDTO getRepoIdentiferByRepoNameAndUserEmail(RepoIdentifierRequestDTO request) {
    return repoMapper.toIdentifierDTO(getRepoByRepoNameAndUserEmail(request)); 
  }

  /*
   * Retrieves repo from database by uuid, updates repo with data from repo update DTO
   *
   * @param updateDTO: contains data to be updated in repo  
   * @param uuid: contains uuid of repo to be updated
   */
  @Override
  public RepoDTO updateRepo(RepoUpdateDTO updateDTO, String uuid) {
    Repo repo = getRepoByUuid(uuid);
    repo.setRepoName(updateDTO.getRepoName());
    repo.setRepoInformation(updateDTO.getRepoInformation());
    Repo updatedRepo = repoRepository.save(repo);
    return repoMapper.toDTO(updatedRepo);
  }

  /*
   * Deletes repo by uuid
   * @param uuid: contains uuid of repo to be deleted
   * @throws RepoNotFoundException: throws exception if repo with uuid was not found
   */
  @Override
  public void deleteRepoByUuid(String uuid) {
    if(!repoRepository.existsByUuid(uuid)) {
      throw new RepoNotFoundException("Could not find repo with uuid: " + uuid);
    }
    repoRepository.deleteByUuid(uuid);
  }

}
