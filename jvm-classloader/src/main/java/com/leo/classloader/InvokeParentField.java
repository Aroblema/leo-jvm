package com.leo.classloader;

/**
 * @author leo
 * @create 2020-05-08 6:37
 */
public class InvokeParentField {
    public static void main(String[] args) {
        System.out.println("---------->" + Son.num);
    }
}

class Parent {
    public static int num = 1;
    static {
        System.out.println("---------->2");
    }
}

class Son extends Parent {
    static {
        System.out.println("---------->3");
    }
}
