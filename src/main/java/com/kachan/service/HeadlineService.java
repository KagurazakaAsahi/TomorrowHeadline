package com.kachan.service;

import com.kachan.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kachan.pojo.vo.PortalVo;
import com.kachan.utils.Result;

/**
* @author KagurazakaAsahi
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2023-12-14 10:53:35
*/
public interface HeadlineService extends IService<Headline> {

    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline,String token);

    Result findHeadlineByHid(Integer hid);

    Result updateHeadLine(Headline headline);
}
