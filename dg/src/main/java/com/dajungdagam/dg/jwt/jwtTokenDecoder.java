package com.dajungdagam.dg.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.security.SignatureException;

@Slf4j
public class jwtTokenDecoder {

    private String jwtToken;

    @Value("${jwt.secretKey}")
    private static String secretKey;

    public static Jws<Claims> getClaims(String jwt){
        try{
            return
                    Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
        } catch(Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
