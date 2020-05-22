package com.leo.classloader;

/**
 * @author leo
 * @create 2020-05-22 13:24
 */
public class ClassReadyAndInit {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        System.out.println("通过单例访问对象A：" + Singleton.A);
        System.out.println("通过单例访问对象B：" + Singleton.B);
    }
}

class Singleton {
    public static int A;
    private static Singleton singleton = new Singleton();

    public Singleton() {
        A++;
        B++;
        System.out.println("A: " + A);
        System.out.println("B: " + B);
    }

    public static int B = 0;

    public static Singleton getInstance() {
        return singleton;
    }
}
