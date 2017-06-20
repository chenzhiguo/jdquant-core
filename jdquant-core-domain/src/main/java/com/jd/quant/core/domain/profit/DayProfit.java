package com.jd.quant.core.domain.profit;

import com.jd.quant.core.domain.log.LogOutput;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * 日收益基础类型
 *
 * @author Zhiguo.Chen
 */
public class DayProfit implements Serializable {

    /**
     * 累计手续费
     */
    private BigDecimal commission = BigDecimal.ZERO;

    /**
     * 累计收益百分比
     */
    private BigDecimal cumulativePercent = BigDecimal.ZERO;

    /**
     * 基准收益百分比
     */
    private BigDecimal benchmarkPercent = BigDecimal.ZERO;

    /**
     * 当前基准价格
     */
    private BigDecimal benchmarkPrice = BigDecimal.ZERO;

    /**
     * 回测年化收益
     */
    private BigDecimal yearPercent = BigDecimal.ZERO;

    /**
     * 基准年化收益
     */
    private BigDecimal yearBenchmarkPercent = BigDecimal.ZERO;

    /**
     * 当日盈亏
     */
    private BigDecimal dayProfit = BigDecimal.ZERO;

    /**
     * 当日购买
     */
    private BigDecimal buy = BigDecimal.ZERO;

    /**
     * 当日卖出
     */
    private BigDecimal sell = BigDecimal.ZERO;

    /**
     * 日期
     */
    private Date date;

    /**
     * 现金
     */
    private BigDecimal cash = BigDecimal.ZERO;

    /**
     * 总持仓金额
     */
    private BigDecimal totalPositionPrice = BigDecimal.ZERO;

    /**
     * 当前总价值
     */
    private BigDecimal totalPortfolioValue = BigDecimal.ZERO;

    /**
     * 总的浮动盈亏
     */
    private BigDecimal totalDayProfit = BigDecimal.ZERO;

    /**
     * 持仓：包括每个股票的开盘价、所持股数、持仓金额
     */
    private List<HoldDetail> holdDetails;

    /**
     * 日志
     */
    private List<LogOutput> logList = new ArrayList<LogOutput>();

    /**
     * 自定义绘图值
     */
    private Map<String, Double> plotMap = new HashMap<String, Double>();

    /**
     * 收益汇总（指标对象）
     */
    private QuantSummary quantSummary;

    /**
     * 指标收益汇总
     */
    private List<RiskResult> riskList;

    /**
     * 运行时长
     */
    private Double runTime = 0d;

    /**
     * 已运行百分比
     */
    private Integer runPercent;

    /**
     * 买卖明细
     */
    private List<TransactionDetail> transactionDetailList = new ArrayList<TransactionDetail>();

    /**
     * 昨日基准价格
     */
    private BigDecimal preBenchmarkPrice = BigDecimal.ZERO;

    /**
     * 每周初始价值
     */
    private BigDecimal initialValueWeekly = BigDecimal.ZERO;

    /**
     * 每月初始价值
     */
    private BigDecimal initialValueMonthly = BigDecimal.ZERO;

    /**
     * 运行天数
     */
    private Integer daysPassed;


    public Integer getDaysPassed() {
        return daysPassed;
    }

    public void setDaysPassed(Integer daysPassed) {
        this.daysPassed = daysPassed;
    }

    public BigDecimal getPreBenchmarkPrice() {
        return preBenchmarkPrice;
    }

    public void setPreBenchmarkPrice(BigDecimal preBenchmarkPrice) {
        this.preBenchmarkPrice = preBenchmarkPrice;
    }

    public BigDecimal getInitialValueWeekly() {
        return initialValueWeekly;
    }

    public void setInitialValueWeekly(BigDecimal initialValueWeekly) {
        this.initialValueWeekly = initialValueWeekly;
    }

    public BigDecimal getInitialValueMonthly() {
        return initialValueMonthly;
    }

    public void setInitialValueMonthly(BigDecimal initialValueMonthly) {
        this.initialValueMonthly = initialValueMonthly;
    }

    public BigDecimal getBenchmarkPrice() {
        return benchmarkPrice;
    }

    public void setBenchmarkPrice(BigDecimal benchmarkPrice) {
        this.benchmarkPrice = benchmarkPrice;
    }

    public List<TransactionDetail> getTransactionDetailList() {
        return transactionDetailList;
    }

    public BigDecimal getYearPercent() {
        return yearPercent;
    }

    public void setYearPercent(BigDecimal yearPercent) {
        this.yearPercent = yearPercent;
    }

    public BigDecimal getYearBenchmarkPercent() {
        return yearBenchmarkPercent;
    }

    public void setYearBenchmarkPercent(BigDecimal yearBenchmarkPercent) {
        this.yearBenchmarkPercent = yearBenchmarkPercent;
    }

    public BigDecimal getTotalPortfolioValue() {
        return totalPortfolioValue;
    }

    public void setTotalPortfolioValue(BigDecimal totalPortfolioValue) {
        this.totalPortfolioValue = totalPortfolioValue;
    }

    public void setTransactionDetailList(List<TransactionDetail> transactionDetailList) {
        this.transactionDetailList = transactionDetailList;
    }

    public Double getRunTime() {
        return runTime;
    }

    public void setRunTime(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            this.runTime = new BigDecimal(endDate.getTime() - startDate.getTime()).divide(new BigDecimal(1000)).doubleValue();
        }
    }

    public double getCommission() {
        if (this.commission == null) {
            return 0d;
        }
        return commission.doubleValue();
    }

    public BigDecimal getBigDecimalCommission() {
        return commission;
    }


    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public double getCumulativePercent() {
        if (this.cumulativePercent == null) {
            return 0d;
        }
        return cumulativePercent.doubleValue();
    }

    public void setCumulativePercent(BigDecimal cumulativePercent) {
        this.cumulativePercent = cumulativePercent;
    }

    public double getBenchmarkPercent() {
        if (this.benchmarkPercent == null) {
            return 0d;
        }
        return benchmarkPercent.doubleValue();
    }

    public void setBenchmarkPercent(BigDecimal benchmarkPercent) {
        this.benchmarkPercent = benchmarkPercent;
    }

    public double getDayProfit() {
        if (this.dayProfit == null) {
            return 0d;
        }
        return dayProfit.doubleValue();
    }

    public BigDecimal getBigDecimalDayProfit() {
        return this.dayProfit;
    }

    public BigDecimal getBigDecimalBenchmarkPercent() {
        return this.benchmarkPercent;
    }

    public BigDecimal getBigDecimalCumulativePercent() {
        return this.cumulativePercent;
    }

    public void setDayProfit(BigDecimal dayProfit) {
        this.dayProfit = dayProfit;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getTotalPositionPrice() {
        return totalPositionPrice;
    }

    public void setTotalPositionPrice(BigDecimal totalPositionPrice) {
        this.totalPositionPrice = totalPositionPrice;
    }

    public BigDecimal getTotalDayProfit() {
        return totalDayProfit;
    }

    public void setTotalDayProfit(BigDecimal totalDayProfit) {
        this.totalDayProfit = totalDayProfit;
    }

    public List<HoldDetail> getHoldDetails() {
        return holdDetails;
    }

    public void setHoldDetails(List<HoldDetail> holdDetails) {
        this.holdDetails = holdDetails;
    }

    public List<LogOutput> getLogList() {
        return logList;
    }

    public void setLogList(List<LogOutput> logList) {
        this.logList = logList;
    }

    public Map<String, Double> getPlotMap() {
        return plotMap;
    }

    public void setPlotMap(Map<String, Double> plotMap) {
        this.plotMap = plotMap;
    }

    public QuantSummary getQuantSummary() {
        return quantSummary;
    }

    public void setQuantSummary(QuantSummary quantSummary) {
        this.quantSummary = quantSummary;
    }

    public List<RiskResult> getRiskList() {
        return riskList;
    }

    public void setRiskList(List<RiskResult> riskList) {
        this.riskList = riskList;
    }

    public void setRunTime(Double runTime) {
        this.runTime = runTime;
    }

    public Integer getRunPercent() {
        return runPercent;
    }

    public void setRunPercent(Integer runPercent) {
        this.runPercent = runPercent;
    }

    public void setRunPercent(Date startDate, Date endDate, Date now) {
        BigDecimal total = BigDecimal.valueOf(endDate.getTime() - startDate.getTime());
        BigDecimal runCount = BigDecimal.valueOf(now.getTime() - startDate.getTime());
        this.runPercent = runCount.divide(total, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue();
    }

    /**
     * 周收益率
     *
     * @return
     */
    public BigDecimal getProfitRateWeek() {
        if (this.initialValueWeekly == null || this.initialValueWeekly.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return (this.totalPortfolioValue.subtract(this.initialValueWeekly)).divide(this.initialValueWeekly, 4, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 月收益率
     *
     * @return
     */
    public BigDecimal getProfitRateMonth() {
        if (this.initialValueMonthly == null || this.initialValueMonthly.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return (this.totalPortfolioValue.subtract(this.initialValueMonthly)).divide(this.initialValueMonthly, 4, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 今日收益
     *
     * @return
     */
    public BigDecimal getProfitRateToday() {
        BigDecimal before = this.totalPortfolioValue.subtract(this.dayProfit);
        if (!before.equals(new BigDecimal(0))) {
            return this.dayProfit.divide(before, 4, BigDecimal.ROUND_HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }
}