package com.wifi.letter.api.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.DigestUtils;

public class Util {
    @NotNull
    public static <T> ResponseMsg<T> SuccessMsg(T data) {
        ResponseMsg<T> msg = new ResponseMsg<>();
        msg.setCode(0);
        msg.setMsg("Success");
        msg.setData(data);
        return msg;
    }

    @NotNull
    public static ResponseMsg<String> ErrMsg(String message) {
        ResponseMsg<String> msg = new ResponseMsg<>();
        msg.setCode(-1);
        msg.setMsg(message);
        return msg;
    }

    public static String PasswordEncrypt(String original) {
        return DigestUtils.md5DigestAsHex(original.getBytes());
    }

}
