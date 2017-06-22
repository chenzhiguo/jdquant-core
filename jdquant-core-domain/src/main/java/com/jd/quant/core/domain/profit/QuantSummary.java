package com.jd.quant.core.domain.profit;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 收益总结（指标）
 *
 * @author Zhiguo.Chen
 */
@Data
public class QuantSummary implements Serializable {

    //累计收益百分比
    private BigDecimal cumulativePercent = BigDecimal.ZERO;

    //回测年化收益
    private BigDecimal yearPercent = BigDecimal.ZERO;

    //基准年化收益
    private BigDecimal yearBenchmarkPercent = BigDecimal.ZERO;

    //基准收益百分比
    private BigDecimal benchmarkPercent = BigDecimal.ZERO;

    //Alpha
    private BigDecimal alpha = BigDecimal.ZERO;

    private BigDecimal beta = BigDecimal.ZERO;

    private BigDecimal sharpe = BigDecimal.ZERO;

    private BigDecimal sortino = BigDecimal.ZERO;

    private BigDecimal InformationRatio = BigDecimal.ZERO;

    private BigDecimal volatility = BigDecimal.ZERO;

    //最大回撤
    private BigDecimal fall = BigDecimal.ZERO;

    private BigDecimal trackingError = BigDecimal.ZERO;

    private BigDecimal downsideRisk = BigDecimal.ZERO;

    //1,3,6,12风险指标
    private List<RiskResult> riskList;
}
