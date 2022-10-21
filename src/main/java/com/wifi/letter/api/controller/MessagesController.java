package com.wifi.letter.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.wifi.letter.api.jwt.JwtConfig;
import com.wifi.letter.api.model.Messages;
import com.wifi.letter.api.service.MessagesService;
import com.wifi.letter.api.util.ResponseMsg;
import com.wifi.letter.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {
    @Autowired
    private MessagesService messagesService;

    @Autowired
    private Gson gson;

    @Autowired
    private JwtConfig jwtConfig;

    @GetMapping("messages")
    public String getMessages(@RequestHeader Map<String, String> header, Integer uid) {
        Integer fromUserId = jwtConfig.getUidFromHeader(header);
        QueryWrapper<Messages> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("to_user_id", fromUserId, uid);
        List<Messages> messagesList = messagesService.list(queryWrapper);
        ResponseMsg<List<Messages>> msg = Util.SuccessMsg(messagesList);
        return gson.toJson(msg);
    }
}
