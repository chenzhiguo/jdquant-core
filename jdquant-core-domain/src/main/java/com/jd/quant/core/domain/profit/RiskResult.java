package com.jd.quant.core.domain.profit;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 风险指标汇总
 */
public class RiskResult implements Serializable {

	private static final long serialVersionUID = -1754747438766524781L;

	//月份日期(yyyy-MM)
    private String month;

    //1,3,6,12月的风险指标
    private ArrayList<QuantSummary> riskOfMonth;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public ArrayList<QuantSummary> getRiskOfMonth() {
        return riskOfMonth;
    }

    public void setRiskOfMonth(ArrayList<QuantSummary> riskOfMonth) {
        this.riskOfMonth = riskOfMonth;
    }
}