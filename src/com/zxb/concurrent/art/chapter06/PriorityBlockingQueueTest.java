package com.zxb.concurrent.art.chapter06;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Mr.zxb
 * @date 2018-12-07 10:23
 */
public class PriorityBlockingQueueTest {

    public static void main(String[] args) {

        PriorityBlockingQueue<User> queue = new PriorityBlockingQueue<>(20);

        for (int i = 0; i < 10; i++) {
            User u = new User();
            u.setPriority(ThreadLocalRandom.current().nextInt(10));
            u.setName("test" + i);
            queue.add(u);
        }

        for (int i = 0; i < queue.size(); i++) {
            System.out.println(queue.poll());
        }

    }

    static class User implements Comparable<User> {
        private int priority;
        private String name;

        public User() {
        }

        public User(int priority, String name) {
            this.priority = priority;
            this.name = name;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "username:" + name + ", priority:" + priority;
        }

        @Override
        public int compareTo(User o) {
            return this.priority < o.getPriority() ? 1 : -1;
        }
    }
}
