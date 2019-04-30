/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.suanfa;

import lombok.extern.slf4j.Slf4j;

// 实现快速排序
@Slf4j
public class demo6_QuickSort {

  public static void main(String[] args) {
    int[] arr = {7, 3, 2, 8, 1, 9, 5, 4, 10, 6};
    // int[] arr = {7, 2};
    sort(arr, 0, arr.length - 1);
    print(arr);
  }

  static void sort(int[] arr, int leftBound, int rightBound) {
    if (leftBound >= rightBound) {
      return;
    }
    int mid = partition(arr, leftBound, rightBound);
    print(arr);
    sort(arr, leftBound, mid - 1);
    sort(arr, mid + 1, rightBound);
  }

  // 对数据进行分区
  static int partition(int[] arr, int leftBound, int rightBound) {
    log.info("###partition###  leftBound={}, rightBound={}", leftBound, rightBound);
    int pivot = arr[rightBound];// 轴!!
    int left = leftBound;
    int right = rightBound - 1;
    while (left < right) {
      while (left <= right && arr[left] <= pivot) {
        left++; //
      }
      while (left < right && arr[right] > pivot) {
        right--; //
      }
      if (left < right) {
        swap(arr, left, right);
      }
      print(arr);
    }
    log.info(" ##### left={}, right={} ", left, right);
    log.info(" ##### arr[left] = {} arr[right] = {}", arr[left], arr[right]);
    if (pivot < arr[left]) {
      swap(arr, left, rightBound);
    }
    return left;
  }

  static void swap(int[] a, int n, int m) {
    int temp = a[m];
    a[m] = a[n];
    a[n] = temp;
  }

  static void print(int[] a) {
    System.out.println("");
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + " ");
    }
    System.out.println("");
  }
}
