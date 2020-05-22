package com.leo.classloader;

/**
 * @author leo
 * @create 2020-05-22 12:53
 */
public class NewArrayNotInitClass {
    public static void main(String[] args) {
        Person[] people = new Person[4];
    }
}

class Person {
    static {
        System.out.println("----------Person的构造函数");
    }
}
