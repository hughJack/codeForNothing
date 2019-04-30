/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.CollectionsExample;

import java.util.HashMap;
import java.util.HashSet;

public class MapExample1 {

  public static void main(String[] args) {
    HashMap<Integer, Object> map = new HashMap<>();
    HashMap<Integer, Object> clone = (HashMap<Integer, Object>) map.clone();

    HashSet<Integer> set = new HashSet<>();

  }
}
