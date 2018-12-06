package com.zxb.concurrent.art.chapter07;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicIntegerFieldUpdater：原子更新字段类
 * AtomicIntegerFieldUpdater：原子更新整型的字段的更新器
 * AtomicLongFieldUpdater：原子更新长整型的字段的更新器
 * AtomicStampedReference：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，可用于原子的更新数据和数据的版本号，可以解决使用CAS进行原子更新时可能出现的
 * ABA问题
 * （1）字段必须是volatile类型的，在线程之间共享变量时保证立即可见.eg:volatile int value = 3
 * （2）字段的描述类型（修饰符public/protected/default/private）是与调用者与操作对象字段的关系一致。也就是说调用者能够直接操作对象字段，那么就可以反射进行原子操作。但是对于父类的字段，子类是不能直接操作的，尽管子类可以访问父类的字段。
 * （3）只能是实例变量，不能是类变量，也就是说不能加static关键字。
 * （4）只能是可修改变量，不能使final变量，因为final的语义就是不可修改。实际上final的语义和volatile是有冲突的，这两个关键字不能同时存在。
 * （5）对于AtomicIntegerFieldUpdater和AtomicLongFieldUpdater只能修改int/long类型的字段，不能修改其包装类型（Integer/Long）。如果要修改包装类型就需要使用AtomicReferenceFieldUpdater。
 * @author Mr.zxb
 * @date 2018-12-06 10:21
 */
public class AtomicIntegerFieldUpdaterTest {

    private static AtomicIntegerFieldUpdater<User> integerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "old");

    public static void main(String[] args) {

        User user = new User("zxb", 26);

        System.out.println(integerFieldUpdater.getAndIncrement(user));
        System.out.println(integerFieldUpdater.get(user));

    }

    static class User {
        private String name;
        public volatile int old;

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
