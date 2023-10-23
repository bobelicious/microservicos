package com.augusto.product.security;

import java.security.Key;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.augusto.product.exceptions.ProductException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpiration;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromJwt(String token) {
        Claims claims =
                Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Long getUserIdFromJwt(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
           token = token.substring(7, token.length());
        }
        Claims claims =
                Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return claims.get("userId", Long.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> getUserRolesFromJwt(String token) {
        Claims claims =
                Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        return claims.get("roles", List.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new ProductException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new ProductException(HttpStatus.BAD_REQUEST, "expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new ProductException(HttpStatus.BAD_REQUEST, "unsuported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new ProductException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }

    }
}
