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
 *
 * @param
 * @return
 * @author: sunshumeng @ 2019/4/29 13:53
 */
public class demo2_Bubble {

  public static void main(String[] args) {

    int[] arr = {3, 5, 1, 6, 9, 8, 2, 7, 4, 3};
    sort(arr);
    print(arr);
  }

  static void sort(int[] a) {
    for (int j = a.length - 1; j > 0; j--) {
      findMax(a, j);
    }
  }

  private static void findMax(int[] a, int j) {
    for (int i = 0; i < j; i++) {
      if (a[i] > a[i + 1]) {
        swap(a, i, i + 1);
      }
    }
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
  }

}
