package com.hch.utils.security;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * User: huaiwang
 * Date: 13-9-12
 * Time: 上午9:31
 */
public class AutoIncrementKeyGeneratorTest {
    public static void main(String[] args) {
        byte b = (byte) 255;


        System.out.println(Integer.toBinaryString(b>>2));
    }
}
