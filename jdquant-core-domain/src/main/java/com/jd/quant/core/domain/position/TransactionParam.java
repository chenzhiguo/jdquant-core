package com.jd.quant.core.domain.position;

import com.jd.quant.core.domain.common.QuantTaskRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Created by yujianming on 2016/2/25.<br>
 * 交易参数
 */
@Data
public class TransactionParam implements Serializable {

    /**
     * 默认一个基准，沪深300
     */
    private String benchmark = "399300.SZ";

    /**
     * 滑点
     */
    private BigDecimal slippage = new BigDecimal("0.001");

    /**
     * 手续费
     */
    private BigDecimal commission = new BigDecimal("0.00025");

    /**
     * 默认印花税
     */
    private BigDecimal taxPercent = new BigDecimal("0.001");

    private QuantTaskRequest taskRequest;

}
