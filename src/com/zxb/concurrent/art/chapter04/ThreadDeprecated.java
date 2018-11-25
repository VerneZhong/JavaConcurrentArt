package com.zxb.concurrent.art.chapter04;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 过时的线程方法suspend()暂停、resume()恢复、stop()停止
 *
 * @author Mr.zxb
 * @date 2018-11-25 21:27:47
 */
public class ThreadDeprecated {

    public static void main(String[] args) throws InterruptedException {

        DateFormat format = new SimpleDateFormat("HH:mm:ss");

        Thread printThread = new Thread(() -> {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            while (true) {
                try {
                    System.out.println(Thread.currentThread().getName() + "Run at " + dateFormat.format(new Date()));
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Print-Thread");
        printThread.setDaemon(true);
        printThread.start();
        TimeUnit.SECONDS.sleep(3);

        // 将Print-Thread线程进行暂停、输出内容工作停止
        printThread.suspend();
        System.out.println("main suspend Print-Thread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

        // 将Print-Thread内容进行恢复
        printThread.resume();
        System.out.println("main resume Print-Thread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

        // 将Print-Thread线程终止
        printThread.stop();
        System.out.println("main stop Print-Thread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

    }
}
