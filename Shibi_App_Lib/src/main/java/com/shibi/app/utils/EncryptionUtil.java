package com.shibi.app.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by User on 13/06/2018.
 */
public class EncryptionUtil {

    private final static String SHA_512 = "SHA-512";
    private final static String SHA_256 = "SHA-256";

    private static Logger LOG = LoggerFactory.getLogger(EncryptionUtil.class);

    public static String generateString(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static synchronized String doSHA512Encryption(String password, String salt) throws  RuntimeException {
        assert  null != password && !password.isEmpty() : "password cannot be null or empty";
        assert  null != salt && !salt.isEmpty() : "salt cannot be null or empty";

        try {
            return doEncryption(SHA_512, password, salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(SHA_512 + " algorithm not found",e);
        }

    }

    private  static String doEncryption(String algorithm, String password) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance(algorithm);
        return digestAndGetPassword(digest, password);

    }

    private  static String doEncryption(String algorithm, String password, String salt) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.update(salt.getBytes(StandardCharsets.UTF_8));
        return digestAndGetPassword(digest, password);


    }

    private static String digestAndGetPassword(MessageDigest digest, String password) {

        byte[] pass = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder builder = new StringBuilder();
        for( int i=0; i<pass.length;i++) {
            builder.append(Integer.toString((pass[i] & 0xff) + 0x100, 16).substring(1));
        }

        return builder.toString();

    }


}
