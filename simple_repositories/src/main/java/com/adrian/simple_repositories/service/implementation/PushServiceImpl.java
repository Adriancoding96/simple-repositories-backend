package com.adrian.simple_repositories.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.simple_repositories.dto.PushDTO;
import com.adrian.simple_repositories.dto.PushResponseDTO;
import com.adrian.simple_repositories.mapper.PushMapper;
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

}
