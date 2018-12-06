package com.zxb.concurrent.art.chapter07;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 原子更新数组类：
 * AtomicIntegerArray：原子更新整型数组里的元素
 * AtomicLongArray：原子更新长整型数组里的元素
 * AtomicReferenceArray：原子更新引用数据类型数组的元素
 * @author Mr.zxb
 * @date 2018-12-06 09:21
 */
public class AtomicIntegerArrayTest {

    private static int[] value = new int[]{1, 2};

    private static String[] strings = new String[] {"1", "2"};

    private static AtomicIntegerArray array = new AtomicIntegerArray(value);

    private static AtomicReferenceArray<String> referenceArray = new AtomicReferenceArray<>(strings);

    public static void main(String[] args) {
        // AtomicIntegerArray操作的是数组的副本，每次会将数组复制一份
        array.getAndSet(0, 3);
        System.out.println(array.get(0));
        System.out.println(value[0]);

        referenceArray.getAndSet(0, "3");
        System.out.println(referenceArray.get(0));
        System.out.println(strings[0]);
    }
}
