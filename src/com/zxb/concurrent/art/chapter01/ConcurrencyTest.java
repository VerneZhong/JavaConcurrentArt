package com.zxb.concurrent.art.chapter01;

/**
 * 串行和并发执行并累加操作的时间
 * @author Mr.zxb
 * @date 2018-11-13 20:50:00
 */
public class ConcurrencyTest {

    public static final long COUNT = 10000L;

    public static void main(String[] args) throws InterruptedException {

        // 当并发累加不超过百万次时，速度会比串行累加要慢，因为线程有创建和上下文切换的开销
        concurrency();
        serial();
    }

    /**
     * 并行
     * @throws InterruptedException
     */
    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            int a = 0;
            for (int i = 0; i < COUNT; i++) {
                a += 5;
            }
        });
        thread.start();

        int b = 0;
        for (int i = 0; i < COUNT; i++) {
            b--;
        }
        thread.join();

        long time = System.currentTimeMillis() - start;
        System.out.println("concurrency：" + time + ", b = " + b);
    }

    /**
     * 串行
     */
    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (int i = 0; i < COUNT; i++) {
            a += 5;
        }
        int b = 0;
        for (int i = 0; i < COUNT; i++) {
            b--;
        }

        long time = System.currentTimeMillis() - start;
        System.out.println("serial：" + time + ", b = " + b +  ", a = " + a);
    }
}
