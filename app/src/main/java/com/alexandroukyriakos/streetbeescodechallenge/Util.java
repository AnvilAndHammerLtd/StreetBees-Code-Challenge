package com.alexandroukyriakos.streetbeescodechallenge;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Useful generic re-usable methods
 */
public class Util {
    /**
     * create an md5 hash based on the given string
     *
     * @param s the string to create a hash from
     * @return the hash
     */
    public static final String md5(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")), 0, s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * gets the current timestamp
     *
     * @return the current timestamp
     */
    // http://stackoverflow.com/a/25264666/2810172
    public static final String getCurrentTimestamp() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        return ts;
    }
}