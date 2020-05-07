package com.leo.classloader;

import java.util.Random;

/**
 * @author leo
 * @create 2020-05-08 6:58
 */
public class InvokeParentFinalField2 {
    public static void main(String[] args) {
        System.out.println("---------->" + Son2.num);
    }
}

class Parent2 {
    public static final int num = new Random().nextInt();
    static {
        System.out.println("---------->2");
    }
}

class Son2 extends Parent2 {
    static {
        System.out.println("---------->3");
    }
}
