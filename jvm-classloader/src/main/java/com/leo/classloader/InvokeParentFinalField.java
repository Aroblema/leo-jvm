package com.leo.classloader;

/**
 * @author leo
 * @create 2020-05-08 6:42
 */
public class InvokeParentFinalField {
    public static void main(String[] args) {
        System.out.println("---------->" + Son1.num);
    }
}

class Parent1 {
    public static final int num = 1;
    static {
        System.out.println("---------->2");
    }
}

class Son1 extends Parent1 {
    static {
        System.out.println("---------->3");
    }
}
