package com.hch.net;

import com.hch.utils.net.HttpEngine2;

import java.io.IOException;

/**
 * Created by huaiwang on 09/16/2014.
 */
public class HttpEngine2Test {
    public static void main(String[] args) throws IOException {
        HttpEngine2 eng = new HttpEngine2();
        String html = eng.get("http://www.baidu.com");
        System.out.println(html);
    }
}
