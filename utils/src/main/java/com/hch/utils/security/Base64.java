package com.hch.utils.security;

/**
 * User: huaiwang
 * Date: 13-9-12
 * Time: 下午1:20
 */
public class Base64 {
    private static final String CharSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final int StartPos = 3;

    public void encode(byte[] bytes) {
        bytes = padding(bytes);
        int size = bytes.length;
        int len = CharSequence.length();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            int b1 = bytes[i * 3 + 0] >> 2;
            int b2 = bytes[i * 3 + 0] & 0x03 << 4 + bytes[i * 3 + 1] >> 4;
            int b3 = bytes[i * 3 + 1] & 0x0f << 2 + bytes[i * 3 + 2] >> 6;
            int b4 = bytes[i * 3 + 2] & 0x3f;

            sb.append(CharSequence.indexOf((b1 + StartPos) % len));
            sb.append(CharSequence.indexOf((b2 + StartPos) % len));
            sb.append(CharSequence.indexOf((b3 + StartPos) % len));
            sb.append(CharSequence.indexOf((b4 + StartPos) % len));
        }
    }

    public byte[] padding(byte[] bytes) {
        int len = bytes.length;

        byte[] ret = new byte[(len + 2) / 3 * 3];

        for (int i = 0; i < len; i++) {
            ret[i] = bytes[i];
        }

        return ret;
    }
}
