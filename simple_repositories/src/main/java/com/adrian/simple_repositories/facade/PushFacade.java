/********************************************
 *            -- PushFacade --              *
 *                                          *
 * PushFacade is used to control            *
 * the API flow based on the content        *
 * of recieved push request.                * 
 * This is done for the reason instead of   *
 * coupling the service classes with        *
 * eachother they are injected here.        *
 *                                          *
 * It also simplifies scaling the           *
 * processes of persiting entitys related   *
 * to pushes.                               *
 *                                          *
 ********************************************/

package com.adrian.simple_repositories.facade;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.push.PushDTO;
import com.adrian.simple_repositories.dto.push.PushRequestDTO;
import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.adrian.simple_repositories.exception.InvalidPushException;
import com.adrian.simple_repositories.mapper.ResponseMapper;
import com.adrian.simple_repositories.model.Branch;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Repo;
import com.adrian.simple_repositories.model.User;
import com.adrian.simple_repositories.model.UserRepoBranchActivity;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.service.BranchService;
import com.adrian.simple_repositories.service.FileService;
import com.adrian.simple_repositories.service.DirectoryService;
import com.adrian.simple_repositories.service.RepoService;
import com.adrian.simple_repositories.service.UserRepoBranchActivityService;
import com.adrian.simple_repositories.service.UserService;
import com.adrian.simple_repositories.wrapper.PushResponseWrapper;


/*
 * PushFacade serves as a facade for processing push operations of repos, directories, and files.
 * It uses diferent services to create a push depending of the content of the request.
 *
 * Injects PushService to handle push requests containing repo data, DirectoryService to handle
 * push requests containing directory data, FileService to handle push requests containing file data,
 * branch service to handle branch validation, UserRepoBranchActivityService to handle user activity by
 * repo and branch, USerService to handle user validation, ResponseMapper to build responses
 * for push data.
 */
@Component
public class PushFacade {

  private final RepoService repoService;
  private final DirectoryService directoryService;
  private final FileService fileService;
  private final BranchService branchService;
  private final UserService userService;
  private final UserRepoBranchActivityService userRepoBranchActivityService;
  private final ResponseMapper responseMapper;

  @Autowired
  public PushFacade(RepoService repoService, DirectoryService directoryService, FileService fileService,
          BranchService branchService, UserService userService, UserRepoBranchActivityService userRepoBranchActivityService, ResponseMapper responseMapper) {
    this.repoService = repoService;
    this.directoryService = directoryService;
    this.fileService = fileService;
    this.branchService = branchService;
    this.userService = userService;
    this.userRepoBranchActivityService = userRepoBranchActivityService;
    this.responseMapper = responseMapper;
  }

  /*
   * Method first check if repository exists, if not returns a failed push response,
   * if repo exists, checks the content of requests and calles porcess directory/file
   * method.
   * 
   * @param requestDTO: contains data needed to perform a push
   * @return responseDTO: contains push success/error information
   */
  public PushResponseWrapper processPush(PushRequestDTO requestDTO) {
    Repo repo = getRepoByUuidAndBranchName(requestDTO.getRepoUuid(), requestDTO.getBranchDTO().getBranchName());
    if(repo == null) { //Repo might be null because user has not passed the latest version uuid
      PushResponseDTO responseDTO = createFailedResponseBecauseNewRepoVersionExists(requestDTO.getRepoUuid()); 
    }
    
   if(requestDTO.getContent() instanceof DirectoryFullDTO) {
      DirectoryFullDTO directoryDTO = (DirectoryFullDTO) requestDTO.getContent();
      return processDirectoryPush(requestDTO, directoryDTO, repo);
    } else {
      FileDTO fileDTO = (FileDTO) requestDTO.getContent();
      return processFilePush(requestDTO, fileDTO, repo);
    }
  } 

  /*
   * This method calls methods in directory service based on data in the directory DTO,
   * if directory DTO does not contain parent directory information, it assumes it is a root directory,
   * if not calls method that updates parent directory with the new data.
   *
   * @param requestDTO: contains push data
   * @param directoryDTO: contains new directory data
   * @param repo: contains repository data
   */
  private PushResponseWrapper processDirectoryPush(PushRequestDTO requestDTO, DirectoryFullDTO directoryDTO, Repo repo) {
    if(directoryDTO.getParentDirectoryUuid() == null) {
      Directory directory = directoryService.assembleRootDirectoryFromPush(directoryDTO, repo);
      addOrReplaceRootDirectory(repo.getDirectories(), directory);
      Repo updatedRepo = repoService.updateRepo(repo); 
      PushResponseDTO responseDTO = createSuccessResponse(updatedRepo.getUuid(), "Directory");
      return new PushResponseWrapper(responseDTO, directory);
    }
    
    Directory directory = directoryService.assembleDirectoryFromPush(directoryDTO, repo);
    Directory parentDirectory = directoryService.getDirectoryByUuid(directoryDTO.getParentDirectoryUuid());
    addOrReplaceDirectory(parentDirectory, directory);
    if(insertDirectory(parentDirectory, repo.getDirectories())) {
      Repo updatedRepo = repoService.updateRepo(repo);
      PushResponseDTO responseDTO = createSuccessResponse(updatedRepo.getUuid(), "Directory");
      return new PushResponseWrapper(responseDTO, directory);
    } else {
      PushResponseDTO responseDTO = new PushResponseDTO(false, "Error updating repo with directory from push", repo.getUuid());
      return new PushResponseWrapper(responseDTO, directory);
    }
  }

  private PushResponseWrapper processFilePush(PushRequestDTO requestDTO, FileDTO fileDTO, Repo repo) {
    //Get the parent directory of file
    //Directory directory = fileDTO.getDirectoryId()
    return null;
  }

  /*
   * Helper method to see if directory by the name already exists, if it does remove old
   * directory and add the new one, if it does not exists simply add the new directory
   *
   * @param parentDirectory: parentDirectory of new directory
   * @param newDirectory: directory containing the new data
   */
  private void addOrReplaceDirectory(Directory parentDirectory, Directory newDirectory) {
    for(Directory subDir : parentDirectory.getDirectories()) {
      if(subDir.getDirectoryName().equals(newDirectory.getDirectoryName())) {
        parentDirectory.getDirectories().remove(subDir);
      }
    }
    parentDirectory.getDirectories().add(newDirectory);
  }

  /*
   * Helper method to check if repos root directoriees already have a directory with the same name,
   * if it does, remove old directory and replace with new one, else simply add the new directory
   *
   * @param rootDirectories: List of current root directories in the repo
   * @param newDirectory: directory containing the new data
   */
  private void addOrReplaceRootDirectory(List<Directory> rootDirectories, Directory newDirectory) {
    for(Directory rootDir : rootDirectories) {
      if(rootDir.getDirectoryName().equals(newDirectory.getDirectoryName())) {
        rootDirectories.remove(rootDir);
      }
    }
    rootDirectories.add(newDirectory);
  }

  /*
   * Recursive method that attempts to find a sub directory with the same name as a specified
   * directorym if it does returns true as a success flag, else returns false representing a fail
   *
   * @param directory: directory with new data, but same name as a existing sub directory
   * @param subDirectories: list of directories of the current directory in iteration
   */
  private boolean insertDirectory(Directory directory, List<Directory> subDirectories) {
    for(int i = 0, n = subDirectories.size(); i < n; i++) {
      Directory subDir = subDirectories.get(i);
      if(subDir.getDirectoryName().equals(directory.getDirectoryName())) {
        subDirectories.set(i, directory);
        return true;
      } else {
        if(insertDirectory(directory, subDirectories.get(i).getDirectories())) {
          return true;
        }
      }
    }
    return false;
  }


  //Fetch repo based on uuid and branch
  private Repo getRepoByUuidAndBranchName(String repoUuid, String branchName) {
    /* 
     * At this point of development we are assuming that the branch we are working on is Main
     * Pushing to separate branches and pulling from separate branches is going to implemented at a later state
     */
    return repoService.getRepoByUuid(repoUuid);
  }

  private PushResponseDTO createSuccessResponse(String uuid, String pushType) {
    return new PushResponseDTO(
      true,
      pushType + " push sucessfull",
      uuid 
    );
  }

  private PushResponseDTO createFailedResponseBecauseNewRepoVersionExists(String oldUuid) {
      String newUuid = repoService.getNewUuidOfRepoByOldUuid(oldUuid);
      return new PushResponseDTO(
        false,
        "Please pull latest version of repo using: " + newUuid + ".",
        null
      );
  }

  
  /*
   * Processes push based on content of pushDTO
   * Determines the push type (repo / directory / file) and passes
   * data to appropriate handler method.
   *
   * @param pushDTO: DTO containing push request data
   * @return pushResponseDTO: DTO containing push response data
   * @throws InvalidPushException: throws exception if request contains invalid data
   */
/*  public PushResponseDTO processPush(PushDTO pushDTO) { 
    validatePushDTO(pushDTO); 
    if(pushDTO.getRepoFullDTO() != null) return handleRepoPush(pushDTO);
    if(pushDTO.getDirectoryFullDTO() != null) return handleDirectoryPush(pushDTO.getDirectoryFullDTO());
    if(pushDTO.getFileDTO() != null) return handleFilePush(pushDTO.getFileDTO());
    throw new InvalidPushException("Push request body is invalid, does not contain repo/directory/file");
  }
*/
  /* TODO refactor method to separate branch logic in to a separate mehtod
   *
   * Handles push operation for request containing repo data
   *
   * @param pushDTO containing push request data
   * @return pushResponseDTO containing push response data
   */
  
/*
  private PushResponseDTO handleRepoPush(PushDTO pushDTO) {
    User user = userService.getUserByEmail(pushDTO.getOwnerEmail());
    Repo repo = repoService.createRepoFromPush(pushDTO.getRepoFullDTO(), user);
    Branch mainBranch = branchService.createBranch(new BranchDTO(null, "main", LocalDateTime.now(), null), repo);
    newUserRepoBranchActivity(pushDTO, repo, mainBranch);
    return responseMapper.toPushResponseFromRepo(repo);   
  }

  /*
   * Handles push operation for request containing directory data
   *
   * @param pushDTO: DTO containing push request data
   * @return pushResponseDTO: DTO containing push response data
   */
  private PushResponseDTO handleDirectoryPush(DirectoryFullDTO directoryDTO) {
    Repo repo = repoService.getRepoByUuid(directoryDTO.getRepoUuid());
    Directory directory = directoryService.createDirectoryFromPush(directoryDTO, repo);
    return responseMapper.toPushResponseFromDirectory(directory);
   }

  /*
   * Handles push operation for request containing file data
   *
   * @param pushDTO: DTO containing push request data
   * @return pushResponseDTO: DTO containing push response data
   */
 private PushResponseDTO handleFilePush(FileDTO fileDTO) {
    Directory parentDirectory = directoryService.getDirectoryById(fileDTO.getDirectoryId());
    File file = fileService.createFileFromPush(fileDTO, parentDirectory);
    return responseMapper.toPushResponseFromFile(file);
  }

  /*
   * Validates data in push request by checking that request contains either repo, directory, or file
   *
   * @param pushDTO: DTO containing push request data
   * @throws InvalidPushException if repo, directory, and file data is null
   */
  private void validatePushDTO(PushDTO pushDTO) {
    if(pushDTO.getRepoFullDTO() == null && pushDTO.getDirectoryFullDTO() == null && pushDTO.getFileDTO() == null) {
      throw new InvalidPushException("Push request body is invalid, does not contain repo/directory/file");
    } 
  }

  /*
   * TODO UserRepoBranchActivity exception handling
   * TODO Separate user fetching from user service to separate method and add user as input param?
   *
   * Records user activity by branch and repo with UserRepoBranchActivity
   *
   * @param pushDTO: DTO containing push request data
   * @param repo: repo used in push operation
   * @param branch: branch used in push operation
   */
  private void newUserRepoBranchActivity(PushDTO pushDTO, Repo repo, Branch branch) {
    User user = userService.getUserByEmail(pushDTO.getOwnerEmail());
    
    UserRepoBranchActivity activity = new UserRepoBranchActivity();
    activity.setUser(user);
    activity.setRepo(repo);
    activity.setBranch(branch);

    userRepoBranchActivityService.createUserRepoBranchActivity(activity);
  }
}
