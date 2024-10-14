package com.adrian.simple_repositories.service;

import com.adrian.simple_repositories.dto.PushDTO;
import com.adrian.simple_repositories.dto.PushResponseDTO;

public interface PushService {
  PushResponseDTO createPush(PushDTO pushDTO);
}
