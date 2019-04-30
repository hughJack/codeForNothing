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
 * 最优解 : 时间复杂度为O(n)，平时是O(n平方)
 */
public class demo2_BubbleSort {
  public static void main(String[] args) {
    int[] a = { 3, 2, 5, 1, 6, 4, 7, 9, 8 };
    sort(a);
    print(a);
  }

  /*
   * 描述 : 主要是引入一个标记变量flag，每一次外循环创建一个新的flag来观测这一次所有的内循环中有没有出现交换的情况，
   * 如果没有的话就说明所有的位置都正确，然后跳出外循环结束排序工作
   *
   */
  static void sort(int[] a) {
    for (int i = a.length - 1; i > 0; i--) {
      boolean flag = true;
      for (int j = 0; j < i; j++) {
        if (a[j] > a[j + 1]) {
          swap(a, j, j + 1);
          flag = false; //代码优化，使时间复杂度为O(n)，平时是O(n平方)
        }
      }
      if (flag) {
        break;
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
