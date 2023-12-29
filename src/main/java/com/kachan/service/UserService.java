package com.kachan.service;

import com.kachan.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kachan.utils.Result;

/**
* @author KagurazakaAsahi
* @description 针对表【news_user】的数据库操作Service
* @createDate 2023-12-14 10:53:35
*/
public interface UserService extends IService<User> {

    Result login(User user);


    //根据token
    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(User user);
}
