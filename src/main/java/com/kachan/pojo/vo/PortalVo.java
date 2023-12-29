package com.kachan.pojo.vo;

import lombok.Data;

import java.io.PrintWriter;

/**
 * @author KagurazakaAsahi
 * @version 1.0
 * @description: TODO
 * @date 2023/12/14 21:11
 */

@Data
public class PortalVo {
    private String keyWords;

    private int type = 0;

    private int pageNum = 1;

    private int pageSize = 10;
}
