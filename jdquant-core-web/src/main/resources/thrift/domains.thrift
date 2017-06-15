namespace java com.jd.quant.core.domain.thrift


struct TTransactionParam{
    //默认一个基准，沪深300
    1:optional  string benchmark = "399300.SZ";

    //滑点
    2:optional  string slippage = "0.002";

    //手续费
    3:optional  string commission = "0.00025";

    //默认印花税
    4:optional  string taxPercent = "0.001";
}

struct TQuantTaskRequest{
    //用户id
    1: optional string user_pin;

    //策略id
    2: optional i64 strategy_id;

    //回测起始时间点
    3: optional string start_date;
    
    //回测结束时间点
    4: optional string end_date;

    //运行频率（分钟，天）
    5: optional string run_frequency;

    //用户访问时间。用户pin+策略id+requestTime
    6: optional i64 request_time;

    //代码类型 1、回测，2模拟 3竞赛模拟，4竞赛回测
    7: optional i32 run_type;

    //模拟运行是否初次启动
    8: optional bool is_first_run = false;

    9: optional string uuid;

}

struct TRuntime{
    1: optional string date;
    2: optional i32 daysPassed=0;

    //该阶段是否结束（如果为true;回测会结束回测，上传云文件;模拟会上传云文件）
    3: optional bool runEnd;
    
    //运行时长
    4: optional double runTime = 0.0;

    //已运行百分比
    5: optional i32 runPercent;
}

/**
* 持仓资产信息
**/
struct TPortfolio{
	1: optional double starting_cash = 0.0;
    2: optional double cash = 0.0;
    3: optional double total_returns = 0.0;
    4: optional double daily_returns = 0.0;
    5: optional double market_value = 0.0;
    6: optional double portfolio_value = 0.0;
    7: optional double today_pnl = 0.0;
    8: optional double pnl = 0.0;
    9: optional double start_date = 0.0;
    10: optional double annualized_returns = 0.0;
    11: optional double dividend_receivable = 0.0;
    12: optional double previous_portfolio_value = 0.0;
    13: optional double benchmark_daily_return = 0.0;
    14: optional double year_benchmark_percent = 0.0;
    15: optional double benchmark_percent = 0.0;
    16: optional double initial_benchmark_price = 0.0;
    17: optional double previous_benchmark_price = 0.0;
    18: optional double current_benchmark_price = 0.0;
    19: optional double pre_total_return = 0.0;
    20: optional double total_commission = 0.0;
}

/**
* 股票持仓信息
**/
struct TPosition{
	1: optional string order_book_id;
    2: optional double quantity = 0.0;
    3: optional double bought_quantity = 0.0;
    4: optional double sold_quantity = 0.0;
    5: optional double sold_value = 0.0;
    6: optional double sellable = 0.0;
    7: optional double total_orders = 0.0;
    8: optional double total_trades = 0.0;
    9: optional double total_commission = 0.0;
    10: optional double total_profit_and_loss = 0.0;
    11: optional double closing_price = 0.0;
    12: optional double can_sell_shares_num = 0.0;
    13: optional double bought_value = 0.0;
    14: optional double cost_price = 0.0;
    15: optional string buy_time;
    16: optional i32 hold_days = 0;
    17: optional double position_ratio = 0.0;
}

/**
* 日志结构
**/
struct TLogOutput{
    1: optional string date;
    2: optional string level;
    3: optional string content;
}

struct TInformer{
	1: optional string date;

    2: optional list<TLogOutput> log_list ;

    3: optional map<string, double> plot_map ;
}

struct TQuantAddExceptionRequest{
    //用户id
    1: required string userPin;

    //策略id
    2: required i64 strategyId;

    //用户访问时间。用户pin+策略id+requestTime
    3: optional i64 requestTime;

    //异常信息
    4: optional string message;

    //任务类型：1、回测，2模拟 3竞赛
    5: optional i32 strategyType;

}

/**
* 持仓详情
**/
struct THoldDetail {

    //股票代码
    1: optional string orderBookId;

    //收盘价格
    2: optional double closingPrice = 0.0;

    //持股数量
    3: optional double count = 0.0;

    //总个股盈亏
    4: optional double dayProfit = 0.0;

    //成本价
    5: optional double costPrice = 0.0;
}

struct TQuantSummary {

    1: optional double cumulativePercent;

    //Alpha
    2: optional double alpha;

    3: optional double beta;

    4: optional double sharpe;

    5: optional double sortino;

    6: optional double InformationRatio;

    7: optional double volatility;

    //最大回撤
    8: optional double fall;

    9: optional double trackingError;

    10: optional double downsideRisk;
}

struct TRiskResult {

    1: optional string month;

    2: optional list<TQuantSummary> riskOfMonth;
}

/**
* 交易详情
**/
struct TTransactionDetail {

    //股票代码
    1: optional string orderBookId;

    //类型: 买入、卖出
    2: optional string orderType;

    //成交数量
    3: optional double count = 0.0;

    //购买数量
    4: optional double buyCount = 0.0;

    //成交价
    5: optional double price = 0.0;

    //印花税
    6: optional double tax = 0.0;

    //总成本
    7: optional double totle = 0.0;

    //交易佣金、手续费
    8: optional double commission = 0.0;

    //交易时间
    9: optional string date;

    //交易成功标示，flag = true 标示交易成功
    10: optional bool flag = true;

    //交易失败原因。flag = false 时，此值有值
    11: optional string message;

    //订单号
    12: optional i64 orderId;

    //交易类型，1是市价单，2是限价单
    13: optional i32 orderStyle = 1;

    //持仓前占比
    14: optional double proportionFrom;

    //持仓后占比
    15: optional double proportionTo;

    //持仓前股数
    16: optional i32 stockNumberFrom;

    //持仓后股数
    17: optional i32 stockNumberTo;
}

/**
* 日收益大结构体
**/
struct TDayProfit{

    //累计手续费
    1: optional double commission = 0.0;

    //累计收益（百分比）
    2: optional double cumulativePercent = 0.0;

    //基准收益（百分比）
    3: optional double benchmarkPercent = 0.0;

    //回测年化收益
    4: optional double yearPercent = 0.0;

    //基准年化收益
    5: optional double yearBenchmarkPercent = 0.0;

    //所有股票总当日盈亏
    6: optional double dayProfit = 0.0;

    //当日购买金额
    7: optional double buy = 0.0;

    //当日卖出金额
    8: optional double sell = 0.0;

    //日期
    9: optional string date;

    //剩余现金
    10: optional double cash = 0.0;

    //市场总价值 = marketValue
    11: optional double totalPositionPrice = 0.0;

    //当前总价值
    12: optional double totalPortfolioValue = 0.0;

    //总的浮动盈亏 = 当前总价值-初始资金
    13: optional double totalDayProfit = 0.0;

    //持仓：包括每个股票的开盘价、所持股数、持仓金额
    14: optional list<THoldDetail> holdDetails;

    //日志
    15: optional list<TLogOutput> logList;

    //用户自定义绘图
    16: optional map<string, double> plotMap;

    //指标对象
    17: optional TQuantSummary quantSummary;

     //1;3;6;12风险指标
    18: optional list<TRiskResult> riskList;

    //运行时长
    19: optional double runTime = 0.0;

    //已运行百分比
    20: optional i32 runPercent;

    //买卖明细
    21: optional list<TTransactionDetail> transactionDetails;

    //昨日基准价格
    22: optional double preBenchmarkPrice = 0.0;

    //每周初始价值
    23: optional double initialValueWeekly = 0.0;

    //每月初始价值
    24: optional double initialValueMonthly = 0.0;

    //运行天数
    25: optional i32 daysPassed;
}

/**
* 结果保存请求结构
**/
struct TQuantSaveResultTaskRequest {
    //用户id
    1: optional string userPin;

    //策略id
    2: optional i64 strategyId;

    //初始回测资金
    3: optional double initialCash;

    //回测任务类型
    4: optional string regressionType;

    //请求时间
    5: optional i64 requestTime;

    //该阶段是否结束（如果为true;回测会结束回测，上传云文件;模拟会上传云文件）
    6: optional bool runEnd;

    //结果
    7: optional list<TDayProfit> dayProfits;

    //开始时间
    8: optional string startDate;

    //结束时间
    9: optional string endDate;

    //回测任务类型 1、回测，2模拟 3竞赛模拟，4竞赛回测
    10: optional i32 runType;

    //是否智投策略
    11: optional bool smartInvest = false;
}

/**
* 总持仓对象
**/
struct TInfoPacks {
    //持仓资产信息
    1:optional TPortfolio portfolio;

    //持仓股票信息
    2:optional map<string, TPosition> position_map;

    //所有未成交的限价单
    3:optional list<TTransactionDetail> transaction_details;

    //用户交易参数
    4:optional TTransactionParam transaction_param;

    //是否运行，程序被中断的关键字段
    5:optional bool isRunning = true;

    //指标对象
    6: optional TQuantSummary quant_summary;

    //1;3;6;12风险指标
    7: optional list<TRiskResult> risk_list;
}

/**
* 新版结果保存（参数为持仓信息与交易详情）
**/
struct TQuantSaveResultRequest {
     //时间
     1:optional TRuntime runtime;

     //交易详情
     2:optional list<TTransactionDetail> transaction_details;

     //用户pin，策略id，requestTime
     3:optional TQuantTaskRequest task_request;

     //资金和持仓
     4:optional TInfoPacks infoPacks;

     5:optional TInformer infomer;
}

/**
* 获取最新持仓信息
**/
struct TQuantGetInfoResponse {

    1:optional bool success;

    2:optional string code;

    3:optional string message;

    4:optional TInfoPacks info;

    //时间
    5:optional TRuntime runtime;
}

/**
* 获取历史结果
**/
struct TQuantGetResultResponse {

    1:optional bool success;

    2:optional string code;

    3:optional string message;

    4:optional list<TDayProfit> profits;
}

/**
* 请求响应结构体
**/
struct TQuantCoreResponse {
    //请求是否成功
    1: optional bool success = true;

    //正常运行：running； 结束：end； 用户取消任务：cancel；接口异常：exception
    2: optional string code;

    3: optional string message;

    4: optional string detailInfo;
}