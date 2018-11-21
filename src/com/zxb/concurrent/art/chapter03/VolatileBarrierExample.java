package com.zxb.concurrent.art.chapter03;

/**
 * Volatile案例：Volatile变量，JMM会在Volatile前后加上内存屏障，JMM内存屏障插入策略规则如下：
 * 在每个Volatile写操作的前面插入一个StoreStore屏障
 * 在每个Volatile写操作的后面插入一个StoreLoad屏障
 * 在每个Volatile读操作的后面插入一个LoadLoad屏障
 * 在每个Volatile读操作的后面插入一个LoadStore屏障
 * StoreStore屏障：禁止上面的普通写和下面的Volatile写重排序
 * StoreLoad屏障：防止上面的volatile写与下面可能有的volatile读/写重排序
 * LoadLoad屏障：禁止下面所有的普通操作和上面的volatile读重排序
 * LoadStore屏障：禁止下面所有的普通写操作和上面的volatile读重排序
 * @author Mr.zxb
 * @date 2018-11-21 22:01:22
 */
public class VolatileBarrierExample {

    int a;
    volatile int v1 = 1;
    volatile int v2 = 2;

    void readAndWrite() {
        // 第一个volatile读
        int i = v1;
        // 第二个volatile读
        int j = v2;
        // 普通写
        a = i + j;
        // 第一个volatile写
        v1 = i + 1;
        // 第二个volatile写
        v2 = j * 2;
    }
}
