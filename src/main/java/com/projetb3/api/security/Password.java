package com.projetb3.api.security;

import com.auth0.jwt.algorithms.Algorithm;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Password {

    private String hash;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    public Password() {
    }

    public static Password init() {
        return new Password();
    }

    public Password hash(char[] password) {
        var random = new SecureRandom();
        byte[] salt = new byte[128 / 8];
        random.nextBytes(salt);
        byte[] dk = pbkdf2(password, salt);
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        this.hash = "$31$" + 16 + '$' + enc.encodeToString(hash);
        return this;
    }

    private byte[] pbkdf2(char[] password, byte[] salt) {
        KeySpec spec = new PBEKeySpec(password, salt, 16, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            return factory.generateSecret(spec).getEncoded(); //hashCode
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException("Invalid SecretKeyFactory", e);
        }
    }

    public String getHashedPassword() {
        return hash;
    }

}
