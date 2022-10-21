package com.wifi.letter.api.jwt;

import org.bitcoinj.core.ECKey;

import java.security.Key;

public class EcdsaKey implements Key {
    public ECKey ecKey;

    public EcdsaKey(ECKey ecKey) {
        this.ecKey = ecKey;
    }

    @Override
    public String getAlgorithm() {
        return "secp256k1";
    }

    @Override
    public String getFormat() {
        return "PKCS#8";
    }

    @Override
    public byte[] getEncoded() {
        return ecKey.getPrivKeyBytes();
    }
}
