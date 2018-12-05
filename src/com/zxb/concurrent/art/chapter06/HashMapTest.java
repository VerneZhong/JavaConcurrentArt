package com.zxb.concurrent.art.chapter06;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 多线程环境下，HashMap的put会导致死循环，导致CPU的使用率接近100%
 * @author Mr.zxb
 * @date 2018-11-30 10:28
 */
public class HashMapTest {

    public static void main(String[] args) throws InterruptedException {

        final Map<String, String> map = new HashMap<>(2);

        Thread t = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                new Thread(() -> map.put(UUID.randomUUID().toString(), ""), "ftf" + i).start();
            }
        }, "ftf");

        t.start();
        t.join();
    }

}
