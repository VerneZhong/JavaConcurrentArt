package com.zxb.concurrent.art.chapter08;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.zxb
 * @date 2018-12-07 10:06
 */
public class Main {

    public static void main(String[] args) {

        System.out.println(ctlOf(6, 8));

        ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 10, 5000L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());


    }

    private static int ctlOf(int rs, int wc) { return rs | wc; }
}
