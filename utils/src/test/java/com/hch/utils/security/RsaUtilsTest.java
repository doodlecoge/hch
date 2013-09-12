package com.hch.utils.security;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * User: huaiwang
 * Date: 13-9-12
 * Time: 上午9:11
 */
public class RsaUtilsTest {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        String rawString = "hch";

        String encodedString = RsaUtils.encryptUsingPublicKey(rawString);
        System.out.println(encodedString);


        String decodedString = RsaUtils.decryptUsingPrivateKey(encodedString);
        System.out.println(decodedString);


        System.out.println("--------------------------");

        encodedString = RsaUtils.encryptUsingPrivateKey(rawString);
        System.out.println(encodedString);

        decodedString = RsaUtils.decryptUsingPublicKey(encodedString);
        System.out.println(decodedString);

    }
}
