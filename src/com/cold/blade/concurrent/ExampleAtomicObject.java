package com.cold.blade.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cold_blade
 * @date 2017年5月4日
 * @version 1.0
 */
public final class ExampleAtomicObject {
  private AtomicInteger counter = new AtomicInteger();

  public static void test() {
    ExampleAtomicObject ao = new ExampleAtomicObject();
    int threadCnt = 5;
    CountDownLatch start = new CountDownLatch(threadCnt);
    ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
    for (int index = 0; index < threadCnt; ++index) {
      executor.execute(new WorkTask(ao, start));
      start.countDown();
    }
    executor.shutdown();
  }

  public int getNext() {
    return counter.incrementAndGet();
  }

  private static class WorkTask implements Runnable {
    ExampleAtomicObject ao;
    CountDownLatch start;

    WorkTask(ExampleAtomicObject ao, CountDownLatch start) {
      this.ao = ao;
      this.start = start;
    }

    @Override
    public void run() {
      try {
        /**等待所有工作线程一起开始执行**/
        start.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      for (int index = 0; index < 5; ++index) {
        System.out.println(ao.getNext());
        BladeTools.sleep(20);
      }
    }
  }
}
