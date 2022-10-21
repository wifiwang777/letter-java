package com.wifi.letter.api.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer uid;
    private String name;
    private String password;
    private String avatar;
    private Date createAt;
    private Date updateAt;
}