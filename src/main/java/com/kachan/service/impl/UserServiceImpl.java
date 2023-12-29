package com.kachan.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kachan.pojo.User;
import com.kachan.service.UserService;
import com.kachan.mapper.UserMapper;
import com.kachan.utils.JwtHelper;
import com.kachan.utils.MD5Util;
import com.kachan.utils.Result;
import com.kachan.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author KagurazakaAsahi
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2023-12-14 10:53:35
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private  UserMapper userMapper;
    @Autowired//必须注入,否则jwtHelper不是static 无法调用
    private JwtHelper jwtHelper;

    /**
     * 登录业务
     * 1.根据账号,查询用户对象 -loginUser
     * 2.如果用户对象为null ,查询失败 ,账号错误  (501)
     * 3.对比密码 失败 (503)
     * 4.根据用户id生成一个token token->result 返回
     *
     *
     *
     * */

    @Override
    public Result login(User user) {
        //根据账号查询数据 MyBatisPLUS 框架
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);

        if (loginUser == null){//用户名不存在 返回501(封装好的枚举)
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //账号没问题,对比密码
        if(!StringUtils.isEmpty(user.getUserPwd())&&MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())){
            //登录成功

            //根据用户id生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));

            Map data = new HashMap();
            data.put("token",token);

            return Result.ok(data);
        }
        //登录失败
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }


    /**
     *根据token获取用户数据
     * 1.token是否在有效期
     * 2.根据token解析userId
     * 3.根据用户id查询用户数据
     * 4.去掉密码,封装result结果返回即可
     *
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        //是否过期 true过期
        boolean expiration = jwtHelper.isExpiration(token);

        if(expiration){
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        int userId = jwtHelper.getUserId(token).intValue();

        User user = userMapper.selectById(userId);
        user.setUserPwd("");

        Map data = new HashMap();
        data.put("loginUser",user);
        return Result.ok(data);
    }


    /**
     * 检查账号是否可用
     * 1.根据账号进行count查询
     * 2.count == 0 可用
     * 3.count > 0 不可用
     *
     * @param username
     * @return
     */
        @Override
        public Result checkUserName(String username) {

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUsername,username);
            User user = userMapper.selectOne(queryWrapper);

            if (user != null){
                return Result.build(null,ResultCodeEnum.USERNAME_USED);
            }

            return Result.ok(null);
        }

    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);

        if (count > 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        int rows = userMapper.insert(user);
        System.out.println("rows = " + rows);
        return Result.ok(null);
    }
}







