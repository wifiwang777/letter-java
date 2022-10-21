package com.wifi.letter.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wifi.letter.api.mapper.UserMapper;
import com.wifi.letter.api.model.User;
import com.wifi.letter.api.service.UserService;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
