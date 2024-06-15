package com.travelbnb.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.travelbnb.entity.AppUserEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmkey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;

    private Algorithm algorithm;

//    private final String USER_NAME = "username";

    @PostConstruct
    public void postConstruct(){
        algorithm=Algorithm.HMAC256(algorithmkey);
    }

//    Generating Token
    public String generateToken(AppUserEntity appUser)
    {
        return JWT.create().
                withClaim("username",appUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

//    public String getUserName(String token)
//    {
//        DecodedJWT decodedJwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
//
//        return decodedJwt.getClaim(USER_NAME).asString();
//    }
}
