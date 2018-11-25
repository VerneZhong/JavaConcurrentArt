package com.zxb.concurrent.art.chapter04;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 通过JMX来查看一个普通Main方法创建了多少个线程
 *
 * @author Mr.zxb
 * @date 2018-11-25 11:42:55
 */
public class MultiThread {

    public static void main(String[] args) {

        // 获取Java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        // 不需要获取同步的monitor和synchronized信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        // 遍历线程信息，仅打印线程ID和线程名称
        for (ThreadInfo info : threadInfos) {
            System.out.println(info.getThreadId() + "-" + info.getThreadName());
        }
    }
}
