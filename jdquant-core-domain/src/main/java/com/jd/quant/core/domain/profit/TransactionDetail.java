package com.jd.quant.core.domain.profit;

import com.jd.quant.core.domain.instrument.Instrument;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易详情
 *
 * @author Zhiguo.Chen
 */
@Data
public class TransactionDetail implements Serializable {

	private static final long serialVersionUID = 6970170835906070622L;

	//股票代码
    private Instrument instrument;

    //类型: 买入、卖出
    private String orderType;

    //成交数量
    private BigDecimal count = BigDecimal.ZERO;

    //购买数量
    private BigDecimal buyCount = BigDecimal.ZERO;

    //成交价
    private BigDecimal price = BigDecimal.ZERO;

    //印花税
    private BigDecimal tax = BigDecimal.ZERO;

    //滑点费
    private BigDecimal slippagePrice = BigDecimal.ZERO;

    //总成本
    private BigDecimal totle = BigDecimal.ZERO;

    //交易佣金、手续费
    private BigDecimal commission = BigDecimal.ZERO;

    //交易时间
    private Date date;

    //交易成功标示，flag = true 标示交易成功
    private boolean flag = false;

    //交易失败原因。flag = false 时，此值有值
    private String message;

    //订单号
    private Long orderId;

    //交易类型，1是市价单，2是限价单
    private Integer orderStyle = 1;

    /** 持仓前占比 */
    private BigDecimal proportionFrom;

    /** 持仓前股数 */
    private Integer stockNumberFrom;

    /** 持仓后占比 */
    private BigDecimal proportionTo;

    /** 持仓后股数 */
    private Integer stockNumberTo;

    private Long id;

}