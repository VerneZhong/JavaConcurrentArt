package com.zxb.concurrent.art.chapter04;

/**
 * @author Mr.zxb
 * @date 2018-11-26 22:25:42
 */
public class SimpleHttpServerTest {

    public static void main(String[] args) throws Exception {
        SimpleHttpServer server = new SimpleHttpServer();
        server.setBasePath("E:\\workSpace\\JavaConcurrentArt\\resources\\html");
        server.setPort(8080);
        server.start();
    }
}
