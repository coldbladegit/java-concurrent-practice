package com.cold.blade.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author cold_blade
 * @date 2017年5月22日
 * @version 1.0
 */
public class ExampleCountDownLatch {
  public static void test() {
    
  }
  
  public long timeTasks(int nTheads, final Runnable task) {
    CountDownLatch startGate = new CountDownLatch(1);
    CountDownLatch endGate = new CountDownLatch(nTheads);
  }
}
