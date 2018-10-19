package com.gq.ged.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by wyq_tomorrow on 2018/3/31.
 */
public class TestThreadPoolTask implements Runnable {
  public static Map<String, List> map = new ConcurrentHashMap<String, List>();
  private int id;
  private String[] array;

  public TestThreadPoolTask(int id, String[] array) {
    this.id = id;
    this.array = array;
  }

  @Override
  public void run() {
    if (array.length > 0) {
      for (int i = 0; i < array.length; i++) {
        String temp = array[i];
        String[] tt = temp.split("\\d");
        String word = tt[0];
        String num = tt[1];
        if (map.get(word) != null) {
          List nums = map.get(word);
          nums.add(num);
          map.put(word, nums);
        } else {
          List nums = new ArrayList();
          nums.add(num);
          map.put(word, nums);
        }
      }
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String[] getArray() {
    return array;
  }

  public void setArray(String[] array) {
    this.array = array;
  }
}
