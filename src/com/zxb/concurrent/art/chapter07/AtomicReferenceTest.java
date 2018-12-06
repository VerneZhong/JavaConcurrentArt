package com.zxb.concurrent.art.chapter07;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 原子更新引用类型：
 * AtomicReference：原子更新引用类型
 * AtomicReferenceFieldUpdater：原子更新引用类型里的字段
 * AtomicMarkableReference：原子更新带有标记位的引用类型。可以原子更新一个布尔类型的标记和引用类型。
 * @author Mr.zxb
 * @date 2018-12-06 09:30
 */
public class AtomicReferenceTest {

    private static AtomicReference<User> reference = new AtomicReference<>();

    /**
     * 要想原子的更新字段需要两步：第一因为原子更新字段类都是抽象类，每次使用必须使用静态方法newUpdater()创建一个更新器，并且需要设置需要更新的类和属性；
     * 第二要更新的字段必须使用public volatile修饰
     */
    private static AtomicReferenceFieldUpdater<User, String> fieldUpdater = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "name");

    private static AtomicReferenceFieldUpdater<User, Integer> integerAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(User.class, Integer.class, "old");



    public static void main(String[] args) {

        User user = new User("zxb", 26);

        reference.set(user);

//        user = new User("zxb1", 26);
        User updateU = new User("zhangsan", 20);

//        reference.getAndSet(updateU);
//        reference.compareAndSet(user, updateU);
        System.out.println(reference.get().getName());
        System.out.println(reference.get().getOld());

        System.out.println("---------------");

//        fieldUpdater.getAndSet(updateU, "lisi");
        System.out.println(fieldUpdater.compareAndSet(updateU, "zhangsan", "lisi"));
        System.out.println(fieldUpdater.get(updateU));

        System.out.println("---------------");
        integerAtomicReferenceFieldUpdater.getAndSet(updateU, 30);
        System.out.println(integerAtomicReferenceFieldUpdater.get(updateU));

    }

    static class User {
        public volatile String name;
        public volatile Integer old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public int getOld() {
            return old;
        }
    }
}
