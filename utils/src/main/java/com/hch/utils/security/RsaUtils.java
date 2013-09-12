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
    private static final String PrivateKeyB64Str = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDirMElaA4Exz8eaoJuRefFb8Gir8ovo70k1v9V84ASfxf1VudaOI87BLCEpojR/OeaCuPYcP7aBR3x3n3kTKe40vboqzHJo8t/yu9tyTf57D7iHtlaVaQqoq3Nlw4CxtXdg9w4iq/4t/OB+6wFkxW+G4mfAirT3YJSDLs2i6EZd256LUE4EasKkrsEmMCZWBczQSvD5ZbjsyHflHb3eGO6sLLGb23/GGYW3EMAPJ/45u5FwJFZGwAfEeM5REPL0v5oxSnAIPhSdG01sVcBL2y3JboqP/g6rS4ZsydtV4RtRBFvzEzw93cKkiBirsesd0Ypf7WQhUhKdqGAtHgrB5ONAgMBAAECggEAWYBm3zM/3ckidgsOJyZ5Bm9DIM6SYc8gPuyZc0GrPyJpvlirDueoPmBypl3vRiK6/AryviKXrlQ456i3Qq61xvedwG7gQMLo9jOV6F58OLV8euI9ZjrMkH0dbUEIqjYCee23xaeVJfrULm3GFYlnG2JaK6u6Z19eNrGKNlZZrOjKKmvIUKArtnoCYATo2FssuehtlYxAckq6O4MsUkW0v8UscJ7CBQciu2GPFNQtt1O1cGxn8GSdGQcNSOQFciVoIWouzGrHxN+UI1T4M02pVjARPA9/AI8iGhwJ0fyUDwhCSV2fBUWHfj7ZWlYXtLyBeNFRMD9NPryUSfeHq6yTTQKBgQD3yzEjfuQI+wbGQnHq/n883AolF8fzEE8henDiyG0HO7k3cLNFaWp/uqr+JGTMMJzGZ1FSyWjY+SnYHy3B2gK6VOgnkQQ72mdCpKLgkkwPkDDoM3JjqWrFBCOUu7U90gIBe8VMTXxVshTB2UG7uTeJJDuO59V3VZ5q6U8YF1x28wKBgQDqLoPy8TJ/PbM5AnaGA29pt+1MhE7jGwigAXGTmH4889fF1LWIqERBXsWKPn44rPYMU1ncs8KZnPTuSMeWkdpz84zBOJButrhI3BfvT/5qLsSMWyFS1oxfolEAMuGjxbShJV1Lf+kNnxAL1HMhY9GeKUdWI7h6Q5qotgvRZgtrfwKBgB7hFGuO+Y3g8nQsfZPCSUrlROJgd0tkAehzILQyM9wN5kPxaHXC8EGgmoR3NpR3jmHvKCQ/8y/uxPco04Pvzh04TYH4wWVQD0QvzhhcQtxBpMaa+qtOxn5syJ5VUf21OQjESs76bP8k5Q9PKrDhIPmXKVDitShc5BQApRmkd6JfAoGBANoONg8hSEPyz89l27ctu7BU80EscchGg+F5dqhYdjOLWn97cQ2uoN+9v3Lafe2IIBOaCTLYr3saC3dVjYNGjIMrZQUGBncQ7MfrKN5NPjjhE2Md+AmKfWsPWR02Q6Sbk3X5tMV4dSmZ0vcw+4M7zxPWm/Y5jK1MBdbSrG5u9nNBAoGADsomU/2vCdqhrtYkMoTn8rSNvCMGuv7Kj1eH7uB1bniPjV1u/D81I7MQMTje0GxfNzaJZaBtOqDTVrwmrMD7Dg7hRjYSsa027fZaVCJumw8hXnJPpL9nHWuCvhm6isIRS/DcRVNO2f1ir2X6z3K+ftzoMEvxZGFcVshRVoTXmRY=";
    private static final String PublicKeyB64Str = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4qzBJWgOBMc/HmqCbkXnxW/Boq/KL6O9JNb/VfOAEn8X9VbnWjiPOwSwhKaI0fznmgrj2HD+2gUd8d595EynuNL26KsxyaPLf8rvbck3+ew+4h7ZWlWkKqKtzZcOAsbV3YPcOIqv+LfzgfusBZMVvhuJnwIq092CUgy7NouhGXduei1BOBGrCpK7BJjAmVgXM0Erw+WW47Mh35R293hjurCyxm9t/xhmFtxDADyf+ObuRcCRWRsAHxHjOURDy9L+aMUpwCD4UnRtNbFXAS9styW6Kj/4Oq0uGbMnbVeEbUQRb8xM8Pd3CpIgYq7HrHdGKX+1kIVISnahgLR4KweTjQIDAQAB";
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

    public static String encryptUsingPrivateKey(String rawString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        PrivateKey privateKey = base64String2PrivateKey(PrivateKeyB64Str);
        return encryptUsingPrivateKey(privateKey, rawString);
    }

    public static String encryptUsingPrivateKey(PrivateKey privateKey, String rawString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] enbytes = cipher.doFinal(rawString.getBytes());
        return Base64.encodeBase64String(enbytes);
    }


    public static String decryptUsingPrivateKey(String b64EncodedString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        PrivateKey privateKey = base64String2PrivateKey(PrivateKeyB64Str);
        return decryptUsingPrivateKey(privateKey, b64EncodedString);
    }

    public static String decryptUsingPrivateKey(PrivateKey privateKey, String b64EncodedString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        byte[] enbytes = Base64.decodeBase64(b64EncodedString);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] debytes = cipher.doFinal(enbytes);
        return new String(debytes);
    }

    public static String encryptUsingPublicKey(String rawString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        PublicKey publicKey = base64String2PublicKey(PublicKeyB64Str);
        return encryptUsingPublicKey(publicKey, rawString);
    }

    public static String encryptUsingPublicKey(PublicKey publicKey, String rawString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] enbytes = cipher.doFinal(rawString.getBytes());
        return Base64.encodeBase64String(enbytes);
    }


    public static String decryptUsingPublicKey(String b64EncodedString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        PublicKey publicKey = base64String2PublicKey(PublicKeyB64Str);
        return decryptUsingPublicKey(publicKey, b64EncodedString);
    }

    public static String decryptUsingPublicKey(PublicKey publicKey, String b64EncodedString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        byte[] enbytes = Base64.decodeBase64(b64EncodedString);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] debytes = cipher.doFinal(enbytes);
        return new String(debytes);
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
