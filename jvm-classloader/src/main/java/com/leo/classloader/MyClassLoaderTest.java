package com.leo.classloader;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author leo
 * @create 2020-05-19 18:14
 */
public class MyClassLoaderTest {

    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        // 重写findClass方法
        @Override
        protected Class<?> findClass(String name) {
            byte[] data = new byte[0];
            try {
                data = loadByte(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // defineClass方法将一个字节数组转化为Class对象，这个字节数组是读取.class文件后生成的（loadByte方法）
            return defineClass(name, data, 0, data.length);
        }

        // 将硬盘中的.class文件以字节数组的方式读取到内存中
        private byte[] loadByte(String name) throws IOException {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader classLoader = new MyClassLoader("D:\\test");
        // 注意：此类全路径名和项目路径一致，因双亲委派机制的存在，按classPath路径向上委派查找
        // User类放置于resources文件夹下，按需移动到项目路径com.leo.classloader下测试类加载器
        Class<?> clazz = classLoader.loadClass("com.leo.classloader.User");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout");
        method.invoke(obj);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }

}
