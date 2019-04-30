/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.suanfa;

import org.junit.Test;

public class demo1_Select {


  /**
   * @author sunshumeng
   * @describe 选择排序
   */
  @Test
  public void selectSort() {
    int a[] = {8, 5, 2, 6, 9, 3, 1, 4, 0, 7}; // 从小到大选择排序
    int n = a.length;
    for (int i = 0; i < n - 1; i++) {// i为已排序序列的末尾
      int min = i;//标记
      for (int j = i + 1; j < n; j++) {
        if (a[j] < a[min]) {//后一个数与前面一个数比较，找出最小值
          min = j;
        }
      }
      if (min != i) {// 放到已排序序列的末尾，该操作很有可能把稳定性打乱，所以选择排序是不稳定的排序算法
        int temp = a[min];
        a[min] = a[i];
        a[i] = temp;
      }
    }
    for (int i = 0; i < n; i++) {
      System.out.print (a[i] + " ");
    }
  }
}