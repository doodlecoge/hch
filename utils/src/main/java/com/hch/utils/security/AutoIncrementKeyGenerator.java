package com.hch.utils.security;

import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * User: huaiwang
 * Date: 13-9-12
 * Time: 上午9:23
 */
public class AutoIncrementKeyGenerator {
    private static long id = 0;
    private static final AutoIncrementKeyGenerator ins;


    static {
        ins = new AutoIncrementKeyGenerator();
    }

    private AutoIncrementKeyGenerator() {
    }


    public static AutoIncrementKeyGenerator getInstance() {
        return ins;
    }

    public static AutoIncrementKeyGenerator getInstance(long start) {
        id = start;
        return ins;
    }

    public synchronized String next() {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(id++);
        byte[] longBytes = bb.array();
        byte[] nine = new byte[9];
        for (int i = 0; i < 8; i++) {
            nine[i] = longBytes[i];
        }
        int rnd = new Random().nextInt() % 256;
        nine[8] = (byte) rnd;



        return "";
    }
}
