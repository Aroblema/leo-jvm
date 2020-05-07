package com.leo.classloader;

import com.sun.crypto.provider.DESedeKeyFactory;

/**
 * @author leo
 * @create 2020-05-08 5:46
 */
public class TestJDKClassLoader {

    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader()); // 启动类加载器
        System.out.println(DESedeKeyFactory.class.getClassLoader().getClass().getName()); // DES加密算法，扩展类加载器，路径\jdk1.8.0_241\jre\lib\ext下
        System.out.println(TestJDKClassLoader.class.getClassLoader().getClass().getName()); // 当前类本身，应用程序类加载器
        System.out.println(ClassLoader.getSystemClassLoader().getClass().getName()); // 当前系统类加载器，应用程序类加载器
    }

}
