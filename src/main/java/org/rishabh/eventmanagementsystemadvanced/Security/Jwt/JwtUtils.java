package org.rishabh.eventmanagementsystemadvanced.Security.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtils {

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.secretKey}")
    private String jwtSecret;

      private Key getSecretKey() {
         byte[] bytes = Decoders.BASE64.decode(jwtSecret);
         return Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(Map<String, Object> claims , UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSecretKey() , SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, String email) {
        return (extractEmail(token).equals(email) && !isTokenExpired(token));
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
          Date expiration = extractExpiration(token);
          return expiration.before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
          final Claims claims = Jwts.parser()
                  .setSigningKey(getSecretKey())
                  .build()
                  .parseClaimsJws(token)
                  .getBody();
          return claimsResolver.apply(claims);
    }
}
