package com.adrian.simple_repositories.mapper;

import com.adrian.simple_repositories.dto.PushDTO;
import com.adrian.simple_repositories.model.Push;

import org.springframework.stereotype.Component;

@Component
public class PushMapper {

  public PushDTO toDTO(Push push) {
    if(push == null) return null;
    
    PushDTO dto = new PushDTO();
    dto.setId(push.getId());
    dto.setCommitHash(push.getCommitHash());
    dto.setCommitMessage(push.getCommitMessage());


    return dto;
  }

  public Push toEntity(PushDTO dto) {
    if(dto == null) return null;

    Push push = new Push();
    push.setId(dto.getId());
    push.setCommitHash(dto.getCommitHash());
    push.setCommitMessage(dto.getCommitMessage());
    
    return push;
  }

}
