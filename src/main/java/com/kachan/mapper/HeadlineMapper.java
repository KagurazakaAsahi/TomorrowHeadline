package com.kachan.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kachan.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kachan.pojo.vo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author KagurazakaAsahi
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2023-12-14 10:53:35
* @Entity com.kachan.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    IPage<Map> selectMyPage(IPage page, @Param("portalVo")PortalVo portalVo);

    Map queryDetailMap(Integer hid);
}




