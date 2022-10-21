package com.wifi.letter.api.jwt;

import cn.hutool.core.util.HexUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.EllipticCurveProvider;
import org.bitcoinj.core.ECKey;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@SpringBootTest
public class JwtTest {

    @Test
    public void createKeyPair() {
        System.out.println();
        KeyPair keyPair = EllipticCurveProvider.generateKeyPair(SignatureAlgorithm.ES256);
        System.out.println(HexUtil.encodeHexStr(keyPair.getPrivate().getEncoded()));
        System.out.println(HexUtil.encodeHexStr(keyPair.getPublic().getEncoded()));
        System.out.println(keyPair.getPrivate().getEncoded().length);
        System.out.println(keyPair.getPublic().getEncoded().length);
        System.out.println(keyPair.getPrivate().getClass());
        System.out.println(keyPair.getPrivate().getAlgorithm());
        System.out.println(keyPair.getPrivate().getFormat());
    }

    @Test
    public void decodeKeyPair() throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("EC");

        byte[] privKey = HexUtil.decodeHex("308193020100301306072a8648ce3d020106082a8648ce3d03010704793077020101042082e6c49802ec8875033f87fb4f834fd945ffa55f22d787d84a92d4ec10f7de2fa00a06082a8648ce3d030107a14403420004601047f1fddb77e3204b2a01cb1757b17a6a91e4a4c3a44ec44d893dfffc6183d782bc6595d4c99c58c60bff57c22a9ef000543f0c5b0d5b6002fe9d7a67dbf1");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privKey);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Jwts.builder().setId("1").signWith(SignatureAlgorithm.ES256, privateKey).compact();


        byte[] pubKey = HexUtil.decodeHex("3059301306072a8648ce3d020106082a8648ce3d03010703420004e09522bdc7f8c7781366d45d0dfcd7f0e5409e63b69977549ac06144058cb779fe5893218934450dc58a97ef3419332e51d2988014e83a48c40294119d335591");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        System.out.println(HexUtil.encodeHexStr(publicKey.getEncoded()));
    }
}
