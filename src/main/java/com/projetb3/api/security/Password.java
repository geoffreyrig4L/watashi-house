package com.projetb3.api.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Password {


    public Password() {
    }

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, 16, 128);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] hash = hash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (hash.length != expectedHash.length) return false;
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] != expectedHash[i]) return false;
        }
        return true;
    }
}
