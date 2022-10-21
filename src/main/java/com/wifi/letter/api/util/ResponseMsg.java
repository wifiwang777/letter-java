package com.wifi.letter.api.util;

import lombok.Data;

@Data
public class ResponseMsg<T> {
    private Integer code;
    private String msg;
    private T data;
}
