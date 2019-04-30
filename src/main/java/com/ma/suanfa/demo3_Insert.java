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
 * 描述 : 插入排序, 有点像冒泡排序反过来,冒泡是大的往后, 插入小的往前
 * 对于基于有序的数组最好用,
 * 稳定
 *
 */
public class demo3_Insert {

  public static void main(String[] args) {
    int[] a = {3, 2, 5, 1, 6, 4, 7, 9, 8};
    sort(a);
    print(a);
  }

  static void sort(int[] a) {
    for (int i = 1; i < a.length; i++) { // 拿出第i张牌
      for (int j = i; j > 0; j--) { // 与他前面的所有进行比较 , 如果小于则交换
        if (a[j] < a[j - 1]) {
          swap(a, j, j - 1);
        }
        print(a);
        System.out.println(i + " = i");
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
