# JVM
## 一、类加载机制
### 1. 类加载过程
多个java文件经编译打包生成可运行jar包，最终由java命令运行某个**主类**的**main函数**启动程序。
首先，需要**通过类加载器把主类加载到JVM**。
主类在**运行过程中**如果要使用到其他类，会逐步加载这些类（**jar包里的类不是一次性全部加载的，是使用到时才加载的**）。

类加载到使用过程有如下几步：
**加载 >> 验证 >> 准备 >> 初始化 >> 使用 >> 卸载**
- 加载：在硬盘上查找并通过IO读入字节码文件，使用到类时才会加载（调用类的main方法，new对象等）
- 验证：校验字节码文件的正确性
- 准备：给类的静态变量分配内存，并赋予默认值（对象赋值为null，常量如int赋值为0）-- ***见常量池？***
- 解析：将符号引用替换为直接引用，该阶段会将一些静态方法（符号引用-比如main方法）替换为指向数据所存内存的指针或者句柄等（直接引用）
  这就是所谓的**静态链接**过程（类加载期间完成）
  **动态链接**是在程序运行期间完成符号引用替换为直接引用的过程
- 初始化：**将类的静态变量初始化为指定的值，执行静态代码块**

#### 类加载有趣示例（天坑）
== 见Module: jvm-classloader ==
##### - InvokeParentField.java
```java
/*
 * Module: jvm-classloader
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
```
输出结果：
```
---------->2
---------->1
```

##### - InvokeParentFinalField.java
```java
/*
 * Module: jvm-classloader
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
```
输出结果：
```
---------->1
```

##### - InvokeParentFinalField2.java
```java
/*
 * Module: jvm-classloader
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
```
输出结果：
```
---------->2
---------->945739838 // 任意int随机数
```

### 2.类加载器与双亲委派
类的加载由类加载器实现，Java里有如下几种类加载器：
- 启动类加载器：负责加载支撑JVM运行，位于JRE的lib目录下的核心类库，如rt.jar、chartset.jar等
- 扩展类加载器：负责加载支撑JVM运行，位于JRE的lib目录下ext扩展目录中的jar包
- 应用程序类加载器：负责加载ClassPath路径下的类包，也就是用户自己写的类
- 自定义加载器：负责加载用户自定义路径下的类包

类加载示例：
==见com.leo.classloader.TestJDKClassLoader==
