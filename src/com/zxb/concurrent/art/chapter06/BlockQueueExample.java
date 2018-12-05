package com.zxb.concurrent.art.chapter06;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Java的阻塞队列
 * ArrayBlockingQueue：一个由数组组成的有界阻塞队列，此队列按照FIFO原则对元素进行排序
 * LinkedBlockingQueue：一个由链表结构组成的有界阻塞队列，此队列的默认和最大长度为Integer.MAX_VALUE，也是按照FIFO原则对元素进行排序
 * PriorityBlockingQueue：一个支持优先级排序的无界阻塞队列，默认情况下元素采取自然顺序升序排列
 * DelayQueue：一个使用优先级队列实现的无界阻塞队列，队列使用PriorityQueue来实现。队列中的元素必须实现Delayed接口，在创建元素时可以
 * 指定多久才能从队列中获取当前元素。只有在延迟期满时才能从队列中提取元素
 * SynchronousQueue：一个不存储元素的阻塞队列
 * LinkedTransferQueue：一个由链表结构组成的无界阻塞队列
 * LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列，所谓双向队列指的是可以从队列的两端插入和移出元素
 * @author Mr.zxb
 * @date 2018-12-05 10:13
 */
public class BlockQueueExample {

    @Test
    public void testArrayBlockingQueue() {
        // 非公平阻塞队列
        ArrayBlockingQueue fairQueue = new ArrayBlockingQueue(10, true);

        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                try {
                    fairQueue.put("" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
//                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(fairQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Test
    public void testLinkedBlockingQueue() {

    }

    @Test
    public void testDelayQueue() {

    }

    @Test
    public void testSynchronousQueue() throws InterruptedException {
        // 支持公平访问队列，设置为true，则等待的线程会采用先进先出的顺序访问队列
        SynchronousQueue queue = new SynchronousQueue(true);
        System.out.println(queue.take());
        queue.put("1");
        System.out.println(queue.take());
//        queue.put("2");
    }
}
