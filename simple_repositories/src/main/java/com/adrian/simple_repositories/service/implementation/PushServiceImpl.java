package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import com.adrian.simple_repositories.dto.push.PushDTO;
import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.adrian.simple_repositories.exception.PushNotFoundException;
import com.adrian.simple_repositories.mapper.PushMapper;
import com.adrian.simple_repositories.model.Push;
import com.adrian.simple_repositories.facade.PushFacade;
import com.adrian.simple_repositories.repository.PushRepository;
import com.adrian.simple_repositories.service.PushService;

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
   * Creates a new push
   *
   * @param pushDTO contains push data
   * @return push response data
   */
  @Override
  public PushResponseDTO createPush(PushDTO pushDTO) {
    pushRepository.save(pushMapper.toEntity(pushDTO));
    return pushFacade.processPush(pushDTO);
  }

  /*
   * Retrieves push by id
   *
   * @param pushId contains ID of push
   * @return push entity
   * @throws PushNotFoundException if push not found by ID
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
   * @param pushId contains id of push
   * @return PushDTO containing push data
   * @throws PushNotFoundException if push not found by ID
   */
  @Override
  public PushDTO getPushDTOById(Long pushId) {
    Push push = getPushById(pushId);
    System.out.println(push.toString());
    return pushMapper.toDTO(push);
  }

  /*
   * Retrieves all push entities
   *
   * @return list of all push entities
   * @throws PushNotFoundException ig no pushes are found
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
   * @return list of pushDTO objects
   * @throws PushNotFoundException if no pushes are found
   */
  @Override
  public List<PushDTO> getAllPushesAsDTOs() {
    return getAllPushes().stream()
      .map(pushMapper::toDTO)
      .collect(Collectors.toList());
  }

  /* Retrieves latest push to branch by branch name
   *
   * @param name of branch
   * @return latest push to branch, by branch name
   * @throws PushNotFoundException if no push is found by branch name
   */
  @Override
  public Push getLatestPushByBranchName(String branchName) {
    return pushRepository.findFirstByBranchBranchNameOrderByDateTimeDesc(branchName)
      .orElseThrow(() -> new PushNotFoundException("Could not fetch last push to branch: " + branchName));
  }

  /*
   * Retrieves latest push to branch by branch name and converts it to a DTO
   *
   * @param name of branch
   * @return pushDTO containing data of latest push to branch
   * @throws PushNotFoundException if no push is found by branch name
   */
  @Override
  public PushDTO getLatestPushByBranchNameAsDTO(String branchName) {
    return pushMapper.toDTO(getLatestPushByBranchName(branchName));
  }

}
