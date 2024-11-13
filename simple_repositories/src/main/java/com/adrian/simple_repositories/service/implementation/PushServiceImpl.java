package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import com.adrian.simple_repositories.dto.push.PushDTO;
import com.adrian.simple_repositories.dto.push.PushRequestDTO;
import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.adrian.simple_repositories.exception.PushNotFoundException;
import com.adrian.simple_repositories.mapper.PushMapper;
import com.adrian.simple_repositories.model.Directory;
import com.adrian.simple_repositories.model.Push;
import com.adrian.simple_repositories.model.File;
import com.adrian.simple_repositories.facade.PushFacade;
import com.adrian.simple_repositories.repository.PushRepository;
import com.adrian.simple_repositories.service.PushService;
import com.adrian.simple_repositories.wrapper.PushResponseWrapper;

/*
 * Implementation of PushService interface.
 *
 * Implements methods to manage pushes, creation, retrieval,
 * and processing push data.
 * 
 * Injects PushRepository for database operations,
 * PushMapper for DTO conversion, PushFacade for processing push logic.
 */
@Service
public class PushServiceImpl implements PushService {

  private final PushRepository pushRepository;
  private final PushMapper pushMapper;
  private final PushFacade pushFacade;

  @Autowired
    public PushServiceImpl(PushRepository pushRepository, PushMapper pushMapper, PushFacade pushFacade) {
    this.pushRepository = pushRepository;
    this.pushMapper = pushMapper;
    this.pushFacade = pushFacade;
  }

  /*
   * Method checks if push is successful if not, returns a response signaling
   * the push failed, if the push was successfull a new push is persisted to the 
   * push table cotaining persisted entity that has handled by PushFacade, the 
   * persisted entity can be either a File or Directory, if success push also
   * returns a response containing type of push, success flag, and the identifier 
   * for the new version of the repository.
   *
   * @param pushDTO: DTO containing push data
   * @return pushResponse: DTO containing push response data
   */
  @Override
  public PushResponseDTO createPush(PushRequestDTO requestDTO) {
    PushResponseWrapper responseWrapper = pushFacade.processPush(requestDTO);
    if(!responseWrapper.getResponse().isSuccess()) {
      return responseWrapper.getResponse();
    }
    Push push = pushMapper.toEntity(requestDTO);
    if(responseWrapper.getNode() instanceof Directory) {
      Directory directory = (Directory) responseWrapper.getNode();
      push.setDirectory(directory);
      pushRepository.save(push);
    } else {
      File file = (File) responseWrapper.getNode();
      push.setFile(file);
      pushRepository.save(push);
    }
    return responseWrapper.getResponse();
  }

  /*
   * Retrieves push by id
   *
   * @param pushId: ID of push
   * @return push: returns push entity fetched from database
   * @throws PushNotFoundException: throws exception if push not is found by ID
   */
  @Override
  @Transactional(readOnly = true)
  public Push getPushById(Long pushId) {
    return pushRepository.findById(pushId)
      .orElseThrow(() -> new PushNotFoundException("Could not find push with id: " + pushId));
  }

  /*
   * Retrieves a push as a DTO by ID
   *
   * @param pushId: ID of push
   * @return pushDTO: DTO containing push data
   * @throws PushNotFoundException: throws exception if push is not found by ID
   */
  @Override
  public PushDTO getPushDTOById(Long pushId) {
    Push push = getPushById(pushId);
    return pushMapper.toDTO(push);
  }

  /*
   * Retrieves all push entities
   *
   * @return pushes: returns list of all push entities
   * @throws PushNotFoundException: throws exeption if no pushes are found
   */
  @Override
  public List<Push> getAllPushes() {
    List<Push> pushes = pushRepository.findAll();
    for(Push push : pushes) {
      push.toString();
    }
    if(pushes == null || pushes.isEmpty()) throw new PushNotFoundException("No pushes could be found");
    return pushes;
  } 

  /*
   * Retrieves all push entities and converts them to DTOs
   *
   * @return pushDTOs: returns list of push DTOs
   * @throws PushNotFoundException: throws exception if no pushes are found
   */
  @Override
  public List<PushDTO> getAllPushesAsDTOs() {
    return getAllPushes().stream()
      .map(pushMapper::toDTO)
      .collect(Collectors.toList());
  }

  /* Retrieves latest push to branch by branch name
   *
   * @param branchName: contains name of branch
   * @return push: contains branch fethched from database by most recent push and branch name
   * @throws PushNotFoundException: throws exception if no push is found by branch name
   */
  @Override
  public Push getLatestPushByBranchName(String branchName) {
    return pushRepository.findFirstByBranchBranchNameOrderByDateTimeDesc(branchName)
      .orElseThrow(() -> new PushNotFoundException("Could not fetch last push to branch: " + branchName));
  }

  /*
   * Retrieves latest push to branch by branch name and converts it to a DTO
   *
   * @param branchName: contains name of branch 
   * @return pushDTO: DTO containing data of branch fetched from database by most recent push and branch name
   * @throws PushNotFoundException: throws exception if no push is found by branch name
   */
  @Override
  public PushDTO getLatestPushByBranchNameAsDTO(String branchName) {
    return pushMapper.toDTO(getLatestPushByBranchName(branchName));
  }

}
