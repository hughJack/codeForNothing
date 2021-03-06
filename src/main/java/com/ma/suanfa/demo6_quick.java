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
public class demo6_quick {

  public static void main(String[] args) {
     //int[] arr = {7, 3, 6, 9, 2, 6, 8, 8, 1, 9, 5, 6, 4, 9, 6, 10};
    // int[] arr = {7, 3, 9, 2, 6, 8, 8, 1, 9, 5, 4, 9, 10};
    // int[] arr = {7, 3, 2, 6, 8, 1, 9, 5, 4, 10};
    // int[] arr = {7, 3, 2, 5, 6, 1, 10, 8, 4, 9};
    //int[] arr = {6, 4, 5, 1, 11, 2, 3};
    // int[] arr = {4, 6};
    // int[] arr = {6, 4};
     int[] arr = {7, 3, 6, 9, 2, 6, 8, 8, 9, 5, 6, 4, 9, 6, 10,1};
    //sort(arr, 0, arr.length - 1);
    //print(arr);
    sort2(arr, 0, arr.length - 1);
    print(arr);
  }

  static void sort(int[] arr, int leftBound, int rightBound) {
    if (leftBound >= rightBound) {
      return;
    }
    int mid = partition(arr, leftBound, rightBound);
    sort(arr, leftBound, mid - 1);
    sort(arr, mid + 1, rightBound);
  }

  // 对数据进行分区
  // 定位bug的位置???
  static int partition(int[] arr, int leftBound, int rightBound) {
    log.info("###partition###  leftBound={}, rightBound={}", leftBound, rightBound);
    int pivot = arr[rightBound];// 轴!!!
    int left = leftBound;
    int right = rightBound - 1;
    // 只有两个的时候  left = 0 right = 0;
    //{6, 4, 5, 1, 11, 2, 3};
    while (left <= right) {
      // 左指针的含义是?指向较大的数值?  left 指向大于等于轴的值
      while (left <= right && arr[left] <= pivot) {
        left++; //
      }
      // 数组越界 -1
      // 获取  小于等轴的值, 因为此时是不涉及交换
      while (left <= right && arr[right] > pivot) {
        right--; //
      }
      // System.out.println(" -- left=" + left + ", right=" + right);
      // System.out.println(" -- arr[left]=" + arr[left] + " arr[right]=" + arr[right]);
      if (left < right) {
        swap(arr, left, right);
      }
      // System.out.println("--");
      // print(arr);
      // System.out.println();
    }
    // if (arr[left] > pivot)
    // print(arr);
    // System.out.println(left);
    // System.out.println(rightBound);
    // 此本身的含义是就是  交换两个数的位置
    // 实现方法是: left指向较大的数,rightBound指向较小的数
    swap(arr, left, rightBound);
    // }
    return left;
  }


  public static int partition2(int[] arr, int left, int right, int point) {
    int leftPtr = left - 1;
    int rightPtr = right;
    while (true) {
      while (leftPtr < rightPtr && arr[++leftPtr] < point) ;
      while (leftPtr < rightPtr && arr[--rightPtr] > point) ;
      if (leftPtr >= rightPtr) {
        break;
      } else {
        swap(arr, leftPtr, rightPtr);
      }
    }
    // 关键字和当前所指的进行交换
    swap(arr, leftPtr, right);
    return leftPtr;
  }

  public static void sort2(int[] arr, int leftBound, int rightBound) {
    if (leftBound >= rightBound) {
      return;
    }
    int point = arr[rightBound];
    int mid = partition2(arr, leftBound, rightBound, point);
    sort2(arr, leftBound, mid - 1);
    sort2(arr, mid + 1, rightBound);
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
