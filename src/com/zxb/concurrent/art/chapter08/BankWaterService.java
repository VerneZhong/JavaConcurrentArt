package com.zxb.concurrent.art.chapter08;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CyclicBarrier可以用于多线程计算数据，最后合并计算结果的场景。
 * @author Mr.zxb
 * @date 2018-12-06 16:27
 */
public class BankWaterService implements Runnable {

    /**
     * 创建4个屏障，处理完之后执行当前类的run方法
     */
    private CyclicBarrier barrier = new CyclicBarrier(4, this);

    /**
     * 假设有4个sheet需要处理，创建4个线程
     */
    private Executor executor = Executors.newFixedThreadPool(4);

    /**
     * 用于保存每个线程处理的结果
     */
    private Map<String, Integer> map = new ConcurrentHashMap<>();

    private void count() {
        AtomicInteger integer = new AtomicInteger(0);
        for (int i = 0; i < 4; i++) {
            executor.execute(() -> {
                // 存储计算结果
                map.put(Thread.currentThread().getName(), integer.getAndIncrement());
                try {
                    // 计算完成后，插入一个屏障
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run() {
        int result = 0;
        // 汇总每个线程计算的结果
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result += entry.getValue();
        }
        // 输出结果
        map.put("result", result);
        System.out.println("result: " + result);
    }

    public static void main(String[] args) {

        BankWaterService service = new BankWaterService();

        service.count();
    }
}
