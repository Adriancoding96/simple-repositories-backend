package com.adrian.simple_repositories.wrapper;

import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.adrian.simple_repositories.model.marker.Node;

public class PushResponseWrapper {

  private PushResponseDTO response;
  private Node node;

  public PushResponseWrapper() {

  }

  public PushResponseWrapper(PushResponseDTO response, Node node) {
    this.response = response;
    this.node = node;
  }

  public PushResponseDTO getResponse() {
    return response;
  }

  public Node getNode() {
    return node;
  }

  public void setResponse(PushResponseDTO response) {
    this.response = response;
  }

  public void setNode(Node node) {
    this.node = node;
  }
}
