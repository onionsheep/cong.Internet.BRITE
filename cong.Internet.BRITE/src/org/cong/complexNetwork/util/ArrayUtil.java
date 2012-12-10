package org.cong.complexNetwork.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ArrayUtil {
  public static Logger logger = LogManager.getLogger(ArrayUtil.class);

  /**
   * 要求a是升序排列的，返回第一个比target大的元素的下标
   * 
   * @param a
   * @param target
   * @return
   */
  public static int firstBigger(double[] a, double target) {
    int low = 0;
    int high = a.length - 1;
    int mid = 0;
    while (low < high) {
      mid = (low + high) / 2;
      if (target < a[mid]) {
        high = mid - 1;
      } else if (target > a[mid]) {
        low = mid + 1;
      } else {
        break;
      }
    }
    if ((a[mid] < target) && ((mid + 1) < a.length)) {
      mid += 1;
    }
    return mid;
  }
}
