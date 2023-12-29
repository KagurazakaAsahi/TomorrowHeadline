package com.kachan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kachan.pojo.Type;
import com.kachan.service.TypeService;
import com.kachan.mapper.TypeMapper;
import com.kachan.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author KagurazakaAsahi
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2023-12-14 10:53:35
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {

        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);
    }
}




