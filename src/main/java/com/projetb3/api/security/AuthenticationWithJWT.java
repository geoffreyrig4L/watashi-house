package com.projetb3.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.projetb3.api.model.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class AuthenticationWithJWT {

    private final static Algorithm ALGORITHM = Algorithm.HMAC256("oknvoibjemrolvmdspmckvnsmvjbocmznctczxbbdc");

    public static String create(User user) {
        try{
            return JWT.create()
                    .withIssuer("auth0")
                    .withClaim("id", user.getId())
                    .withClaim("firstname", user.getFirstname())
                    .withClaim("lastname", user.getLastname())
                    .withClaim("email", user.getEmail())
                    .withClaim("typeUser", user.getTypeUser())
                    .withExpiresAt(Date.from(Instant.now().plus(3600, ChronoUnit.SECONDS)))
                    .sign(ALGORITHM);
        } catch(JWTCreationException exception){
            throw new JWTCreationException("Erreur lors de la création du token", exception);
        }
    }

    public static boolean verifier(String token, Optional<String> selector){

        try{
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .withIssuer("auth0")
                    .acceptLeeway(1)
                    .acceptExpiresAt(3600)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("typeUser").toString().equals("\"administrateur\"") ||
                    jwt.getClaim("firstname").toString().equals("\""+selector.orElse("")+"\"");
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Le token est invalide {}", exception);
        }
    }
}
