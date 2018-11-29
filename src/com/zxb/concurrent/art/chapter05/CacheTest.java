package com.zxb.concurrent.art.chapter05;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Mr.zxb
 * @date 2018-11-29 21:05:12
 */
public class CacheTest {

    public static void main(String[] args) throws InterruptedException {

        IntStream.range(0, 2).mapToObj(i -> new Thread(() -> Cache.put(i+"", i))).forEach(Thread::start);

        TimeUnit.SECONDS.sleep(1);

        IntStream.range(0, 10).mapToObj(i -> new Thread(() -> System.out.println(Cache.get(i+"")))).forEach(Thread::start);

    }
}
