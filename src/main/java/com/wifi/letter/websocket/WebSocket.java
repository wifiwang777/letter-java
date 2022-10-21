package com.wifi.letter.websocket;


import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public final class WebSocket {
    public static ConcurrentHashMap<Integer, ArrayList<WsConn>> sessions = new ConcurrentHashMap<>();

}
