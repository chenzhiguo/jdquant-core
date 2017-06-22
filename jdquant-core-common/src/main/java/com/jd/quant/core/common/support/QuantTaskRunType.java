package com.jd.quant.core.common.support;

/**
 * 任务类型
 *
 * @author Zhiguo.Chen <me@chenzhiguo.cn>
 */
public enum QuantTaskRunType {

    RUNNING("running"), END("end"), EXCEPTION("exception"), CANCEL("cancel");

    private String state;

    QuantTaskRunType(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
