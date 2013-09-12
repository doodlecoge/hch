package com.hch.utils.net;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * User: huaiwang
 * Date: 13-9-12
 * Time: 下午2:47
 */
public class HttpEngineTest {
    public static void main(String[] args) throws IOException, URISyntaxException {
        HttpEngine eng = new HttpEngine();

        eng.get("http://www.baidu.com").consume();

        String more = eng.get("more").getHtml();
        System.out.println(more);
    }
}
