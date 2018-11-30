package com.zxb.concurrent.art.chapter05;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Mr.zxb
 * @date 2018-11-30 09:26
 */
public class BoundedQueueTest {

    public static void main(String[] args) {

        BoundedQueue<String> boundedQueue = new BoundedQueue<>(5);

        IntStream.range(0, 8).mapToObj(i -> new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(boundedQueue.remove());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).forEach(Thread::start);

        IntStream.range(0, 6).mapToObj(i -> new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                boundedQueue.add(i + "");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).forEach(Thread::start);


    }
}
