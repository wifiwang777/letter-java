package com.wifi.letter.api.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Messages {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer fromUserId;
    private Integer toUserId;
    private String content;
    private Date createAt;
}
