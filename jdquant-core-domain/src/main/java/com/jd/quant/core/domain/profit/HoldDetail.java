package com.jd.quant.core.domain.profit;

import com.jd.quant.core.domain.instrument.Instrument;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 每日持仓明细
 *
 * @author Zhiguo.Chen
 */
@Data
public class HoldDetail implements Serializable {

    //股票
    private Instrument instrument;

    //收盘价格
    private BigDecimal closingPrice = BigDecimal.ZERO;

    //持股数量
    private BigDecimal count = BigDecimal.ZERO;

    //持仓金额
    private BigDecimal positionPrice = BigDecimal.ZERO;

    //个股盈亏
    private BigDecimal dayProfit = BigDecimal.ZERO;

    //成本价
    private BigDecimal costPrice = BigDecimal.ZERO;

}