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

  public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
    CountDownLatch startGate = new CountDownLatch(1);
    CountDownLatch endGate = new CountDownLatch(nThreads);
    for (int index = 0; index < nThreads; ++index) {
      Thread t = new Thread(new Runnable() {

        @Override
        public void run() {
          try {
            startGate.await();
            task.run();
          } catch (InterruptedException ignored) {
          } finally {
            endGate.countDown();
          }
        }
      });
      t.start();
    }
    long start = System.nanoTime();
    startGate.countDown();
    endGate.await();
    return System.nanoTime() - start;
  }
}
