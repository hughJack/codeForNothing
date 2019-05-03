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
 * 描述 : 排序成功的基础是   子数组的完成排序
 * 然后 合并两个子数组
 */
public class demo5_merge {

  public static void main(String[] args) {

    int arr[] = {1, 4, 7, 8, 3, 6, 9};
    sort(arr);
  }

  static void sort(int[] arr) {
    merge(arr);
  }

  // 演示数组的合并
  static void merge(int[] arr) {
    int mid = arr.length >> 1; // len/2
    System.out.println(mid);

    int temp[] = new int[arr.length];
    int i = 0; // 指向前一个数组
    int j = mid + 1; // 指向后一个数组
    int k = 0;// 指向 temp 数组
    // 这个条件真的要命啊!!!
    while (i <= mid && j < arr.length) {
      if (arr[i] <= arr[j]) {
        temp[k++] = arr[i++];
      } else {
        temp[k++] = arr[j++];
      }
      print(temp);
    }
    // 子数组中剩余的数据  放在末尾
    while (i <= mid) {
      temp[k++] = arr[i++];
    }
    print(temp);
    while (j < arr.length) {
      temp[k++] = arr[j++];
    }
    print(temp);
  }

  static void print(int[] a) {
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + " ");
    }
    System.out.println();
  }

}
