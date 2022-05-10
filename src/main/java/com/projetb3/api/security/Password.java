package com.projetb3.api.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    private String hash;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final Pattern layout = Pattern.compile("\\$31\\$16\\$(.{43})");

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
        var encoder = Base64.getUrlEncoder().withoutPadding();
        this.hash = "$31$" + 16 + '$' + encoder.encodeToString(hash);
        return this;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt) {
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

    public static boolean checkPassword(char[] password) {
        Matcher matcher = verifyToken(token);
        byte[] hash = Base64.getUrlDecoder().decode(matcher.group(2));
        //retourne un tableau tronqué ou rempli de 0 pour atteindre la longueur require
        byte[] salt = Arrays.copyOfRange(hash, 0, 16 / 8);
        byte[] check = pbkdf2(password, salt);
        int zero = 0;
        for (int index = 0; index < check.length; ++index) {
            //Retourne 1 si l'un ou l'autre des deux bits de même poids est à 1 (ou les deux)
            zero |= hash[salt.length + index] ^ check[index];
        }
        return zero == 0;
    }

    private static Matcher verifyToken(String token) {
        Matcher matcher = layout.matcher(token);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid token format");
        }
        return matcher;
    }

}
