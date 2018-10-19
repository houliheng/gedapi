package com.gq.ged.async;

import jodd.util.BCrypt;

/**
 * Created by wyq_tomorrow on 2018/7/4.
 */
public class ModifyPassword {
  public static void main(String[] args) {
    String socialCreditCode="92450981MA5L9Q398L";
    String test= socialCreditCode.substring(socialCreditCode.length() - 6, socialCreditCode.length());
    String pass = BCrypt.hashpw(test, "$2a$10$LnBOX/Y75eUMFtUyN/THse");
    System.out.println(pass);
  }
}
