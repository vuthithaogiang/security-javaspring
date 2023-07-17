package com.giang.security.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private static final String SECRET_KEY = "7kE4P38iNlebE4Hu53LcHido+p6b2wjcRGVkQ1LF3QowlgX6rToVYzKabyyJHL5+";
    private static final long  SECRET_EXPIRATION =  1000*60*60*24*2; // 2 days


    @Value( value = SECRET_KEY)
    private String secretKey;

    private long jwtExpiration = SECRET_EXPIRATION;
    private long refreshExpiration = SECRET_EXPIRATION;

    public String extractUsername(String token){
       return extractClaim(token, Claims::getSubject);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){

        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object > extraClaims,
            UserDetails userDetails
    ){
        return builderToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return builderToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String builderToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration){
        System.out.println(new Date(System.currentTimeMillis()));
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuedAt(new Date())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
       return extractExpiration(token).before( new Date());
    }

    private Date extractExpiration(String token){

        return (Date) extractClaim(token, Claims::getExpiration);
    }

    private Claims extractClaims(String token){
         return Jwts
                 .parserBuilder()
                 .setSigningKey(getSignInKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
