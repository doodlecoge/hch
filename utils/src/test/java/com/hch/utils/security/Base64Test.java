package com.hch.utils.security;


import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by huaiwang on 13-12-16.
 */
public class Base64Test {
    @Test
    public void testB64() {
        String orig = "Hello\u574F\u8349";
        System.out.println(orig);
        String en = Base64.encode(orig);
        System.out.println(en);
        String raw = Base64.decode(en);
        System.out.println(raw);
        Assert.assertEquals("equal after en/decode", orig, raw);
    }
}
