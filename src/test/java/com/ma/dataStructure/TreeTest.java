package com.ma.dataStructure;

import static org.junit.Assert.*;

public class TreeTest {
  public static void main(String[] args) {
    Tree tree = new Tree();
    tree.insert(10L);
    tree.insert(15L);
    tree.insert(20L);
    tree.insert(3L);
    System.out.println(tree);
  }
}
