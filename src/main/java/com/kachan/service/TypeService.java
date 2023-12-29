package com.kachan.service;

import com.kachan.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kachan.utils.Result;

/**
* @author KagurazakaAsahi
* @description 针对表【news_type】的数据库操作Service
* @createDate 2023-12-14 10:53:35
*/
public interface TypeService extends IService<Type> {

    Result findAllTypes();
}
