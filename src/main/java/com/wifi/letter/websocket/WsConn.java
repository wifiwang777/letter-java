package com.wifi.letter.websocket;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wifi.letter.api.mapper.MessagesMapper;
import com.wifi.letter.api.model.Messages;
import com.wifi.letter.api.service.MessagesService;
import com.wifi.letter.api.service.impl.MessagesServiceImpl;
import com.wifi.letter.pb.proto.WsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@ServerEndpoint("/letter/ws/{uid}")
public class WsConn {
    private Integer uid;
    private Session session;

    private static MessagesService messagesService;

    @Autowired
    public void setMessagesService(MessagesService messagesService) {
        WsConn.messagesService = messagesService;
    }

    @OnOpen
    public void onOpen(@PathParam(value = "uid") Integer uid, Session session) {
        if (uid == null) {
            return;
        }
        this.uid = uid;
        this.session = session;
        ArrayList<WsConn> sessions;
        if (!WebSocket.sessions.containsKey(uid)) {
            sessions = new ArrayList<>();
        } else {
            sessions = WebSocket.sessions.get(uid);
        }
        sessions.add(this);
        WebSocket.sessions.put(uid, sessions);
        System.out.println("uid:" + this.uid + " conn:" + this);
        System.out.println("uid:" + this.uid + " current conn:" + sessions);
    }

    @OnMessage
    public void onMessage(byte[] data) throws InvalidProtocolBufferException {
        WsMessage.Message message = WsMessage.Message.parseFrom(data);
        Integer toUid = message.getTo();
        Messages messages = new Messages();
        messages.setFromUserId(message.getFrom());
        messages.setToUserId(message.getTo());
        messages.setContent(message.getContent());
        boolean res = messagesService.save(messages);
        if (!res) {
            System.out.println("message insert db error");
        }

        ArrayList<WsConn> wsConns = WebSocket.sessions.get(toUid);
        if (wsConns != null && wsConns.size() > 0) {
            wsConns.forEach((wsConn) -> {
                final RemoteEndpoint.Basic basic = wsConn.session.getBasicRemote();
                if (basic == null) {
                    return;
                }
                try {
                    basic.sendBinary(ByteBuffer.wrap(data));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
    }

    @OnClose
    public void onClose() {
        ArrayList<WsConn> wsConns = WebSocket.sessions.get(this.uid);
        if (wsConns.size() == 0) {
            return;
        }
        ArrayList<WsConn> newConns = new ArrayList<>();
        wsConns.forEach((conn) -> {
            if (conn != this) {
                newConns.add(conn);
            } else {
                System.out.println("uid:" + this.uid + " remove closed conn:" + conn);
            }
        });
        WebSocket.sessions.put(this.uid, newConns);
        System.out.println("uid:" + this.uid + " current conn:" + newConns);
    }
}