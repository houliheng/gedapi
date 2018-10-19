package com.gq.ged.async;

import java.math.BigDecimal;

/**
 * Created by wyq_tomorrow on 2018/2/2.
 */
public class TestMain {
  public static void main(String[] args) {
    A a = new B();
    test(a);
  }

  public static void test(A a) {
    System.out.println("test a");
    a.whoamI();
  }

  public static void test(B b) {
    System.out.println("test b");
    b.whoamI();
  }
}


class A {
  public void whoamI() {
    System.out.println("I am a");
  }
}


class B extends A {
  public void whoamI() {
    System.out.println("I am b");
  }
}
