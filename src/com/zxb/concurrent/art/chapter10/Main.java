package com.zxb.concurrent.art.chapter10;

/**
 * @author Mr.zxb
 * @date 2018-12-11 09:17
 */
public class Main {

    static {
        System.out.println("parent:" + 1);
    }


    public Main() {
        System.out.println("parent:" + 3);
    }

    {
        System.out.println("parent:" + 2);
    }


}
