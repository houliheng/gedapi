package com.gq.ged.log;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by wyq_tomorrow on 2018/3/31.
 */
public class Test {
  public static void main(String[] args) {
    System.out.println(addZeroForNum("2332sds", 32));
  }
  public static String addZeroForNum(String str, int strLength) {
    int strLen = str.length();
    if (strLen < strLength) {
      while (strLen < strLength) {
        StringBuffer sb = new StringBuffer();
        sb.append("0").append(str);// 左补0
        // sb.append(str).append("0");//右补0
        str = sb.toString();
        strLen = str.length();
      }
    }

    return str;
  }
}
