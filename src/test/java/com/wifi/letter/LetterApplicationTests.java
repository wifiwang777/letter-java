package com.wifi.letter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LetterApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void jwtTest() {
        String res = Jwts.builder().claim("uid", 1).signWith(SignatureAlgorithm.HS256, "letter").compact();
        System.out.println(res);
    }
}
