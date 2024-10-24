package com.adrian.simple_repositories.service;

import java.util.List;

import com.adrian.simple_repositories.dto.push.PushDTO;
import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.adrian.simple_repositories.model.Push;

public interface PushService {
  PushResponseDTO createPush(PushDTO pushDTO);
  Push getPushById(Long pushId);
  PushDTO getPushDTOById(Long pushId);
  List<Push> getAllPushes();
  List<PushDTO> getAllPushesAsDTOs();
  Push getLatestPushByBranchName(String branchName);
  PushDTO getLatestPushByBranchNameAsDTO(String branchName);

}
