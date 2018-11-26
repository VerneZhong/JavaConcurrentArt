package com.zxb.concurrent.art.chapter04;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 模拟数据库连接池，获取、使用和释放连接的过程，而客户端获取连接的过程被设定为等待超时的模式，
 * 也就是在1000毫秒内如果无法获取到可用连接，将会返回给客户端一个null
 *
 * @author Mr.zxb
 * @date 2018-11-26 11:16
 */
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                // 连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    /**
     * 在mills内无法获取到连接，将会返回null
     *
     * @param mills
     * @return
     * @throws InterruptedException
     */
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            // 完全超时
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
            } else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
        return null;
    }


    private static class ConnectionDriver {

        static class ConnectionHandler implements InvocationHandler {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (Objects.equals("commit", method.getName())) {
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                return null;
            }
        }

        /**
         * 创建一个Connection代理，在commit时休眠100毫秒
         *
         * @return
         */
        public static final Connection createConnection() {
            return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(), new Class<?>[]{Connection.class}, new ConnectionHandler());
        }
    }
}
