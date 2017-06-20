package com.jd.quant.core.domain.position;

import com.google.common.collect.Lists;
import com.jd.quant.core.domain.instrument.Instrument;
import com.jd.quant.core.domain.profit.TransactionDetail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 回测时的持仓信息
 */
public class InfoPacks implements Serializable {

    private static final long serialVersionUID = -3185561447321146953L;

    /**
     * 时间
     */
    private IRuntime runtime = new IRuntime();

    /**
     * 持仓资产信息
     */
    private IPortfolio portfolio;

    /**
     * 持仓股票信息
     */
    private Map<String, IPosition> positionMap = new HashMap<String, IPosition>();

    /**
     * 所选股票池的基本信息
     */
    private Map<String, Instrument> instrumentMap;

    /**
     * 所有未成交的限价单
     */
    private LinkedList<TransactionDetail> transactionDetails = Lists.newLinkedList();

    /**
     * 用户交易参数
     */
    private TransactionParam transactionParam = new TransactionParam();

    /**
     * 是否运行，程序被中断的关键字段
     */
    private Boolean isRunning = true;

    public IRuntime getRuntime() {
        return runtime;
    }

    public void setRuntime(IRuntime runtime) {
        this.runtime = runtime;
    }

    public IPortfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(IPortfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Map<String, IPosition> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<String, IPosition> positionMap) {
        this.positionMap = positionMap;
    }

    public Map<String, Instrument> getInstrumentMap() {
        return instrumentMap;
    }

    public void setInstrumentMap(Map<String, Instrument> instrumentMap) {
        this.instrumentMap = instrumentMap;
    }

    public IRuntime runtime() {
        return this.runtime;
    }

    public IPosition position(String id) {
        IPosition position = positionMap.get(id);
        if (position == null) {
            position = new IPosition();
        }
        return position;
    }

    public IPortfolio portfolio() {
        return this.portfolio;
    }

    public boolean getRunning() {
        return isRunning;
    }

    public void setRunning(Boolean running) {
        isRunning = running;
    }

    public TransactionDetail[] transactionDetails() {
        if (this.transactionDetails != null && this.transactionDetails.size() > 0) {
            return this.transactionDetails.toArray(new TransactionDetail[this.transactionDetails.size()]);
        }
        return new TransactionDetail[]{};
    }

    public List<TransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }

    public TransactionParam getTransactionParam() {
        return transactionParam;
    }

    public void setTransactionParam(TransactionParam transactionParam) {
        this.transactionParam = transactionParam;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("daysPassed").append("=").append(runtime.getDaysPassed()).append(";");
        sb.append("date").append("=").append(runtime.getCurrentDate()).append(";");
        sb.append("initialCash").append("=").append(this.getPortfolio().getInitialCash()).append(";");
        sb.append("availableCash").append("=").append(runtime.getDaysPassed()).append(";");
        sb.append("totalReturn").append("=").append(runtime.getDaysPassed()).append(";");
        sb.append("dailyReturn").append("=").append(runtime.getDaysPassed()).append(";");
        sb.append("marketValue").append("=").append(runtime.getDaysPassed()).append(";");

        sb.append("portfolioValue").append("=").append(this.getPortfolio().getInitialCash()).append(";");
        sb.append("profitAndLoss").append("=").append(this.getPortfolio().getProfitAndLoss()).append(";");
        sb.append("annualizedAvgReturns").append("=").append(this.getPortfolio().getAnnualizedAvgReturns()).append(";");
        sb.append("benchmarkPercent").append("=").append(this.getPortfolio().getBenchmarkPercent()).append(";");
        sb.append("totalCommission").append("=").append(this.getPortfolio().getTotalCommission()).append(";");
        sb.append("previousPortfolioValue").append("=").append(this.getPortfolio().getPreviousPortfolioValue()).append(";");
        sb.append("benchmarkDailyReturn").append("=").append(this.getPortfolio().getBenchmarkDailyReturn()).append(";");
        sb.append("initialBenchmarkPrice").append("=").append(this.getPortfolio().getInitialBenchmarkPrice()).append(";");
        sb.append("previousBenchmarkPrice").append("=").append(this.getPortfolio().getPreviousBenchmarkPrice()).append(";");
        sb.append("yearBenchmarkPercent").append("=").append(this.getPortfolio().getYearBenchmarkPercent()).append(";");
        sb.append("currentBenchmarkPrice").append("=").append(this.getPortfolio().getCurrentBenchmarkPrice()).append(";");
        sb.append("totalProfitAndLoss").append("=").append(this.getPortfolio().getTotalProfitAndLoss()).append(";");
        sb.append("limitCash").append("=").append(this.getPortfolio().getLimitCash()).append(";");

        return sb.toString();
    }

    public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
        this.transactionDetails = new LinkedList<>(transactionDetails);
    }
}
