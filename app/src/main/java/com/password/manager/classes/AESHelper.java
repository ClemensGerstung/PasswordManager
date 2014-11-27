package com.password.manager.classes;

import android.util.Base64;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Clemens on 15.09.2014.
 */

/// One of the old classes!
/// But still worth using it!
public class AESHelper {
    public static String decrypt(String data) throws Exception {
        byte[] decodedFrom64 = Base64.decode(data, Base64.DEFAULT);

        byte[] key = (User.password).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("MD5");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 32);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] cipherData = cipher.doFinal(decodedFrom64);

        return new String(cipherData);
    }

    public static String encrypt(String text, String key_string) throws Exception {
        String encrypted_string = "";

        byte[] key = (key_string).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("MD5");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 32);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(text.getBytes());

        encrypted_string = Base64.encodeToString(encrypted, Base64.DEFAULT);

        return encrypted_string;
    }
}
