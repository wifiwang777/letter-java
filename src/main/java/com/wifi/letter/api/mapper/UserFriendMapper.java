package com.wifi.letter.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wifi.letter.api.model.Messages;
import com.wifi.letter.api.model.UserFriend;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFriendMapper extends BaseMapper<UserFriend> {
}
