package com.leo.classloader;

/**
 * @author leo
 * @create 2020-05-22 12:34
 */
public class InvokeClassNotInitInterface {
    public static void main(String[] args) {
        System.out.println("---------->" + Son3.num);
    }
}

interface Parent3 {
    TestDemo testDemo = new TestDemo();
}

class Son3 implements Parent3 {
    public static int num = 1;
}

class TestDemo {
    public TestDemo() {
        System.out.println("---------->2");
    }
}
