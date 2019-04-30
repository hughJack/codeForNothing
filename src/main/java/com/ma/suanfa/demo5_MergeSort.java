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
public class demo5_MergeSort {

  public static void main(String[] args) {

    int arr[] = {1, 4, 7, 8, 3, 6, 9};
    sort(arr, 0, arr.length - 1);
    print(arr);
  }

  static void sort(int[] arr, int left, int right) {
    // merge(arr, left, 4, right);
    if (left == right) {
      return;
    }
    //  分成2份,
    int mid = left + (right - left) / 2;
    //  左边排序
    sort(arr, left, mid);
    //  右边排序
    sort(arr, mid + 1, right);
    //  合并数组
    merge(arr, left, mid + 1, right);
  }

  // 子数组已经排好序的情况下,演示数组的合并,,该方法不递归
  static void merge(int[] arr, int leftPtr, int rightPtr, int rightBound) {
    int mid = rightPtr - 1;

    int len = rightBound - leftPtr + 1;
    int temp[] = new int[len];
    int i = leftPtr; // 指向前一个数组
    int j = rightPtr; // 指向后一个数组
    int k = 0;// 指向 temp 数组
    // 这个条件真的要命啊!!!
    while (i < mid && j <= rightBound) {
      temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
    }
    while (i <= mid) {
      temp[k++] = arr[i++];
    }
    while (j <= rightBound) {
      temp[k++] = arr[j++];
    }
    for (int m = 0; m < temp.length; m++) {
      arr[leftPtr + m] = temp[m];
    }
  }

  static void print(int[] a) {
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + " ");
    }
    System.out.println();
  }

}
