package com.cold.blade.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 循环关卡
 * @author cold_blade
 * @date 2017年5月23日
 * @version 1.0
 */
public class ExampleCyclicBarrier {
  private final CyclicBarrier barrier;
  private final BladeWorker[] workers;

  public static void test() {
    ExampleCyclicBarrier example = new ExampleCyclicBarrier(new Game(10));
    example.start();
  }

  public ExampleCyclicBarrier(Game game) {
    int count = Runtime.getRuntime().availableProcessors();
    System.out.println("cpu_count = " + count);
    barrier = new CyclicBarrier(count, new Guardian());
    workers = new BladeWorker[count];
    for (int index = 0; index < count; ++index) {
      workers[index] = new BladeWorker(game);
    }
  }

  public void start() {
    for (BladeWorker elem : workers) {
      new Thread(elem).start();
    }
  }

  private class BladeWorker implements Runnable {
    private final Game game;

    public BladeWorker(Game game) {
      this.game = game;
    }

    @Override
    public void run() {
      while (!game.isOver()) {
        System.out.println(game.brokeBarrier());
        try {
          barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
          return;
        }
      }
    }
  }

  private class Guardian implements Runnable {

    @Override
    public void run() {
      System.out.println("congratulations!!!");
    }
  }

  private static class Game {
    private AtomicInteger barrier;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private boolean isOver = false;

    public Game(int barrier) {
      this.barrier = new AtomicInteger(barrier);
    }

    public int brokeBarrier() {
      BladeTools.sleep(500);
      if (isOver()) {
        return 0;
      }
      int b = barrier.decrementAndGet();
      if (0 == b) {
        rwLock.writeLock().lock();
        isOver = true;
        rwLock.writeLock().unlock();
      }
      return b;
    }

    public boolean isOver() {
      boolean b;
      rwLock.readLock().lock();
      b = isOver;
      rwLock.readLock().unlock();
      return b;
    }
  }
}
