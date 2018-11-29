package com.zxb.concurrent.art.chapter05;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的使用方式，使用读写锁来提高读操作的并发性，也保证每一次的写操作都对所有的读写操作的可见性
 * @author Mr.zxb
 * @date 2018-11-29 20:55:42
 */
public class Cache {

    /**
     * 缓存存储对象，用非线程安全的HashMap作为缓存的实现
     */
    private static Map<String, Object> map = new HashMap<>();

    /**
     * 读写锁，来保证线程安全
     */
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 读锁
     */
    private static Lock readLock = readWriteLock.readLock();

    /**
     * 写锁
     */
    private static Lock writeLock = readWriteLock.writeLock();

    /**
     * 获取一个key对应的value
     * @param key
     * @return
     */
    public static final Object get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 设置key对应的value，并返回旧的值
     * @param key
     * @param value
     */
    public static final Object put(String key, Object value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 清空缓存所有内容
     */
    public static final void clear() {
        writeLock.lock();
        try {
            map.clear();
        } finally {
            writeLock.unlock();
        }
    }


}
