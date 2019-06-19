package com.ma.dataStructure;

import java.util.HashMap;

public class Tree {
  public Node root;

  public void insert(Long val) {
    Node newNode = new Node(val);
    Node current = root;
    Node parent;
    if (root == null) {
      root = newNode;
      return;
    } else {
      while (true) {
        // 父节点的引用印象当前的节点
        parent = current; //
        if (current.getVal() > val) {
          current = current.rightNode;
          if (current == null) {
            parent.rightNode = newNode;
            return;
          }
        } else {
          current = current.leftNode;
          if (current == null) {
            parent.leftNode = newNode;
            return;
          }
        }
      }
    }

  }

  public Node find(long value) {
    Node current = root;
    while (current != null && current.getVal() != value) {
      if (current.getVal() > value) {
        current = current.rightNode;
      } else if (current.getVal() < value) {
        current = current.leftNode;
      }
    }
    return current;
  }

  //   遍历二叉树， 遍历的意思
//  前序遍历 中序遍历 后序遍历
//  访问根节点  左子树  右子树
  public void frontOrder(Node currentNode) {
    if (currentNode != null) {
      System.out.println(currentNode.getVal());
      frontOrder(currentNode.leftNode);
      frontOrder(currentNode.rightNode);
    }
  }

  // 从小到大  排序
  // 先访问左 访问根节点 访问右节点
  public void inOrder(Node currentNode) {
    if (currentNode != null) {
      inOrder(currentNode.leftNode);
      System.out.println(currentNode.getVal());
      inOrder(currentNode.rightNode);
    }
  }

  // 从大到小  排序
  // 访问左节点  访问右节点  访问根节点
  public void backOrder(Node currentNode) {
    if (currentNode != null) {
      backOrder(currentNode.rightNode);
      System.out.println(currentNode.getVal());
      backOrder(currentNode.leftNode);
    }
    new HashMap<>(16);
  }

  public void deleteNode(Node currentNode) {

  }

}
