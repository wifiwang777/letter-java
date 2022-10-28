package com.wifi.letter.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.google.gson.Gson;
import com.wifi.letter.api.jwt.JwtConfig;
import com.wifi.letter.api.jwt.JwtUtil;
import com.wifi.letter.api.mapper.UserMapper;
import com.wifi.letter.api.model.User;
import com.wifi.letter.api.model.UserFriend;
import com.wifi.letter.api.service.UserFriendService;
import com.wifi.letter.api.service.UserService;
import com.wifi.letter.api.util.ResponseMsg;
import com.wifi.letter.api.util.Util;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@EnableConfigurationProperties(JwtConfig.class)
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private Gson gson;

    @PostMapping("login")
    public String login(@NotNull @RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", user.getName());
        queryWrapper.eq("password", Util.PasswordEncrypt(user.getPassword()));
        user = userService.getOne(queryWrapper);
        ResponseMsg<String> msg;
        if (user == null) {
            msg = Util.ErrMsg("unknown user");
        } else {
            String jwt = JwtUtil.createJwt(user, jwtConfig.getKey());
            msg = Util.SuccessMsg(jwt);
        }
        return gson.toJson(msg);
    }

    @PostMapping("register")
    public String register(@NotNull @RequestBody User user) {
        User userServiceOne = userService.getOne(new QueryWrapper<User>().eq("name", user.getName()));
        if (userServiceOne != null) {
            return gson.toJson(Util.ErrMsg("用户已注册"));
        }

        user.setPassword(Util.PasswordEncrypt(user.getPassword()));
        boolean res = userService.save(user);
        if (res) {
            return gson.toJson(Util.SuccessMsg("register Success"));
        } else {
            return gson.toJson(Util.ErrMsg("register error"));
        }
    }

    @GetMapping("/user")
    public String getAllUser() {
        List<User> userList = userService.list();
        return gson.toJson(userList);
    }

    @GetMapping("/user/{uid}")
    public String getUserByUid(@PathVariable Integer uid) {
        User user = userService.getById(uid);
        ResponseMsg<User> msg = Util.SuccessMsg(user);
        return gson.toJson(msg);
    }

    @GetMapping("/user/info")
    public String getUserByUid(@RequestHeader Map<String, String> header) {
        Integer uid = jwtConfig.getUidFromHeader(header);
        User user = userService.getById(uid);
        ResponseMsg<User> msg = Util.SuccessMsg(user);
        return gson.toJson(msg);
    }

    @GetMapping("login/check")
    public String checkLogin(@RequestHeader Map<String, String> header) {
        Integer uid = jwtConfig.getUidFromHeader(header);
        ResponseMsg<Integer> msg = Util.SuccessMsg(uid);
        return gson.toJson(msg);
    }

    @GetMapping("user/getFriends")
    public String getFriends(@RequestHeader Map<String, String> header) {
        Integer uid = jwtConfig.getUidFromHeader(header);
        UserMapper userMapper = (UserMapper) userService.getBaseMapper();
        List<User> friendList = userMapper.getFriends(uid);
        ResponseMsg<List<User>> msg = Util.SuccessMsg(friendList);
        return gson.toJson(msg);
    }

    @GetMapping("user/searchUser")
    public String searchUser(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<User> userList = userService.list(queryWrapper);
        ResponseMsg<List<User>> msg = Util.SuccessMsg(userList);
        return gson.toJson(msg);
    }

    @PostMapping("user/deleteFriend")
    public String deleteFriend(@RequestHeader Map<String, String> header, @NotNull @RequestBody User friend) {
        Integer uid = jwtConfig.getUidFromHeader(header);
        QueryWrapper<UserFriend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", uid);
        queryWrapper.eq("friend_id", friend.getUid());
        boolean res = userFriendService.remove(queryWrapper);
        if (res) {
            return gson.toJson(Util.SuccessMsg("delete friend Success"));
        } else {
            return gson.toJson(Util.ErrMsg("delete friend error"));
        }
    }

    @PostMapping("user/addFriend")
    public String addFriend(@RequestHeader Map<String, String> header, @NotNull @RequestBody User friend) {
        Integer uid = jwtConfig.getUidFromHeader(header);
        UserFriend userFriend = new UserFriend();
        userFriend.setUserId(uid);
        userFriend.setFriendId(friend.getUid());
        boolean res = userFriendService.save(userFriend);
        if (res) {
            return gson.toJson(Util.SuccessMsg("add friend Success"));
        } else {
            return gson.toJson(Util.ErrMsg("add friend error"));
        }
    }
}
