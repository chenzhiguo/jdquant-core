package com.jd.quant.core.domain.instrument;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 股票类
 *
 * @author Zhiguo.Chen
 */
@Data
public class Instrument implements Serializable {

    private Integer id;

    //股票标识,股票编号
    private String orderBookId;

    //股票名称
    private String symbol;

    //返回股票名称的简称。在中国市场，这个字段绝大部分时候是股票名称的拼音首字母。例如“上海石化” 的简称是“SHSH”，“连云港”则简称为“LYG”。
    private String abbrevSymbol;

    //返回股票每手的数量。在上海及深圳证券交易所，这个数总是100。
    private double roundLot = 100d;

    //板块代码
    private String sectorCode;

    //板块中文名称
    private String sectorName;

    //行业代码
    private String industryCode;

    //行业中文名称
    private String industryName;

    //起始日期
    private Integer begindate;

    //截止日期
    private Integer enddate;

    //1：表示st
    private Integer stStock;

    //上市日期
    private Integer listedDate;

    //退市日期
    private Integer sInfoDelistdate;

    //生成instrument的当前时间
    private Date date;
}
