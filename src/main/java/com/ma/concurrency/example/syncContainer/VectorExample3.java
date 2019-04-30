package com.ma.concurrency.example.syncContainer;

import com.ma.concurrency.annoations.ThreadSafe;
import java.util.Vector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ThreadSafe
public class VectorExample3 {

    public static void main(String[] args) {
      Vector<Integer> vector = new Vector<>();
      vector.add(1);
      vector.add(2);
      vector.add(3);
      vector.add(4);
      vector.add(5);
      vector.add(6);
    }

    public static void test1(Vector<Integer> vector){

    }
    public static void test2(Vector<Integer> vector){

    }
    public static void test3(Vector<Integer> vector){

    }
    public static void test4(Vector<Integer> vector){

    }

}
