/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.suanfa;

public class demo3_InsertSort {

  public static void main(String[] args) {
    int[] a = {3, 2, 5, 1, 6, 4, 7, 9, 8};
    sort(a);
    print(a);
  }

  static void sort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      int temp = arr[i]; // 不再使用  swap() 方法
      for (int j = i; j > 0 && arr[j] < arr[j - 1]; j--) {
        arr[j] = arr[j - 1];
        arr[j - 1] = temp;
      }
    }
  }

  static void swap(int[] a, int i, int j) {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }

  static void print(int[] a) {
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + " ");
    }
  }
}
