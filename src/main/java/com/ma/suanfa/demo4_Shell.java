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
    long before = System.currentTimeMillis();
    sort(arr);
    long after = System.currentTimeMillis();
    System.out.println((after - before));
    print(arr);
  }

  static void sort(int[] a) {
    // 间隔序列  knuth 序列  h = 3 * h + 1
    // 找最大的那个gap
    // length >= 3 * h + 1;
    int h = 1;
    // (15 - 1) / 3 = 4
    // (15 - 0) / 3 = 5
    while (h <= a.length / 3) {
      h = h * 3 + 1;
    }
    System.out.println(a.length);
    System.out.println(h);
    // int gap = 4; // 可以使用 h
    for (int gap = 4; gap > 0; gap = (gap - 1) / 3) {
      for (int i = gap; i < a.length; i += gap) { // 拿出第i张牌
        for (int j = i; j > gap - 1; j -= gap) { // 与他前面的所有进行比较 , 如果小于则交换
          if (a[j] < a[j - gap]) {
            swap(a, j, j - gap);
          }
          print(a);
          System.out.println(i + " = i");
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
  }
}
