package com.ma.AnnoationAndReflect.reflect;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  private String id;
  private String username;
  private String password;

  //....省略
  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
  }
}
