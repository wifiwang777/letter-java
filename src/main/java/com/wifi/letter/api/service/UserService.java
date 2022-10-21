package com.wifi.letter.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wifi.letter.api.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends IService<User> {
}
