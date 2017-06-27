package com.jd.quant.core.service.utils;

import com.google.common.collect.Lists;
import com.jd.quant.core.common.utils.DateUtils;
import com.jd.quant.core.domain.common.QuantTaskRequest;
import com.jd.quant.core.domain.instrument.Instrument;
import com.jd.quant.core.domain.log.LogOutput;
import com.jd.quant.core.domain.log.QuantLogger;
import com.jd.quant.core.domain.position.*;
import com.jd.quant.core.domain.profit.*;
import com.jd.quant.core.domain.thrift.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Thrift Domain Util for JDQuant Python 2.0
 *
 * @author Zhiguo.Chen
 */
public class ThriftDomainUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThriftDomainUtils.class);

    public static Instrument toInstrument(String orderBookId, String date) {
        Assert.notNull(orderBookId, "股票代码不可为空！");
        Assert.notNull(date, "时间不可为空！");
        if (StringUtils.isEmpty(orderBookId) || StringUtils.isEmpty(date)) {
            LOGGER.error("股票代码或时间不可为空！orderBookId:{}，date:{}", orderBookId, date);
            throw new RuntimeException("股票代码或时间不可为空！");
        }
        //TODO 获取股票信息方法
        Instrument instrument = null;//InstrumentService.getInstrument(orderBookId, DateUtils.string2Date(date));
        if (null == instrument) {
            LOGGER.error("获取股票实体出错！股票代码：{}，日期：{}", orderBookId, date);
            instrument = new Instrument();
            instrument.setOrderBookId(orderBookId);
        }
        return instrument;
    }

    public static TransactionParam toTransactionParam(TTransactionParam tTransactionParam) {
        TransactionParam transactionParam = new TransactionParam();
        if (tTransactionParam != null) {
            transactionParam.setBenchmark(tTransactionParam.getBenchmark());
            transactionParam.setCommission(new BigDecimal(tTransactionParam.getCommission()));
            transactionParam.setSlippage(new BigDecimal(tTransactionParam.getSlippage()));
            transactionParam.setTaxPercent(new BigDecimal(tTransactionParam.getTaxPercent()));
        }
        return transactionParam;
    }

    public static QuantTaskRequest toQuantTaskRequest(TQuantTaskRequest taskRequest) {
        QuantTaskRequest request = new QuantTaskRequest();
        if (taskRequest != null) {
//            request.setEncrypted(taskRequest.isEncrypted());
            request.setEndDate(DateUtils.string2Date(taskRequest.getEnd_date()));
            request.setFirstRun(taskRequest.isIs_first_run());
//            request.setInitialCash(BigDecimal.valueOf(taskRequest.getInit_cash()));
//            request.setLanguageType(taskRequest.getLanguage_type());
//            request.setLastRunTime(DateUtils.string2Date(taskRequest.getLastRunTime()));
            request.setRegressionType(taskRequest.getRun_frequency());
            request.setRequestTime(taskRequest.getRequest_time());
//            request.setRunStartDate(DateUtils.string2Date(taskRequest.getRunStartDate()));
//            request.setSimulationFrequency(taskRequest.getSimulationFrequency());
//            request.setSourceCode(taskRequest.getSource_code());
            request.setStartDate(DateUtils.string2Date(taskRequest.getStart_date()));
//            request.setStrategyFromCache(taskRequest.isStrategyFromCache());
            request.setStrategyId(taskRequest.getStrategy_id());
//            request.setStrategyName(taskRequest.getStrategy_name());
            request.setStrategyType(taskRequest.getRun_type());
            request.setUserPin(taskRequest.getUser_pin());
//            request.setVersion(taskRequest.getVersion());
//            request.setSmartInvest(taskRequest.isSm());
        }
        return request;
    }

    /**
     * Java持仓转Thrift持仓
     *
     * @param info
     * @return
     */
    public static TInfoPacks toThriftInfoPack(InfoPacks info) {
        TInfoPacks tInfo = new TInfoPacks();
        if (info != null) {
            tInfo.setPortfolio(toThriftPortfolio(info.getPortfolio()));
            tInfo.setPosition_map(toThriftPositionMap(info.getPositionMap()));
            tInfo.setIsRunning(info.getRunning());
//            tInfo.setRuntime(toThriftRuntime(info.getRuntime()));
            tInfo.setTransaction_details(toThriftTransactionDetailList(info.getTransactionDetails()));
            tInfo.setTransaction_param(toThriftTransactionParam(info.getTransactionParam()));
        }
        return tInfo;
    }

    private static TTransactionParam toThriftTransactionParam(TransactionParam transactionParam) {
        TTransactionParam tTransactionParam = new TTransactionParam();
        if (transactionParam != null) {
            tTransactionParam.setBenchmark(transactionParam.getBenchmark());
            tTransactionParam.setCommission(transactionParam.getCommission() == null ? 0 : transactionParam.getCommission().doubleValue());
            tTransactionParam.setSlippage(transactionParam.getSlippage() == null ? 0 : transactionParam.getSlippage().doubleValue());
            tTransactionParam.setTaxPercent(transactionParam.getTaxPercent() == null ? 0 : transactionParam.getTaxPercent().doubleValue());
        }
        return tTransactionParam;
    }

    private static List<TTransactionDetail> toThriftTransactionDetailList(List<TransactionDetail> transactionDetails) {
        List<TTransactionDetail> tTransactionDetailList = new ArrayList<>();
        if (transactionDetails != null) {
            transactionDetails.forEach(transactionDetail -> tTransactionDetailList.add(toThriftTransactionDetail(transactionDetail)));
        }
        return tTransactionDetailList;
    }

    private static TTransactionDetail toThriftTransactionDetail(TransactionDetail transactionDetail) {
        TTransactionDetail tTransactionDetail = new TTransactionDetail();
        if (transactionDetail != null) {
            tTransactionDetail.setBuyCount(decimalToDouble(transactionDetail.getBuyCount()));
            tTransactionDetail.setCommission(transactionDetail.getCommission().doubleValue());
            tTransactionDetail.setCount(decimalToDouble(transactionDetail.getCount()));
            tTransactionDetail.setDate(DateUtils.format(transactionDetail.getDate(), "yyyy-MM-dd HH:mm:ss"));
            tTransactionDetail.setFlag(transactionDetail.isFlag());
            tTransactionDetail.setMessage(transactionDetail.getMessage());
            tTransactionDetail.setOrderId(ifNotNull(transactionDetail.getOrderId()));
            tTransactionDetail.setOrderStyle(ifNotNull(transactionDetail.getOrderStyle()));
            tTransactionDetail.setOrderType(transactionDetail.getOrderType());
            tTransactionDetail.setPrice(transactionDetail.getPrice().doubleValue());
//            tTransactionDetail.setSlippagePrice(transactionDetail.getSlippagePrice().doubleValue());
            tTransactionDetail.setTax(transactionDetail.getTax().doubleValue());
            tTransactionDetail.setTotle(transactionDetail.getTotle().doubleValue());
        }

        return tTransactionDetail;
    }


    public static TRuntime toThriftRuntime(IRuntime runtime) {
        TRuntime tRuntime = new TRuntime();
        if (runtime != null) {
            tRuntime.setDate(DateUtils.format(runtime.getDate(), "yyyy-MM-dd HH:mm:ss"));
            tRuntime.setDaysPassed(ifNotNull(runtime.getDaysPassed()));
        }
        return tRuntime;
    }

    private static Map<String, TPosition> toThriftPositionMap(Map<String, IPosition> positionMap) {
        Map<String, TPosition> tPositionMap = new HashMap<>();
        if (positionMap != null) {
            positionMap.entrySet().forEach(entry -> tPositionMap.put(entry.getKey(), toThriftPosition(entry.getValue())));
        }
        return tPositionMap;
    }

    private static TPosition toThriftPosition(IPosition position) {
        TPosition tPosition = new TPosition();
        if (position != null) {
            if (null != position.getInstrument()) {
                tPosition.setOrder_book_id(position.getInstrument().getOrderBookId());
            } else {
                LOGGER.error("转换ThriftPosition对象，持仓股票信息为空！");
                throw new RuntimeException("转换ThriftPosition对象，获取的该持仓信息中股票信息为空！");
            }
            tPosition.setQuantity(decimalToDouble(position.getNonClosedTradeQuantity()));
            tPosition.setBought_quantity(decimalToDouble(position.getBoughtQuantity()));
            tPosition.setSold_quantity(decimalToDouble(position.getSoldQuantity()));
            tPosition.setSold_value(decimalToDouble(position.getSoldValue()));
            tPosition.setSellable(decimalToDouble(position.getCanSellSharesNum()));
            tPosition.setTotal_commission(decimalToDouble(position.getTotalCommission()));
            tPosition.setTotal_orders(decimalToDouble(position.getTotalOrders()));
            tPosition.setTotal_profit_and_loss(decimalToDouble(position.getTotalProfitAndLoss()));
            tPosition.setTotal_trades(decimalToDouble(position.getTotalTrades()));
            tPosition.setCan_sell_shares_num(decimalToDouble(position.getCanSellSharesNum()));
            tPosition.setClosing_price(decimalToDouble(position.getClosingPrice()));
            tPosition.setBought_value(decimalToDouble(position.getBoughtValue()));
            tPosition.setCost_price(decimalToDouble(position.getCostPrice()));
            tPosition.setBuy_time(DateFormatUtils.format(position.getBuyTime(), "yyyy-MM-dd HH:mm:ss"));
            tPosition.setHold_days(position.getHoldDays() == null ? 0 : position.getHoldDays());
            tPosition.setPosition_ratio(decimalToDouble(position.getPositionRatio()));
        }
        return tPosition;
    }

    private static double decimalToDouble(BigDecimal value) {
        if (value != null) {
            return value.doubleValue();
        }

        return 0;
    }

    private static TPortfolio toThriftPortfolio(IPortfolio portfolio) {
        TPortfolio tPortfolio = new TPortfolio();
        if (portfolio != null) {
            tPortfolio.setStarting_cash(decimalToDouble(portfolio.getInitialCash()));
            tPortfolio.setCash(decimalToDouble(portfolio.getAvailableCash()));
            tPortfolio.setTotal_returns(decimalToDouble(portfolio.getTotalReturn()));
            tPortfolio.setDaily_returns(decimalToDouble(portfolio.getDailyReturn()));
            tPortfolio.setMarket_value(decimalToDouble(portfolio.getMarketValue()));
            tPortfolio.setPortfolio_value(decimalToDouble(portfolio.getPortfolioValue()));
            tPortfolio.setToday_pnl(decimalToDouble(portfolio.getProfitAndLoss()));
            tPortfolio.setPnl(decimalToDouble(portfolio.getTotalProfitAndLoss()));
            tPortfolio.setAnnualized_returns(decimalToDouble(portfolio.getAnnualizedAvgReturns()));
            tPortfolio.setDividend_receivable(0d);
            tPortfolio.setPrevious_portfolio_value(decimalToDouble(portfolio.getPreviousPortfolioValue()));
            tPortfolio.setBenchmark_daily_return(decimalToDouble(portfolio.getBenchmarkDailyReturn()));
            tPortfolio.setYear_benchmark_percent(decimalToDouble(portfolio.getYearBenchmarkPercent()));
            tPortfolio.setBenchmark_percent(decimalToDouble(portfolio.getBenchmarkPercent()));
            tPortfolio.setInitial_benchmark_price(decimalToDouble(portfolio.getInitialBenchmarkPrice()));
            tPortfolio.setPrevious_benchmark_price(decimalToDouble(portfolio.getPreviousBenchmarkPrice()));
            tPortfolio.setCurrent_benchmark_price(decimalToDouble(portfolio.getCurrentBenchmarkPrice()));
            tPortfolio.setPre_total_return(decimalToDouble(portfolio.getPreTotalReturn()));
            tPortfolio.setTotal_commission(decimalToDouble(portfolio.getTotalCommission()));
        }
        return tPortfolio;
    }

    private static double ifNotNull(Double value) {
        return value == null ? 0d : value;
    }

    private static long ifNotNull(Long value) {
        return value == null ? 0L : value;
    }

    private static int ifNotNull(Integer value) {
        return value == null ? 0 : value;
    }

    public static QuantLogger toQuantLogger(TInformer informer) {
        QuantLogger iInformer = new QuantLogger();
        if (informer != null) {
            iInformer.setDate(DateUtils.string2Date(informer.getDate()));
            iInformer.setLogList(toLogList(informer.getLog_list()));
            iInformer.setPlotMap(informer.getPlot_map());
        }
        return iInformer;
    }

    private static List<LogOutput> toLogList(List<TLogOutput> logList) {
        List<LogOutput> iLogList = new ArrayList<>();
        if (logList != null) {
            logList.forEach(tLogOutput -> iLogList.add(toLogOutput(tLogOutput)));
        }
        return iLogList;
    }

    private static LogOutput toLogOutput(TLogOutput tLogOutput) {
        LogOutput logOutput = new LogOutput();
        if (tLogOutput != null) {
            logOutput.setDate(DateUtils.string2Date(tLogOutput.getDate()));
            logOutput.setContent(tLogOutput.getContent());
            logOutput.setLevel(tLogOutput.getLevel());
        }
        return logOutput;
    }

    /**
     * Thrift持仓转Java对象持仓
     *
     * @param saveResultRequest
     * @return
     */
    public static InfoPacks toInfoPacks(TQuantSaveResultRequest saveResultRequest) {
        InfoPacks result = new InfoPacks();
        TInfoPacks tInfoPacks = saveResultRequest.getInfoPacks();
        if (tInfoPacks != null) {
            result.setPortfolio(toPortfolio(tInfoPacks.getPortfolio()));
            result.setPositionMap(toPositionMap(tInfoPacks.getPosition_map()));
            result.setRunning(tInfoPacks.isRunning);
            result.setRuntime(toRuntime(saveResultRequest.getRuntime()));
            result.setTransactionDetails(toTransactionDetails(tInfoPacks.getTransaction_details()));
            result.setTransactionParam(toTransactionParam(tInfoPacks.getTransaction_param()));
        } else {
            if (null != saveResultRequest.getTask_request())
                LOGGER.error("本次Python请求中TInfoPacks对象为空！userPin:{}，strategyId:{}", saveResultRequest.getTask_request().getUser_pin(), saveResultRequest.getTask_request().getStrategy_id());
        }
        return result;
    }

    private static IPortfolio toPortfolio(TPortfolio portfolio) {
        IPortfolio result = new IPortfolio();
        if (portfolio != null) {
            result.setInitialCash(BigDecimal.valueOf(portfolio.getStarting_cash()));
            result.setAvailableCash(BigDecimal.valueOf(portfolio.getCash()));
            result.setTotalReturn(BigDecimal.valueOf(portfolio.getTotal_returns()));
            result.setDailyReturn(BigDecimal.valueOf(portfolio.getDaily_returns()));
            result.setMarketValue(BigDecimal.valueOf(portfolio.getMarket_value()));
            result.setPortfolioValue(BigDecimal.valueOf(portfolio.getPortfolio_value()));
            result.setTotalProfitAndLoss(BigDecimal.valueOf(portfolio.getPnl()));
            result.setProfitAndLoss(BigDecimal.valueOf(portfolio.getToday_pnl()));
            result.setAnnualizedAvgReturns(BigDecimal.valueOf(portfolio.getAnnualized_returns()));
            result.setPreviousPortfolioValue(BigDecimal.valueOf(portfolio.getPrevious_portfolio_value()));
            result.setBenchmarkDailyReturn(BigDecimal.valueOf(portfolio.getBenchmark_daily_return()));
            result.setYearBenchmarkPercent(BigDecimal.valueOf(portfolio.getYear_benchmark_percent()));
            result.setBenchmarkPercent(BigDecimal.valueOf(portfolio.getBenchmark_percent()));
            result.setInitialBenchmarkPrice(BigDecimal.valueOf(portfolio.getInitial_benchmark_price()));
            result.setPreviousBenchmarkPrice(BigDecimal.valueOf(portfolio.getPrevious_benchmark_price()));
            result.setCurrentBenchmarkPrice(BigDecimal.valueOf(portfolio.getCurrent_benchmark_price()));
            result.setPreTotalReturn(BigDecimal.valueOf(portfolio.getPre_total_return()));
            result.setTotalCommission(BigDecimal.valueOf(portfolio.getTotal_commission()));
            return result;
        }
        return null;
    }

    private static Map<String, IPosition> toPositionMap(Map<String, TPosition> positionMap) {
        if (positionMap != null) {
            Map<String, IPosition> resultMap = new HashMap<>();
            positionMap.entrySet().forEach(entry -> resultMap.put(entry.getKey(), toPosition(entry.getValue())));
            return resultMap;
        }

        return null;
    }

    private static IPosition toPosition(TPosition value) {
        IPosition position = new IPosition();
        if (value != null) {
            position.setBoughtQuantity(BigDecimal.valueOf(value.getBought_quantity()));
            position.setCanSellSharesNum(BigDecimal.valueOf(value.getCan_sell_shares_num()));
            position.setClosingPrice(BigDecimal.valueOf(value.getClosing_price()));
            position.setInstrument(toInstrument(value.getOrder_book_id(), value.getBuy_time()));
            position.setNonClosedTradeQuantity(BigDecimal.valueOf(value.getQuantity()));
            position.setSoldQuantity(BigDecimal.valueOf(value.getSold_quantity()));
            position.setSoldValue(BigDecimal.valueOf(value.getSold_value()));
            position.setTotalCommission(BigDecimal.valueOf(value.getTotal_commission()));
            position.setTotalOrders(BigDecimal.valueOf(value.getTotal_orders()));
            position.setTotalProfitAndLoss(BigDecimal.valueOf(value.getTotal_profit_and_loss()));
            position.setTotalTrades(BigDecimal.valueOf(value.getTotal_trades()));
            position.setBoughtValue(BigDecimal.valueOf(value.getBought_value()));
            position.setCostPrice(BigDecimal.valueOf(value.getCost_price()));
            position.setBuyTime(DateUtils.string2Date(value.getBuy_time()));
            position.setHoldDays(value.getHold_days());
            position.setPositionRatio(BigDecimal.valueOf(value.getPosition_ratio()));
            return position;
        }
        return null;
    }

    private static IRuntime toRuntime(TRuntime runtime) {
        IRuntime result = new IRuntime();
        if (runtime != null) {
            result.setDate(DateUtils.string2Date(runtime.getDate()));
            result.setDaysPassed(runtime.getDaysPassed());
        }
        return result;
    }

    private static List<TransactionDetail> toTransactionDetails(List<TTransactionDetail> transactionDetails) {
        List<TransactionDetail> resultDetailList = new ArrayList<>();
        if (transactionDetails != null) {
            transactionDetails.forEach(tTransactionDetail -> resultDetailList.add(toTransactionDetail(tTransactionDetail)));
        }
        return resultDetailList;
    }

    private static TransactionDetail toTransactionDetail(TTransactionDetail tTransactionDetail) {
        TransactionDetail detail = new TransactionDetail();
        if (tTransactionDetail != null) {
            detail.setBuyCount(BigDecimal.valueOf(tTransactionDetail.getBuyCount()));
            detail.setCommission(BigDecimal.valueOf(tTransactionDetail.getCommission()));
            detail.setCount(BigDecimal.valueOf(tTransactionDetail.getCount()));
            detail.setDate(DateUtils.string2Date(tTransactionDetail.getDate()));
            detail.setFlag(tTransactionDetail.isFlag());
            detail.setInstrument(toInstrument(tTransactionDetail.getOrderBookId(), tTransactionDetail.getDate()));
            detail.setMessage(tTransactionDetail.getMessage());
            detail.setOrderId(tTransactionDetail.getOrderId());
            detail.setOrderStyle(tTransactionDetail.getOrderStyle());
            detail.setOrderType(tTransactionDetail.getOrderType());
            detail.setPrice(BigDecimal.valueOf(tTransactionDetail.getPrice()));
//            detail.setSlippagePrice(BigDecimal.valueOf(tTransactionDetail.getSlip()));
            detail.setTax(BigDecimal.valueOf(tTransactionDetail.getTax()));
            detail.setTotle(BigDecimal.valueOf(tTransactionDetail.getTotle()));
            detail.setProportionFrom(BigDecimal.valueOf(tTransactionDetail.getProportionFrom()));
            detail.setProportionTo(BigDecimal.valueOf(tTransactionDetail.getProportionTo()));
            detail.setStockNumberFrom(tTransactionDetail.getStockNumberFrom());
            detail.setStockNumberTo(tTransactionDetail.getStockNumberTo());
            return detail;
        }
        return null;
    }

//    public static TransactionResult toTransactionResult(TQuantSaveResultRequest saveResultRequest, Date date) {
//        TransactionResult transactionResult = new TransactionResult(date);
//
//        List<TransactionDetail> transactionDetails = toTransactionDetails(saveResultRequest.getTransaction_details());
//
//        transactionResult.addTransactionDetail(transactionDetails);
//
//        return transactionResult;
//    }

    public static QuantSummary toQuantSummary(TQuantSaveResultRequest saveResultRequest) {
        QuantSummary quantSummary = new QuantSummary();
        TQuantSummary tQuantSummary = saveResultRequest.getInfoPacks().getQuant_summary();
        if (null == tQuantSummary) {
            LOGGER.error("quant_summary为null！userPin:{}，strategyId:{}", saveResultRequest.getTask_request().getUser_pin(), saveResultRequest.getTask_request().getStrategy_id());
            return null;
        }
        copyToQuantSummary(tQuantSummary, quantSummary);

        List<TRiskResult> tRiskResults = saveResultRequest.getInfoPacks().getRisk_list();
        if (!CollectionUtils.isEmpty(tRiskResults)) {
            List<RiskResult> riskResults = Lists.newArrayListWithCapacity(tRiskResults.size());
            RiskResult riskResult;
            for (TRiskResult tRiskResult : tRiskResults) {
                riskResult = new RiskResult();
                riskResult.setMonth(tRiskResult.getMonth());
                if (!CollectionUtils.isEmpty(tRiskResult.getRiskOfMonth())) {
                    ArrayList<QuantSummary> quantSummaries = Lists.newArrayListWithCapacity(tRiskResult.getRiskOfMonth().size());
                    QuantSummary quantSummary1;
                    for (TQuantSummary tQuantSummary1 : tRiskResult.getRiskOfMonth()) {
                        quantSummary1 = new QuantSummary();
                        copyToQuantSummary(tQuantSummary1, quantSummary1);
                        quantSummaries.add(quantSummary1);
                    }
                    riskResult.setRiskOfMonth(quantSummaries);
                }
                riskResults.add(riskResult);
            }
            quantSummary.setRiskList(riskResults);
        }
        //设置累计收益
        quantSummary.setCumulativePercent(BigDecimal.valueOf(saveResultRequest.getInfoPacks().getPortfolio().getTotal_returns()));
        //设置年化收益
        quantSummary.setYearPercent(BigDecimal.valueOf(saveResultRequest.getInfoPacks().getPortfolio().getAnnualized_returns()));
        //设置基准收益
        quantSummary.setBenchmarkPercent(BigDecimal.valueOf(saveResultRequest.getInfoPacks().getPortfolio().getBenchmark_percent()));
        //设置基准年华收益
        quantSummary.setYearBenchmarkPercent(BigDecimal.valueOf(saveResultRequest.getInfoPacks().getPortfolio().getYear_benchmark_percent()));

        return quantSummary;
    }

    public static void copyToQuantSummary(TQuantSummary tQuantSummary, QuantSummary quantSummary) {
        quantSummary.setAlpha(BigDecimal.valueOf(tQuantSummary.getAlpha()));
        quantSummary.setBeta(BigDecimal.valueOf(tQuantSummary.getBeta()));
        quantSummary.setSharpe(BigDecimal.valueOf(tQuantSummary.getSharpe()));
        quantSummary.setSortino(BigDecimal.valueOf(tQuantSummary.getSortino()));
        quantSummary.setInformationRatio(BigDecimal.valueOf(tQuantSummary.getInformationRatio()));
        quantSummary.setVolatility(BigDecimal.valueOf(tQuantSummary.getVolatility()));
        quantSummary.setFall(BigDecimal.valueOf(tQuantSummary.getFall()));
        quantSummary.setTrackingError(BigDecimal.valueOf(tQuantSummary.getTrackingError()));
        quantSummary.setDownsideRisk(BigDecimal.valueOf(tQuantSummary.getDownsideRisk()));
    }

    public static TDayProfit toThriftDayProfit(DayProfit dayProfit) {
        if (dayProfit != null) {
            TDayProfit tDayProfit = new TDayProfit();

            tDayProfit.setCommission(dayProfit.getCommission());
            tDayProfit.setCumulativePercent(dayProfit.getCumulativePercent());
            tDayProfit.setBenchmarkPercent(dayProfit.getBenchmarkPercent());
            tDayProfit.setYearPercent(dayProfit.getYearPercent().doubleValue());
            tDayProfit.setYearBenchmarkPercent(dayProfit.getYearBenchmarkPercent().doubleValue());
            tDayProfit.setDayProfit(dayProfit.getDayProfit());
            tDayProfit.setBuy(dayProfit.getBuy().doubleValue());
            tDayProfit.setSell(dayProfit.getSell().doubleValue());
            tDayProfit.setDate(DateUtils.format(dayProfit.getDate(), "yyyy-MM-dd HH:mm:ss"));
            tDayProfit.setCash(dayProfit.getCash().doubleValue());
            tDayProfit.setTotalPositionPrice(dayProfit.getTotalPositionPrice().doubleValue());
            tDayProfit.setTotalPortfolioValue(dayProfit.getTotalPortfolioValue().doubleValue());
            tDayProfit.setTotalDayProfit(dayProfit.getTotalDayProfit().doubleValue());
            tDayProfit.setHoldDetails(toHoldDetails(dayProfit.getHoldDetails()));
            tDayProfit.setLogList(null);
            tDayProfit.setPlotMap(null);
            tDayProfit.setQuantSummary(toThriftQuantSummary(dayProfit.getQuantSummary()));
            tDayProfit.setRiskList(null);
            tDayProfit.setRunTime(dayProfit.getRunTime() == null ? 0 : dayProfit.getRunTime());
            tDayProfit.setRunPercent(dayProfit.getRunPercent() == null ? 0 : dayProfit.getRunPercent());
            tDayProfit.setTransactionDetails(null);
            tDayProfit.setPreBenchmarkPrice(dayProfit.getPreBenchmarkPrice().doubleValue());
            tDayProfit.setInitialValueWeekly(dayProfit.getInitialValueWeekly().doubleValue());
            tDayProfit.setInitialValueMonthly(dayProfit.getInitialValueMonthly().doubleValue());
            tDayProfit.setDaysPassed(dayProfit.getDaysPassed());

            return tDayProfit;
        }
        return null;
    }

    public static TQuantSummary toThriftQuantSummary(QuantSummary quantSummary) {
        if (null == quantSummary) {
            return null;
        }
        TQuantSummary tQuantSummary = new TQuantSummary();
        tQuantSummary.setCumulativePercent(quantSummary.getCumulativePercent().doubleValue());
        tQuantSummary.setAlpha(quantSummary.getAlpha().doubleValue());
        tQuantSummary.setBeta(quantSummary.getBeta().doubleValue());
        tQuantSummary.setSharpe(quantSummary.getSharpe().doubleValue());
        tQuantSummary.setSortino(quantSummary.getSortino().doubleValue());
        tQuantSummary.setInformationRatio(quantSummary.getInformationRatio().doubleValue());
        tQuantSummary.setVolatility(quantSummary.getVolatility().doubleValue());
        tQuantSummary.setFall(quantSummary.getFall().doubleValue());
        tQuantSummary.setTrackingError(quantSummary.getTrackingError().doubleValue());
        tQuantSummary.setDownsideRisk(quantSummary.getDownsideRisk().doubleValue());

        return tQuantSummary;
    }

    private static List<THoldDetail> toHoldDetails(List<HoldDetail> details) {
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        List<THoldDetail> holdDetails = Lists.newArrayListWithCapacity(details.size());
        THoldDetail tHoldDetail;
        for (HoldDetail holdDetail : details) {
            tHoldDetail = new THoldDetail();
            tHoldDetail.setOrderBookId(holdDetail.getInstrument().getOrderBookId());
            tHoldDetail.setClosingPrice(holdDetail.getClosingPrice().doubleValue());
            tHoldDetail.setCount(holdDetail.getCount().doubleValue());
            tHoldDetail.setDayProfit(holdDetail.getDayProfit().doubleValue());
            tHoldDetail.setCostPrice(holdDetail.getCostPrice().doubleValue());
            holdDetails.add(tHoldDetail);
        }
        return holdDetails;
    }
}
