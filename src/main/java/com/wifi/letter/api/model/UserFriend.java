package com.wifi.letter.api.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class UserFriend {
    @TableId(type = IdType.AUTO)
    private Integer userId;
    private Integer friendId;
}
