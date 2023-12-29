package com.kachan.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kachan.pojo.Headline;
import com.kachan.pojo.vo.PortalVo;
import com.kachan.service.HeadlineService;
import com.kachan.mapper.HeadlineMapper;
import com.kachan.utils.JwtHelper;
import com.kachan.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author KagurazakaAsahi
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2023-12-14 10:53:35
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**首页分页数据查询
     *
     * 1.进行分页数据查询
     * 2.分页数据,拼接到result即可
     *
     * 注意1.查询需要自定义语句 自定义mapper的方法 携带分页
     * 注意2.返回的结果List<Map>
     *
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {
        IPage<Map> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        headlineMapper.selectMyPage(page,portalVo);

        List<Map> records = page.getRecords();//拿到数据集合 封装(两层(pageInfo(pageData)))
        Map data = new HashMap();
        data.put("pageData",records);//pageData,pageNum ... 封装到同一层 ->再封装到pageInfo内
        data.put("pageNum",page.getCurrent());
        data.put("pageSize",page.getSize());
        data.put("totalPage",page.getPages());
        data.put("totalSize",page.getTotal());
        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo",data);
        return Result.ok(pageInfo);
    }


    /*
    * 根据id查询详情
    *
    * 1.查询对应的数据 (多表查询 头条和用户表 方法需要自定义 返回map)
    * 2.修改阅读量 +1 (version乐观锁 必须知道版本号 故先查数据再修改阅读量)
    *
    * */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map data = headlineMapper.queryDetailMap(hid);
        Map headlineMap = new HashMap();
        headlineMap.put("headline",data);
        //修改阅读量+1
        Headline headline = new Headline();
        headline.setHid((Integer) data.get("hid"));
                    headline.setVersion((Integer) data.get("version")); //设置版本
        headlineMapper.updateById(headline);

        Map<String,Object> pageInfoMap=new HashMap<>();
        pageInfoMap.put("headline",data);
        return Result.ok(headline);

    }

    /**
     * 发布头条
     * 1.补全数据
     */
    @Override
    public Result publish(Headline headline,String token) {
        //根据token查询用户id
        int userId = jwtHelper.getUserId(token).intValue();
        //数据装配
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);
        return Result.ok(null);
    }

    @Override
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineMapper.selectById(hid);
        Map<String,Object> pageInfoMap=new HashMap<>();
        pageInfoMap.put("headline",headline);
        return Result.ok(pageInfoMap);
    }

    /**
     * 修改业务
     * 1.查询version版本
     * 2.补全属性,修改时间 , 版本!
     *
     * @param headline
     * @return
     */
    @Override
    public Result updateHeadLine(Headline headline) {

        //读取版本
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version);
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }
}




