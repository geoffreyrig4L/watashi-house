package com.projetb3.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.projetb3.api.model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AuthenticationWithJWT {

    private final static Algorithm ALGORITHM = Algorithm.HMAC256("zabuifbauickbdc");

    public static String create(User user) {
        try{
            return JWT.create()
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

    //pour authentifier si l'user est connecté avant d'effectuer une opération
    public static DecodedJWT verifier(String token){
        try{
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .acceptLeeway(36000)
                    .acceptExpiresAt(36000)
                    .build();
            verifier.verify(token);
            return JWT.decode(token);
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Le token est invalide", exception);
        }
    }
}
