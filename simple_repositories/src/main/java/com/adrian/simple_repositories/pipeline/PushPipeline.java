/********************************************
 *           -- PushPipeline --             *
 *                                          *
 * PushPipeline is used to control          *
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

package com.adrian.simple_repositories.pipeline;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adrian.simple_repositories.dto.branch.BranchDTO;
import com.adrian.simple_repositories.dto.file.FileDTO;
import com.adrian.simple_repositories.dto.folder.FolderFullDTO;
import com.adrian.simple_repositories.dto.push.PushDTO;
import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.adrian.simple_repositories.exception.InvalidPushException;
import com.adrian.simple_repositories.mapper.ResponseMapper;
import com.adrian.simple_repositories.model.Branch;
import com.adrian.simple_repositories.model.Folder;
import com.adrian.simple_repositories.model.Project;
import com.adrian.simple_repositories.model.User;
import com.adrian.simple_repositories.model.UserProjectActivity;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.service.BranchService;
import com.adrian.simple_repositories.service.FileService;
import com.adrian.simple_repositories.service.FolderService;
import com.adrian.simple_repositories.service.ProjectService;
import com.adrian.simple_repositories.service.UserProjectActivityService;
import com.adrian.simple_repositories.service.implementation.UserDetailServiceImpl;

@Component
public class PushPipeline {

  private final ProjectService projectService;
  private final FolderService folderService;
  private final FileService fileService;
  private final BranchService branchService;
  private final UserDetailServiceImpl userDetailServiceImpl;
  private final UserProjectActivityService userProjectActivityService;
  private final ResponseMapper responseMapper;

  @Autowired
  public PushPipeline(ProjectService projectService, FolderService folderService, FileService fileService,
          BranchService branchService, UserDetailServiceImpl userDetailServiceImpl, UserProjectActivityService userProjectActivityService, ResponseMapper responseMapper) {
    this.projectService = projectService;
    this.folderService = folderService;
    this.fileService = fileService;
    this.branchService = branchService;
    this.userDetailServiceImpl = userDetailServiceImpl;
    this.userProjectActivityService = userProjectActivityService;
    this.responseMapper = responseMapper;
  }

  public PushResponseDTO processPush(PushDTO pushDTO) { 
    validatePushDTO(pushDTO); 
    if(pushDTO.getProjectFullDTO() != null) return handleProjectPush(pushDTO);
    if(pushDTO.getFolderFullDTO() != null) return handleFolderPush(pushDTO.getFolderFullDTO());
    if(pushDTO.getFileDTO() != null) return handleFilePush(pushDTO.getFileDTO());
    throw new InvalidPushException("Push request body is invalid, does not contain project/folder/file");
  }

  private PushResponseDTO handleProjectPush(PushDTO pushDTO) {
    User user = userDetailServiceImpl.getUserByEmail(pushDTO.getOwnerEmail());
    Project project = projectService.createProjectFromPush(pushDTO.getProjectFullDTO(), user);
    Branch mainBranch = branchService.createBranch(new BranchDTO(null, "main", LocalDateTime.now(), null), project);
    newUserProjectActivity(pushDTO, project, mainBranch);
    return responseMapper.toPushResponseFromProject(project);   
  }

  private PushResponseDTO handleFolderPush(FolderFullDTO folderDTO) {
    Project project = projectService.getProjectById(folderDTO.getProjectId());
    Folder folder = folderService.createFolderFromPush(folderDTO, project);
    return responseMapper.toPushResponseFromFolder(folder);
   }

  private PushResponseDTO handleFilePush(FileDTO fileDTO) {
    Folder parentFolder = folderService.getFolderById(fileDTO.getFolderId());
    File file = fileService.createFileFromPush(fileDTO, parentFolder);
    return responseMapper.toPushResponseFromFile(file);
  }

  private void validatePushDTO(PushDTO pushDTO) {
    if(pushDTO.getProjectFullDTO() == null && pushDTO.getFolderFullDTO() == null && pushDTO.getFileDTO() == null) {
      throw new InvalidPushException("Push request body is invalid, does not contain project/folder/file");
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
