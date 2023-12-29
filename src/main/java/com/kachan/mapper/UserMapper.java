package com.kachan.mapper;

import com.kachan.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author KagurazakaAsahi
* @description 针对表【news_user】的数据库操作Mapper
* @createDate 2023-12-14 10:53:35
* @Entity com.kachan.pojo.User
*/
public interface UserMapper extends BaseMapper<User> {
}




