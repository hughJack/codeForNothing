/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.suanfa;

import com.ma.code.Test;

/*
 * 描述 : 必须选定的是  排序的间隔
 * 考的并不多
 * 空间复杂度 : 没有使用额外的空间,所以是O(n)
 * 时间复杂度: n一三
 */
public class demo4_Shell {

  public demo4_Shell() {
  }

  public static void main(String[] args) {

    int[] arr = {9, 6, 11, 3, 5, 12, 8, 7, 10, 15, 14, 4, 1, 13, 2};

    // 间隔序列  knuth 序列  h = 3 * h + 1
    // 找最大的那个gap
    // length >= 3 * h + 1;
    // (15 - 1) / 3 = 4
    // (15 - 0) / 3 = 5
    int h = 1;
    while (h <= arr.length / 3) {
      h = h * 3 + 1;
    }
    System.out.println("arr.length = " + arr.length);// 15
    System.out.println("gap = " + h); // 4

    //test(arr, h);
    //print(arr);

    demo(arr, h);
    print(arr);

    sort(arr, h);
    print(arr);
    //long before = System.currentTimeMillis();
    //sort(arr, h);
    //long after = System.currentTimeMillis();
    //System.out.println((after - before));
    //print(arr);
    //
    //demo(arr, h);
  }

  static void sort(int[] arr, int h) {
    // int gap = 4; // 可以使用 h
    // 外部使用  希尔排序 增大排序的间隔
    // 内部使用  插入排序
    for (int gap = h; gap > 0; gap = (gap - 1) / 3) { // 变更间隔
      for (int i = gap; i < arr.length; i += gap) {  // 拿出第i张牌，与他前面的所有shell间隔的进行比较 , 如果小于则交换
        for (int j = i; j > gap - 1; j -= gap) {     //
          if (arr[j] < arr[j - gap]) {
            swap(arr, j, j - gap);
          }
        }
      }
    }
  }

  private static void demo(int[] arr, int h) {
    // 参考示例
    while (h > 0) {
      // 插入排序的实现
      int temp = 0;
      for (int i = h; i < arr.length; i += h) {
        temp = arr[i];
        int j = i;
        while (j > h - 1 && arr[j - h] >= temp) {
          arr[j] = arr[j - h];
          j -= h;
        }
        arr[i] = temp;
      }
      h = (h - 1) / 3;
    }
  }


  public static void test(int[] arr, int gap) {
    for (; gap > 0; gap = (gap - 1) / 3) {
      // 标准的插入排序
      // 与前面的进行比较  所以 必须是  > gap-1
      // gap == 1 的时候  则是 > 0
      for (int i = gap; i < arr.length; i += gap) {
        for (int j = i; j > gap - 1; j -= gap) {
          if (arr[j] < arr[j - gap]) {
            swap(arr, j, j - gap);
          }
        }
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
    System.out.println("");
  }
}
