package com.hch.utils.security;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created with IntelliJ IDEA.
 * User: huaiwang
 * Date: 13-8-27
 * Time: 上午9:41
 * To change this template use File | Settings | File Templates.
 */
public class RsaUtils {
    private static final Logger log = LoggerFactory.getLogger(RsaUtils.class);
    private static final String alg = "RSA";

    public static String privateKey2Base64String(PrivateKey privateKey) {
        byte[] bytes = privateKey.getEncoded();
        return Base64.encodeBase64String(bytes);
    }

    public static PrivateKey base64String2PrivateKey(String b64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = Base64.decodeBase64(b64);

        PrivateKey privateKey = KeyFactory.getInstance(alg).generatePrivate(
                new PKCS8EncodedKeySpec(bytes)
        );

        return privateKey;
    }

    public static String publicKey2Base64String(PublicKey publicKey) {
        byte[] bytes = publicKey.getEncoded();
        return Base64.encodeBase64String(bytes);
    }

    public static PublicKey base64String2PublicKey(String b64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = Base64.decodeBase64(b64);

        PublicKey publicKey = KeyFactory.getInstance(alg).generatePublic(
                new X509EncodedKeySpec(bytes)
        );

        return publicKey;
    }


    public static String decrypt(PrivateKey privateKey, String b64EncodedString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        byte[] enbytes = Base64.decodeBase64(b64EncodedString);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] debytes = cipher.doFinal(enbytes);
        return new String(debytes);
    }

    public static String encrypt(PublicKey publicKey, String rawString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] enbytes = cipher.doFinal(rawString.getBytes());
        return Base64.encodeBase64String(enbytes);
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return generateKeyPair(1024);
    }

    public static KeyPair generateKeyPair(int length) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(alg);
        keyGen.initialize(length);
        KeyPair keyPair = keyGen.generateKeyPair();
        return keyPair;
    }
}
