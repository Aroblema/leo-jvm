package com.leo.classloader;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author leo
 * @create 2020-05-19 18:14
 */
public class MyClassLoaderTest2 {

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

        /**
         * 重写类加载方法，不委派给双亲加载
         *
         * @param name
         * @param resolve
         * @return
         */
        @Override
        protected Class<?> loadClass(String name, boolean resolve) {
            synchronized (getClassLoadingLock(name)) {
                // First, check if the class has already been loaded
                Class<?> c = findLoadedClass(name);
                long t0 = System.nanoTime();
                if (c == null) {
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
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
        Class<?> clazz = classLoader.loadClass("java.lang.String"); // 将自定义String.class放置指定目录下
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("main");
        method.invoke(obj);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }

}
