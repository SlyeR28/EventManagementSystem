package org.rishabh.eventmanagementsystemadvanced.Security.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;


    public JwtUtils(String secretKey) {
        this.secretKey = secretKey;

    }

    public String generateToken(String username , List<String> roles){
        return Jwts.builder()
                .setSubject(username)
                .claim("roles" , roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*2))
                .signWith(getSignedKey() , SignatureAlgorithm.HS256)
                .compact();

    }
    private Key getSignedKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Boolean validToken(String token , String username){
        return (extractUsername(token).equals(username) &&  !isTokenExpired(token));
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public  Date extractExpiration(String token){
        return extractClaim(token , Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public List<String> extractRoles(String token){
        return extractClaim(token , claims -> claims.get("roles" , List.class));
    }



    public <T> T extractClaim(String token , Function<Claims , T> claimsResolvers){
        final Claims  claims = Jwts.parser()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolvers.apply(claims);

    }
}
