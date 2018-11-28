package com.zxb.concurrent.art.chapter04;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 简单的Web服务器
 *
 * @author Mr.zxb
 * @date 2018-11-26 22:02:44
 */
public class SimpleHttpServer {

    /**
     * 处理HTTPRequest的线程池
     */
    private static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>(1);

    /**
     * SimpleHTTPService的根路径
     */
    private static String basePath;

    private static ServerSocket serverSocket;

    private static int PORT;

    public void setPort(int port) {
        if (port <= 0) {
            throw new IllegalArgumentException("The port is invalid.");
        }
        SimpleHttpServer.PORT = port;
    }

    public void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        } else {
            throw new IllegalArgumentException("The basePath is invalid.");
        }
    }

    public void start() throws Exception {
        serverSocket = new ServerSocket(PORT);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
            // 接收一个客户端的Socket，生成一个HTTPRequestHandler，放入线程池执行
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }

    static class HttpRequestHandler implements Runnable {

        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String line;
            BufferedReader br = null, reader = null;
            PrintWriter out = null;
            InputStream in = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                // 由相对路径计算出绝对路径
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());
                // 如果请求资源的后缀为JPG或者ico，则读取资源并输出
                if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i = 0;
                    while ((i = in.read()) != -1) {
                        baos.write(i);
                    }
                    byte[] array = baos.toByteArray();
                    out.println("HTTP/1.0 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + array.length);
                    out.println("");
                    socket.getOutputStream().write(array, 0, array.length);
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.0 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: text/html;charset=UTF-8");
                    out.println("");
                    while ((line = br.readLine()) != null) {
                        out.write(line);
                    }
                }
                out.flush();
            } catch (Exception e) {
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            } finally {
                close(br, in, reader, out, socket);
            }
        }

        /**
         * 关闭流或者socket
         * @param closeables
         */
        private void close(Closeable... closeables) {
            if (closeables != null) {
                for (Closeable closeable : closeables) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
