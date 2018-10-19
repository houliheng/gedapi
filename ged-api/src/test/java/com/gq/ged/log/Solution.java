package com.gq.ged.log;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by wyq_tomorrow on 2018/3/31.
 */
public class Solution {
  public static Map<String, Object> map = new HashMap<String, Object>();

  public static void groupAndSort(String[] array) {
    // TODO:
  }

  public List<String[]> getResult() {
    return null;
  }

  public static void main(String[] args) {
    String[] arr1 = {"A1", "A2", "B1"};
    String[] arr2 = {"C1", "B2", "D1"};
    List<String[]> list = new ArrayList<>();
    list.add(arr1);
    list.add(arr2);
    ArrayBlockingQueue<Runnable> arrayWorkQueue = new ArrayBlockingQueue(10);
    int count = 20;
    ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, // 时间单位
        arrayWorkQueue, new ThreadPoolExecutor.DiscardOldestPolicy());
    for (int i = 1; i <= count; i++) {
      TestThreadPoolTask task = new TestThreadPoolTask(i, list.get(i));
      threadPool.execute(task);
    }
  }
}
