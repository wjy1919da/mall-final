package com.cmall.gatewayservice.utils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class JwtTokenUtil {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public JwtTokenUtil() {}

    /**
     * get username from the token
     * @param token
     * @return
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * validate JWT token
     * @param token
     * @return
     */
    public Mono<Boolean> validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return Mono.just(true);
        } catch (SignatureException ex) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JWT signature", ex));
        } catch (MalformedJwtException ex) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JWT token", ex));
        } catch (ExpiredJwtException ex) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expired JWT token", ex));
        } catch (UnsupportedJwtException ex) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported JWT token", ex));
        } catch (IllegalArgumentException ex) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.", ex));
        }
    }
}
