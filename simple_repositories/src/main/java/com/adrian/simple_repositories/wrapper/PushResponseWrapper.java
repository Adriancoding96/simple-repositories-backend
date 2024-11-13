package com.adrian.simple_repositories.wrapper;

import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.Content;

public class PushResponseWrapper {

  private PushResponseDTO response;
  private Content content;

  public PushResponseWrapper() {

  }

  public PushResponseWrapper(PushResponseDTO response, Content content) {
    this.response = response;
    this.content = content;
  }

  public PushResponseDTO getResponse() {
    return response;
  }

  public Content getContent() {
    return content;
  }

  public void setResponse(PushResponseDTO response) {
    this.response = response;
  }

  public void setContent(Content content) {
    this.content = content;
  }
}
