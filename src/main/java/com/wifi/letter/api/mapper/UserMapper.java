package com.wifi.letter.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wifi.letter.api.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select b.* from user_friend a left join user b on a.friend_id = b.uid where a.user_id = #{uid}")
    List<User> getFriends(@Param("uid") Integer uid);
}
