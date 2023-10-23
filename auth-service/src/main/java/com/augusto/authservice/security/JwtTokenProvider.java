package com.augusto.authservice.security;

import java.security.Key;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.augusto.authservice.exceptions.UserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider{
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpiration;

    public String generateToken(Authentication authentication, Long userId, List<String> userRole){
        var username = authentication.getName();
        var currentDate = new Date();
        var expireDate =  new Date(currentDate.getTime()+ jwtExpiration);

        var token = Jwts.builder()
                        .setSubject(username)
                        .claim("userId", userId)
                        .claim("roles", userRole)
                        .setIssuedAt(new Date())
                        .setExpiration(expireDate)
                        .signWith(key())
                        .compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(jwtSecret)
        );
    }

    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new UserException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new UserException(HttpStatus.BAD_REQUEST, "expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new UserException(HttpStatus.BAD_REQUEST, "unsuported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new UserException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }

    }
}