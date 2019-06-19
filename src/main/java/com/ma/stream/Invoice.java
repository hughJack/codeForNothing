package com.ma.stream;

import javax.naming.directory.Attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Invoice {

  private int id;

  private String customer;

  private String title;

  public Double amount;
}
