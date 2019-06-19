package com.ma.dataStructure;

import lombok.Getter;

@Getter
public class Node {
  private long val;
  public Node leftNode;
  public Node rightNode;

  public Node(long val) {
    this.val = val;
  }


}
