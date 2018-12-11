package com.zxb.concurrent.art.chapter10;

/**
 * @author Mr.zxb
 * @date 2018-12-11 09:19
 */
public class MainChild extends Main {

    static {
        System.out.println("child:" + 1);
    }

    {
        System.out.println("child:" + 2);
    }

    public MainChild() {
        System.out.println("child:" + 3);
    }

    public static void main(String[] args) {
        // static块是最先加载
        // 普通代码块是在类初始化时候前加载
        // 构造方法加载
        new MainChild();
    }
}
