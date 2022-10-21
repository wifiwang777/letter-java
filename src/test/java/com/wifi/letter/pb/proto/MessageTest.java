package com.wifi.letter.pb.proto;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessageTest {
    @Test
    public void testDecode() {
        WsMessage.Message.Builder builder = WsMessage.Message.newBuilder();
        builder.setFrom(1);
        builder.setTo(2);
        builder.setType(1);
        builder.setContent("hello");
        WsMessage.Message message = builder.build();
        System.out.println(message);
    }


}
