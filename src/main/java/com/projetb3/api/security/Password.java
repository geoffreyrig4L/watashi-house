package com.projetb3.api.security;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class Password {


    public Password() {
    }

    public static byte[] hashPassword(final char[] password, final byte[] salt) {
        try {
            var secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, 1000, 512);
            SecretKey key = secretKeyFactory.generateSecret(spec);
            return key.getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
    }

    public static boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] hash = hashPassword(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (hash.length != expectedHash.length) return false;
        for (int i = 0; i < hash.length; i++) {
            if (hash[i] != expectedHash[i]) return false;
        }
        return true;
    }
}
