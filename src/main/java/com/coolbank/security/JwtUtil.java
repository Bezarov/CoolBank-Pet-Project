package com.coolbank.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String USER_SECRET_KEY = "WakandaBank";
    private final String COMPONENT_SECRET_KEY = "BankWakanda";

    private final String TOKEN_TYPE_CLAIM = "tokenType";
    private final String USER_TOKEN_TYPE = "user";
    private final String COMPONENT_TOKEN_TYPE = "component";

    public String userTokenGenerator(String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE_CLAIM, USER_TOKEN_TYPE);
        return doGenerateToken(claims, userEmail, USER_SECRET_KEY);
    }

    public String componentTokenGenerator(String serviceId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE_CLAIM, COMPONENT_TOKEN_TYPE);
        return doGenerateToken(claims, serviceId, COMPONENT_SECRET_KEY);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, String secretKey) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 10 hours
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getIdentityFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        String key = determineKeyForToken(token);
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    private String determineKeyForToken(String token) {
        String tokenType = Jwts.parser()
                .setSigningKey(USER_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get(TOKEN_TYPE_CLAIM, String.class);

        if (COMPONENT_TOKEN_TYPE.equals(tokenType)) {
            return COMPONENT_SECRET_KEY;
        } else {
            return USER_SECRET_KEY;
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateUserToken(String token) {
        return (isUserToken(token) && !isTokenExpired(token));
    }

    public Boolean validateComponentToken(String token) {
        return (isComponentToken(token) && !isTokenExpired(token));
    }

    public Boolean isUserToken(String token) {
        return USER_TOKEN_TYPE.equals(Jwts.parser().setSigningKey(
                USER_SECRET_KEY).parseClaimsJws(token).getBody().get(TOKEN_TYPE_CLAIM, String.class));
    }

    public Boolean isComponentToken(String token) {
        return COMPONENT_TOKEN_TYPE.equals(Jwts.parser().setSigningKey(
                COMPONENT_SECRET_KEY).parseClaimsJws(token).getBody().get(TOKEN_TYPE_CLAIM, String.class));
    }
}