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


/*
 * PushFacade serves as a facade for processing push operations of projects, directories, and files.
 * It uses diferent services to create a push depending of the content of the request.
 *
 * Injects PushService to handle push requests containing project data, DirectoryService to handle
 * push requests containing directory data, FileService to handle push requests containing file data,
 * branch service to handle branch validation, UserProjectActivityService to handle user activity by
 * project and branch, UserDetailServiceImpl to handle user validation, ResponseMapper to build responses
 * for push data.
 */
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

  /*
   * Processes push based on content of pushDTO
   * Determines the push type (project / directory / file) and passes
   * data to appropriate handler method.
   *
   * @param pushDTO: DTO containing push request data
   * @return pushResponseDTO: DTO containing push response data
   * @throws InvalidPushException: throws exception if request contains invalid data
   */
  public PushResponseDTO processPush(PushDTO pushDTO) { 
    validatePushDTO(pushDTO); 
    if(pushDTO.getProjectFullDTO() != null) return handleProjectPush(pushDTO);
    if(pushDTO.getDirectoryFullDTO() != null) return handleDirectoryPush(pushDTO.getDirectoryFullDTO());
    if(pushDTO.getFileDTO() != null) return handleFilePush(pushDTO.getFileDTO());
    throw new InvalidPushException("Push request body is invalid, does not contain project/directory/file");
  }

  /* TODO refactor method to separate branch logic in to a separate mehtod
   *
   * Handles push operation for request containing project data
   *
   * @param pushDTO containing push request data
   * @return pushResponseDTO containing push response data
   */
  private PushResponseDTO handleProjectPush(PushDTO pushDTO) {
    User user = userDetailServiceImpl.getUserByEmail(pushDTO.getOwnerEmail());
    Project project = projectService.createProjectFromPush(pushDTO.getProjectFullDTO(), user);
    Branch mainBranch = branchService.createBranch(new BranchDTO(null, "main", LocalDateTime.now(), null), project);
    newUserProjectActivity(pushDTO, project, mainBranch);
    return responseMapper.toPushResponseFromProject(project);   
  }

  /*
   * Handles push operation for request containing directory data
   *
   * @param pushDTO: DTO containing push request data
   * @return pushResponseDTO: DTO containing push response data
   */
  private PushResponseDTO handleDirectoryPush(DirectoryFullDTO directoryDTO) {
    Project project = projectService.getProjectById(directoryDTO.getProjectId());
    Directory directory = directoryService.createDirectoryFromPush(directoryDTO, project);
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
   * Validates data in push request by checking that request contains either project, directory, or file
   *
   * @param pushDTO: DTO containing push request data
   * @throws InvalidPushException if project, directory, and file data is null
   */
  private void validatePushDTO(PushDTO pushDTO) {
    if(pushDTO.getProjectFullDTO() == null && pushDTO.getDirectoryFullDTO() == null && pushDTO.getFileDTO() == null) {
      throw new InvalidPushException("Push request body is invalid, does not contain project/directory/file");
    } 
  }

  /*
   * TODO Does UserProjectActivity need renaming? UserProjectBranchActivity?
   * TODO UserProjectActivity exception handling
   * TODO Separate user fetching from user service to separate method and add user as input param?
   *
   * Records user activity by branch and project with UserProjectActivity
   *
   * @param pushDTO: DTO containing push request data
   * @param project: project used in push operation
   * @param branch: branch used in push operation
   */
  private void newUserProjectActivity(PushDTO pushDTO, Project project, Branch branch) {
    User user = userDetailServiceImpl.getUserByEmail(pushDTO.getOwnerEmail());
    
    UserProjectActivity activity = new UserProjectActivity();
    activity.setUser(user);
    activity.setProject(project);
    activity.setBranch(branch);

    userProjectActivityService.createUserProjectActivity(activity);
  }
}
