package com.zxb.concurrent.art.chapter03;

/**
 * final域的重排序规则
 * 对于final域，编译器和处理器遵循2个重排序规则
 * 1) 在构造函数内对一个final域的写入，与随后把这个被构造对象的引用赋值给一个引用变量，这两个操作之间不能重排序
 * 2) 初次读一个包含final域的对象的引用，与随后初次读这个final域，这两个操作之间不能重排序
 * @author Mr.zxb
 * @date 2018-11-22 13:45
 */
public class FinalExample {
    /**
     * 普通变量
     */
    int i;
    /**
     * final变量
     */
    final int j;

    static FinalExample obj;

    /**
     * 构造函数
     */
    public FinalExample() {
        // 写普通域
        this.i = 1;
        // 写final域
        this.j = 2;
    }

    /**
     * 写线程A执行
     */
    public static void writer() {
        obj = new FinalExample();
    }

    /**
     * 读线程B执行
     */
    public static void reader() {
        // 读对象引用
        FinalExample object = obj;
        // 读普通域
        int a = object.i;
        // 读final域
        int b = object.j;
    }


}
