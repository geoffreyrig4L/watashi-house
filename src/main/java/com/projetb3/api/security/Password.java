package com.projetb3.api.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class Password {

    private byte[] salt;
    private byte[] hash;

    public Password() {
    }

    public static Password init() {
        return new Password();
    }

    public Password hash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            this.hash = factory.generateSecret(spec).getEncoded(); //hashCode
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException();
        } catch (InvalidKeySpecException e) {
            throw new InvalidKeySpecException();
        }
        return this;
    }

    //création du salt
    public Password salt() {
        var random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        this.salt = salt;
        return this;
    }

    public String getHashedPassword() {
        return Arrays.toString(this.hash);
    }
}
