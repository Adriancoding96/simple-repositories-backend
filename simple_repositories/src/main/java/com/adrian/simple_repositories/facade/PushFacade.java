/********************************************
 *           -- PushFacade --             *
 *                                          *
 * PushFacade is used to control          *
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

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.directory.DirectoryFullDTO;
import com.adrian.simple_repositories.dto.push.PushDTO;
import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.adrian.simple_repositories.exception.InvalidPushException;
import com.adrian.simple_repositories.mapper.ResponseMapper;
import com.adrian.simple_repositories.model.Branch;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.model.User;
import com.adrian.simple_repositories.model.UserProjectActivity;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.service.BranchService;
import com.adrian.simple_repositories.service.FileService;
import com.adrian.simple_repositories.service.DirectoryService;
import com.adrian.simple_repositories.service.ProjectService;
import com.adrian.simple_repositories.service.UserProjectActivityService;
import com.adrian.simple_repositories.service.implementation.UserDetailServiceImpl;

@Component
public class PushFacade {

  private final ProjectService projectService;
  private final DirectoryService directoryService;
  private final FileService fileService;
  private final BranchService branchService;
  private final UserDetailServiceImpl userDetailServiceImpl;
  private final UserProjectActivityService userProjectActivityService;
  private final ResponseMapper responseMapper;

  @Autowired
  public PushFacade(ProjectService projectService, DirectoryService directoryService, FileService fileService,
          BranchService branchService, UserDetailServiceImpl userDetailServiceImpl, UserProjectActivityService userProjectActivityService, ResponseMapper responseMapper) {
    this.projectService = projectService;
    this.directoryService = directoryService;
    this.fileService = fileService;
    this.branchService = branchService;
    this.userDetailServiceImpl = userDetailServiceImpl;
    this.userProjectActivityService = userProjectActivityService;
    this.responseMapper = responseMapper;
  }

  public PushResponseDTO processPush(PushDTO pushDTO) { 
    validatePushDTO(pushDTO); 
    if(pushDTO.getProjectFullDTO() != null) return handleProjectPush(pushDTO);
    if(pushDTO.getDirectoryFullDTO() != null) return handleDirectoryPush(pushDTO.getDirectoryFullDTO());
    if(pushDTO.getFileDTO() != null) return handleFilePush(pushDTO.getFileDTO());
    throw new InvalidPushException("Push request body is invalid, does not contain project/directory/file");
  }

  private PushResponseDTO handleProjectPush(PushDTO pushDTO) {
    User user = userDetailServiceImpl.getUserByEmail(pushDTO.getOwnerEmail());
    Project project = projectService.createProjectFromPush(pushDTO.getProjectFullDTO(), user);
    Branch mainBranch = branchService.createBranch(new BranchDTO(null, "main", LocalDateTime.now(), null), project);
    newUserProjectActivity(pushDTO, project, mainBranch);
    return responseMapper.toPushResponseFromProject(project);   
  }

    private PushResponseDTO handleDirectoryPush(DirectoryFullDTO directoryDTO) {
    Project project = projectService.getProjectById(directoryDTO.getProjectId());
    Directory directory = directoryService.createDirectoryFromPush(directoryDTO, project);
    return responseMapper.toPushResponseFromDirectory(directory);
   }

  private PushResponseDTO handleFilePush(FileDTO fileDTO) {
    Directory parentDirectory = directoryService.getDirectoryById(fileDTO.getDirectoryId());
    File file = fileService.createFileFromPush(fileDTO, parentDirectory);
    return responseMapper.toPushResponseFromFile(file);
  }

  private void validatePushDTO(PushDTO pushDTO) {
    if(pushDTO.getProjectFullDTO() == null && pushDTO.getDirectoryFullDTO() == null && pushDTO.getFileDTO() == null) {
      throw new InvalidPushException("Push request body is invalid, does not contain project/directory/file");
    } 
  }

  private void newUserProjectActivity(PushDTO pushDTO, Project project, Branch branch) {
    User user = userDetailServiceImpl.getUserByEmail(pushDTO.getOwnerEmail());
    
    UserProjectActivity activity = new UserProjectActivity();
    activity.setUser(user);
    activity.setProject(project);
    activity.setBranch(branch);

    userProjectActivityService.createUserProjectActivity(activity);
  }
}
