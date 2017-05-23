package com.cold.blade.concurrent;

/**
 * @author cold_blade
 * @date 2017年5月4日
 * @version 1.0
 */
public class ExampleMain {
  public static void main(String[] argv) {
    /**test atomic object**/
    ExampleAtomicObject.test();
    /**test CyclicBarrier**/
    ExampleCyclicBarrier.test();
  }
}
