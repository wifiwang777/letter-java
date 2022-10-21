package com.wifi.letter.api.jwt;

import com.wifi.letter.api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static String createJwt(User user, String key) {
        Map<String, Object> userMap = new HashMap<>();
        Claims claims = new DefaultClaims(userMap);
        claims.put("uid", user.getUid());
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, key).compact();
    }

    public static Integer parseJwt(String jwt, String key) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();
        return claims.get("uid", Integer.class);
    }
}
