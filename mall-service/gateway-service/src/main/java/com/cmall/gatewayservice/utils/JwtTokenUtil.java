package com.cmall.gatewayservice.utils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtTokenUtil {
    private final String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public JwtTokenUtil() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 生成符合要求的密钥
        this.jwtSecret = Base64.getEncoder().encodeToString(key.getEncoded()); // 将密钥编码为Base64字符串
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
