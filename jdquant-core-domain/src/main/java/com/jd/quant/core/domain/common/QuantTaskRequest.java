package com.jd.quant.core.domain.common;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务请求
 *
 * @author Zhiguo.Chen
 */
@Data
public class QuantTaskRequest implements Serializable{

    /**
     * 用户id
     */
    private String userPin;
    /**
     * 策略名
     */
    private String strategyName;

    /**
     * 策略id
     */
    private Long strategyId;

    /**
     * 源代码
     */
    private String sourceCode;
    /**
     * 策略语言类型
     */
    private String languageType;

    /**
     * 源代码版本号
     */
    private Integer version;

    /**
     * 回测起始时间点
     */
    private Date startDate;
    /**
     * 回测结束时间点
     */
    private Date endDate;

    /**
     * 初始回测资金
     */
    private BigDecimal initialCash;
    /**
     * 回测任务类型
     */
    private String regressionType;

    /**
     * 运行开始时间
     */
    private Date runStartDate;

    /**
     * 最后运行时间
     */
    private Date lastRunTime;

    /**
     * 用户访问时间。用户pin+策略id+requestTime
     */
    private Long requestTime;

    /**
     * 任务类型：1、回测，2模拟 3竞赛模拟，4竞赛回测,5 真实交易
     */
    private Integer strategyType;

    private boolean encrypted = false;

    private boolean strategyFromCache = false;

    /**
     * 模拟运行是否初次启动
     */
    private boolean isFirstRun = false;

    /**
     * 模拟频率，minute：按分钟模拟
     */
    private String simulationFrequency;

    /**
     * 是否是智投策略
     */
    private boolean smartInvest = false;
}
