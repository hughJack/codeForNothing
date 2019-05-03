/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.suanfa;

/*
 * 描述   : 冒泡排序
 * 最优解 : 时间复杂度为O(n平方)
 * m
 * @param
 * @return: 从后向前排序
 * @author: 每比较一次 都可能导致交换
 */
public class demo2_Bubble {

  public static void main(String[] args) {

    int[] arr = {3, 5, 1, 6, 9, 8, 2, 7, 4, 3};
    sort(arr);
    print(arr);
  }

  static void sort(int[] a) {
    // 两两比较
    for (int j = a.length - 1; j > 0; j--) {
      findMax(a, j);
    }
  }

  private static void findMax(int[] arr, int j) {
    for (int i = 0; i < j; i++) {
      if (arr[i] > arr[i + 1]) {
        swap(arr, i, i + 1);
      }
    }
    print(arr);
  }

  static void swap(int[] a, int n, int m) {
    int temp = a[m];
    a[m] = a[n];
    a[n] = temp;
  }

  static void print(int[] a) {
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + " ");
    }
    System.out.println();
  }

}
