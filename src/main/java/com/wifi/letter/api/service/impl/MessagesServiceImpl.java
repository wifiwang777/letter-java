package com.wifi.letter.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wifi.letter.api.service.MessagesService;
import com.wifi.letter.api.mapper.MessagesMapper;
import com.wifi.letter.api.model.Messages;
import org.springframework.stereotype.Service;

@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements MessagesService {
}
