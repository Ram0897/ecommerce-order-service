package com.ram.ecommerce.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final byte[] secret;
    public JwtService(@Value("${app.security.jwt-secret}") String secret){this.secret=secret.getBytes(StandardCharsets.UTF_8);}
    public String generate(String username){
        Date now=new Date();
        return Jwts.builder().subject(username).issuedAt(now).expiration(new Date(now.getTime()+3600000)).signWith(Keys.hmacShaKeyFor(secret)).compact();
    }
    public String username(String token){return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret)).build().parseSignedClaims(token).getPayload().getSubject();}
}
