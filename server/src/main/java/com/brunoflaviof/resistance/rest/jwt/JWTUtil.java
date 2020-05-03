package com.brunoflaviof.resistance.rest.jwt;

import com.brunoflaviof.resistance.rest.repository.data.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JWTUtil implements Serializable {
    private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 5;
    @Value("${security.jwt.secret}")
    private String secret;

    public  String generateToken(User user){
        Map<String, Object>tokenData = new HashMap<>();
        tokenData.put("userID", user.getUserID());
        tokenData.put("name", user.getName());
        String token = Jwts.builder().setClaims(tokenData)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    public  UUID getUserIDFromToken(String token){
        Claims tokenData = getClaims(token);
        return UUID.fromString((String) tokenData.get("userID"));
    }

    public  boolean validateToken(String token, User user){
        Claims tokenData = getClaims(token);
        if(isExpired(tokenData))
            return false;
        return user.getUserID().equals(tokenData.get("userID"));
    }

    private  Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private  boolean isExpired(Claims tokenData) {
        return tokenData.getExpiration().before(new Date());
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
