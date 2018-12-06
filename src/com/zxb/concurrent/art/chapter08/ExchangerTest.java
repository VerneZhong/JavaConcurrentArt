package com.zxb.concurrent.art.chapter08;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger（交换者）是一个用于线程间协作的工具类。Exchanger用于进行线程间的数据交换。它提供一个同步点，在这个同步点，两个线程可以
 * 交换彼此的数据。这俩个线程通过exchange方法交换数据。
 * Exchanger用于遗传算法，也可以用于校对工作
 * @author Mr.zxb
 * @date 2018-12-06 17:20
 */
public class ExchangerTest {

    /**
     * 创建交换者
     */
    private static Exchanger<String> exgr = new Exchanger<>();

    /**
     * 创建2个线程的线程池
     */
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        threadPool.execute(() -> {
            String s = "银行流水A";
            try {
                exgr.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            String s = "银行流水A";
            try {
                String a = exgr.exchange(s);
                System.out.println("A和B数据是否一致：" + s.equals(a));
                System.out.println("A录入的是：" + a);
                System.out.println("B录入的是：" + s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
