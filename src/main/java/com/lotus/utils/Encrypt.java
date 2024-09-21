package com.lotus.utils;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Encrypt {
    private static final String SALT = "shit";

    public static String encode(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update((str + SALT).getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return byteToHexString(digest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String byteToHexString(byte[] bytes) {
        return String.valueOf(Hex.encodeHex(bytes));
    }
}
