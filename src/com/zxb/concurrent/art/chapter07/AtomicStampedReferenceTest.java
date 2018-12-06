package com.zxb.concurrent.art.chapter07;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，可用于原子的更新数据和数据的版本号，
 * 可以解决使用CAS进行原子更新时可能出现的ABA问题
 * @author Mr.zxb
 * @date 2018-12-06 15:44
 */
public class AtomicStampedReferenceTest {

    private static AtomicStampedReference<Integer> money = new AtomicStampedReference<>(19, 0);

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
//                while (true) {
                    Integer m = money.getReference();
                    Integer stamp = money.getStamp();
                    if (m < 20 && atomicInteger.get() < 2) {
                        if (money.compareAndSet(m, m + 20, stamp, stamp + 1)) {
                            System.out.println("余额小于20，充值成功，当前余额：" + money.getReference());
                        }
                    }
//                }
            }).start();
        }

        new Thread(() -> {
//            while (true) {
                Integer m = money.getReference();
                Integer stamp = money.getStamp();
                if (m > 10) {
                    System.out.println("余额大于10：" + m);
                    if (money.compareAndSet(m, m - 10, stamp, stamp + 1)) {
                        System.out.println("成功消费10元，剩余余额：" + money.getReference());
                    }
                } else {
                    System.out.println("余额不足10元");
                }
//            }
        }).start();
    }
}
