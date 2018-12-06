package com.zxb.concurrent.art.chapter07;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java中的原子操作类
 * 原子更新基本数据类型：
 * AtomicInteger：原子更新整型
 * AtomicLong：原子更新长整型
 * AtomicBoolean：原子更新布尔类型
 *
 * @author Mr.zxb
 * @date 2018-12-06 09:12
 */
public class AtomicIntegerTest {

    private static AtomicInteger integer = new AtomicInteger(0);

    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + integer.incrementAndGet());
            System.out.println(Thread.currentThread().getName() + ":" + integer.get());
        }).start();

        System.out.println(Thread.currentThread().getName() + ":" + integer.incrementAndGet());
        System.out.println(Thread.currentThread().getName() + ":" + integer.get());

        System.out.println(integer.compareAndSet(integer.get(), 3));
        System.out.println(integer.weakCompareAndSet(integer.get(), 4));
        System.out.println(Thread.currentThread().getName() + ":" + integer.get());
    }
}
