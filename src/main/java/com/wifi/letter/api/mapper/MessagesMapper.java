package com.wifi.letter.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wifi.letter.api.model.Messages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {
}
