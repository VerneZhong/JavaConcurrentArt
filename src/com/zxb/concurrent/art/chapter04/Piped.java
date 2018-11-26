package com.zxb.concurrent.art.chapter04;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道流，用于线程间的数据传输，而传输的媒介为内存
 * @author Mr.zxb
 * @date 2018-11-26 09:52
 */
public class Piped {

    public static void main(String[] args) throws IOException {

        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();

        // 将输入流和输出流进行连接，否则在使用时会抛出IOException
        in.connect(out);

        Thread thread = new Thread(new Print(in), "Print-Thread");
        thread.start();

        try {
            int received;
            while ((received = System.in.read()) != -1) {
                out.write(received);
            }
        } finally {
            out.close();
        }
    }

    static class Print implements Runnable {

        private PipedReader reader;

        public Print(PipedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            int received;
            try {
                while ((received = reader.read()) != -1) {
                    System.out.print((char) received);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
