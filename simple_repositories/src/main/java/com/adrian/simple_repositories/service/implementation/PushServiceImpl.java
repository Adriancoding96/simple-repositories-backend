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
import com.adrian.simple_repositories.pipeline.PushPipeline;
import com.adrian.simple_repositories.repository.PushRepository;
import com.adrian.simple_repositories.service.PushService;



@Service
public class PushServiceImpl implements PushService {

  private final PushRepository pushRepository;
  private final PushMapper pushMapper;
  private final PushPipeline pushPipeline;

  @Autowired
  public PushServiceImpl(PushRepository pushRepository, PushMapper pushMapper, PushPipeline pushPipeline) {
    this.pushRepository = pushRepository;
    this.pushMapper = pushMapper;
    this.pushPipeline = pushPipeline;
  }

  @Override
  public PushResponseDTO createPush(PushDTO pushDTO) {
    pushRepository.save(pushMapper.toEntity(pushDTO));
    return pushPipeline.processPush(pushDTO);
  }

  @Override
  @Transactional(readOnly = true)
  public Push getPushById(Long pushId) {
    return pushRepository.findById(pushId)
      .orElseThrow(() -> new PushNotFoundException("Could not find push with id: " + pushId));
  }

  @Override
  public PushDTO getPushDTOById(Long pushId) {
    Push push = getPushById(pushId);
    System.out.println(push.toString());
    return pushMapper.toDTO(push);
  }

  @Override
  public List<Push> getAllPushes() {
    List<Push> pushes = pushRepository.findAll();
    for(Push push : pushes) {
      push.toString();
    }
    if(pushes == null || pushes.isEmpty()) throw new PushNotFoundException("No pushes could be found");
    return pushes;
  } 

  @Override
  public List<PushDTO> getAllPushesAsDTOs() {
    return getAllPushes().stream()
      .map(pushMapper::toDTO)
      .collect(Collectors.toList());
  }


  @Override
  public Push getLatestPushByBranchName(String branchName) {
    return pushRepository.findFirstByBranchBranchNameOrderByDateTimeDesc(branchName)
      .orElseThrow(() -> new PushNotFoundException("Could not fetch last push to branch: " + branchName));
  }

  @Override
  public PushDTO getLatestPushByBranchNameAsDTO(String branchName) {
    return pushMapper.toDTO(getLatestPushByBranchName(branchName));
  }

}
