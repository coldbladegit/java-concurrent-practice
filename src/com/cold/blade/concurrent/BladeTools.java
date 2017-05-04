package com.cold.blade.concurrent;

/**
 * @author cold_blade
 * @date 2017年5月4日
 * @version 1.0
 */
public class BladeTools {

  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
