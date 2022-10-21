package com.wifi.letter.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wifi.letter.api.mapper.UserFriendMapper;
import com.wifi.letter.api.model.UserFriend;
import com.wifi.letter.api.service.UserFriendService;
import org.springframework.stereotype.Service;

@Service
public class UserFriendServiceImpl extends ServiceImpl<UserFriendMapper, UserFriend> implements UserFriendService {
}
