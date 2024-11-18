package com.adrian.simple_repositories.wrapper;

import com.adrian.simple_repositories.dto.push.PushResponseDTO;
import com.adrian.simple_repositories.model.Branch;
import com.adrian.simple_repositories.model.marker.Node;

public class PushResponseWrapper {

  private PushResponseDTO response;
  private Node node;
  private Branch branch;

  public PushResponseWrapper() {

  }

  public PushResponseWrapper(PushResponseDTO response, Node node, Branch branch) {
    this.response = response;
    this.node = node;
    this.branch = branch;
  }

  public PushResponseDTO getResponse() {
    return response;
  }

  public Node getNode() {
    return node;
  }

  public Branch getBranch() {
    return branch;
  }

  public void setResponse(PushResponseDTO response) {
    this.response = response;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }
}
