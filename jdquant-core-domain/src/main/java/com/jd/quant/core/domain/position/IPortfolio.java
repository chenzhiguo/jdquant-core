package com.jd.quant.core.domain.position;

import com.jd.quant.core.common.utils.BigDecimalUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 总持仓资金情况
 *
 * Created by yujianming on 2016/1/24.
 */
public class IPortfolio implements Serializable {

    private static final long serialVersionUID = -2208381440344327844L;

    private BigDecimal initialCash = BigDecimal.ZERO;             //获得投资组合的初始资金
    private BigDecimal availableCash = BigDecimal.ZERO;           //获得投资组合的剩余资金
    private BigDecimal totalReturn = BigDecimal.ZERO;             //获得投资组合的总收益百分比
    private BigDecimal dailyReturn = BigDecimal.ZERO;             //获得投资组合的目前日期的单日收益
    private BigDecimal marketValue = BigDecimal.ZERO;             //获得投资组合的当前市场价值
    private BigDecimal portfolioValue = BigDecimal.ZERO;          //获得投资组合的当前总价值，包含市场价值和剩余资金
    private BigDecimal profitAndLoss = BigDecimal.ZERO;           //获得投资组合的目前日期的当日金钱盈亏
    private BigDecimal annualizedAvgReturns = BigDecimal.ZERO;    //获得投资组合的目前的年化收益率
    private BigDecimal benchmarkPercent = BigDecimal.ZERO;        //基准总收益
    private BigDecimal totalCommission = BigDecimal.ZERO;         //总交易费
    private BigDecimal previousPortfolioValue = BigDecimal.ZERO;  //昨日的总价值价值，包括市场价值和剩余资金
    private BigDecimal benchmarkDailyReturn = BigDecimal.ZERO;    //当日基准收益
    private BigDecimal initialBenchmarkPrice = BigDecimal.ZERO;   //最初的基准价格
    private BigDecimal previousBenchmarkPrice = BigDecimal.ZERO;  //昨日的基准价格
    private BigDecimal yearBenchmarkPercent = BigDecimal.ZERO;    //基准年华收益率
    private BigDecimal limitCash = BigDecimal.ZERO;               //限价单金额
    private BigDecimal currentBenchmarkPrice = BigDecimal.ZERO;   //当前的基准价格
    private BigDecimal totalProfitAndLoss = BigDecimal.ZERO;      //累计金钱盈亏
    private BigDecimal preTotalReturn = BigDecimal.ZERO;         //上次资金变动总收益百分比

    private Map<String, IPosition> map;

    //TODO 隐藏了。
    //    double getDividendReceivable     //获得投资组合的应收股息及分红，在股息部分有详细的解释               注意：涉及到姑息粉红的计算方法


    public BigDecimal getPreTotalReturn() {
        return preTotalReturn;
    }

    public void setPreTotalReturn(BigDecimal preTotalReturn) {
        this.preTotalReturn = preTotalReturn;
    }

    /**
     * @return 投资组合的初始资金
     */
    public double getInitialCashValue() {
        return BigDecimalUtils.doubleValue(initialCash);
    }

    /**
     * @return 投资组合的剩余资金
     */
    public double getAvailableCashValue() {
        return BigDecimalUtils.doubleValue(availableCash);
    }

    /**
     * @return 投资组合的总收益
     */
    public double getTotalReturnValue() {
        return BigDecimalUtils.doubleValue(totalReturn);
    }

    /**
     * @return 投资组合的目前日期的单日收益
     */
    public double getDailyReturnValue() {
        return BigDecimalUtils.doubleValue(dailyReturn);
    }

    /**
     * @return 投资组合的当前市场价值
     */
    public double getMarketValueValue() {
        return BigDecimalUtils.doubleValue(marketValue);
    }

    /**
     * @return 投资组合的当前总价值，包含市场价值和剩余资金
     */
    public double getPortfolioValueValue() {
        return BigDecimalUtils.doubleValue(portfolioValue);
    }

    /**
     * @return 投资组合的目前日期的金钱盈亏
     */
    public double getProfitAndLossValue() {
        return BigDecimalUtils.doubleValue(profitAndLoss);
    }

    /**
     * @return 投资组合的目前的年化收益率
     */
    public double getAnnualizedAvgReturnsValue() {
        return BigDecimalUtils.doubleValue(annualizedAvgReturns);
    }


    public double getBenchmarkPercentValue() {
        return BigDecimalUtils.doubleValue(benchmarkPercent);
    }

    public double getTotalCommissionValue() {
        return BigDecimalUtils.doubleValue(totalCommission);
    }

    public double getPreviousPortfolioValueValue() {
        return BigDecimalUtils.doubleValue(previousPortfolioValue);
    }

    public double getBenchmarkDailyReturnValue() {
        return BigDecimalUtils.doubleValue(benchmarkDailyReturn);
    }

    public double getPreviousBenchmarkPriceValue() {
        return BigDecimalUtils.doubleValue(previousBenchmarkPrice);
    }


    /**
     * @return 投资组合的初始资金
     */
    public double getInitialCashMoney() {
        return BigDecimalUtils.doubleValue(initialCash);
    }

    /**
     * @return 投资组合的剩余资金
     */
    public double getAvailableCashMoney() {
        return BigDecimalUtils.doubleValue(availableCash);
    }

    /**
     * @return 投资组合的总收益
     */
    public double getTotalReturnMoney() {
        return BigDecimalUtils.doubleValue(totalReturn);
    }

    /**
     * @return 投资组合的目前日期的单日收益
     */
    public double getDailyReturnMoney() {
        return BigDecimalUtils.doubleValue(dailyReturn);
    }

    /**
     * @return 投资组合的当前市场价值
     */
    public double getMarketValueMoney() {
        return BigDecimalUtils.doubleValue(marketValue);
    }

    /**
     * @return 投资组合的当前总价值，包含市场价值和剩余资金
     */
    public double getPortfolioValueMoney() {
        return BigDecimalUtils.doubleValue(portfolioValue);
    }

    /**
     * @return 投资组合的目前日期的金钱盈亏
     */
    public double getProfitAndLossMoney() {
        return BigDecimalUtils.doubleValue(profitAndLoss);
    }

    /**
     * @return 投资组合的目前的年化收益率
     */
    public double getAnnualizedAvgReturnsMoney() {
        return BigDecimalUtils.doubleValue(annualizedAvgReturns);
    }


    public double getBenchmarkPercentMoney() {
        return BigDecimalUtils.doubleValue(benchmarkPercent);
    }

    public double getTotalCommissionMoney() {
        return BigDecimalUtils.doubleValue(totalCommission);
    }

    public double getPreviousPortfolioValueMoney() {
        return BigDecimalUtils.doubleValue(previousPortfolioValue);
    }

    public double getBenchmarkDailyReturnMoney() {
        return BigDecimalUtils.doubleValue(benchmarkDailyReturn);
    }

    public double getPreviousBenchmarkPriceMoney() {
        return BigDecimalUtils.doubleValue(previousBenchmarkPrice);
    }


    public BigDecimal getLimitCash() {
        return limitCash;
    }

    public void setLimitCash(BigDecimal limitCash) {
        this.limitCash = limitCash;
    }

    public BigDecimal getYearBenchmarkPercent() {
        return yearBenchmarkPercent;
    }

    public void setYearBenchmarkPercent(BigDecimal yearBenchmarkPercent) {
        this.yearBenchmarkPercent = yearBenchmarkPercent;
    }

    public BigDecimal getPreviousBenchmarkPrice() {
        return previousBenchmarkPrice;
    }

    public BigDecimal getInitialBenchmarkPrice() {
        return initialBenchmarkPrice;
    }

    public void setInitialBenchmarkPrice(BigDecimal initialBenchmarkPrice) {
        this.initialBenchmarkPrice = initialBenchmarkPrice;
    }

    public void setPreviousBenchmarkPrice(BigDecimal previousBenchmarkPrice) {
        this.previousBenchmarkPrice = previousBenchmarkPrice;
    }

    public BigDecimal getBenchmarkDailyReturn() {
        return benchmarkDailyReturn;
    }

    public void setBenchmarkDailyReturn(BigDecimal benchmarkDailyReturn) {
        this.benchmarkDailyReturn = benchmarkDailyReturn;
    }

    public BigDecimal getBenchmarkPercent() {
        return benchmarkPercent;
    }

    public void setBenchmarkPercent(BigDecimal benchmarkPercent) {
        this.benchmarkPercent = benchmarkPercent;
    }

    public BigDecimal getInitialCash() {
        return initialCash;
    }

    public void setInitialCash(BigDecimal initialCash) {
        this.initialCash = initialCash;
    }

    public BigDecimal getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(BigDecimal availableCash) {
        this.availableCash = availableCash;
    }

    public BigDecimal getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(BigDecimal totalReturn) {
        this.totalReturn = totalReturn;
    }

    public BigDecimal getDailyReturn() {
        return dailyReturn;
    }

    public void setDailyReturn(BigDecimal dailyReturn) {
        this.dailyReturn = dailyReturn;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(BigDecimal portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public BigDecimal getProfitAndLoss() {
        return profitAndLoss;
    }

    public void setProfitAndLoss(BigDecimal profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

    public BigDecimal getAnnualizedAvgReturns() {
        return annualizedAvgReturns;
    }

    public void setAnnualizedAvgReturns(BigDecimal annualizedAvgReturns) {
        this.annualizedAvgReturns = annualizedAvgReturns;
    }

    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Map<String, IPosition> getMap() {
        return map;
    }

    public BigDecimal getPreviousPortfolioValue() {
        return previousPortfolioValue;
    }

    public void setPreviousPortfolioValue(BigDecimal previousPortfolioValue) {
        this.previousPortfolioValue = previousPortfolioValue;
    }

    public BigDecimal getTotalProfitAndLoss() {
        return totalProfitAndLoss;
    }

    public void setTotalProfitAndLoss(BigDecimal totalProfitAndLoss) {
        this.totalProfitAndLoss = totalProfitAndLoss;
    }

    public void setMap(Map<String, IPosition> map) {
        this.map = map;
    }


    public BigDecimal getCurrentBenchmarkPrice() {
        return currentBenchmarkPrice;
    }

    public void setCurrentBenchmarkPrice(BigDecimal currentBenchmarkPrice) {
        this.currentBenchmarkPrice = currentBenchmarkPrice;
    }

    public IPosition position(String key) {
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

}
