package com.jd.quant.core.common.support;

/**
 * 任务类型
 *
 * @author Zhiguo.Chen <me@chenzhiguo.cn>
 */
public enum QuantTaskType {

    REGRESSION(1, "回测"), SIMULATE(2, "实盘模拟");

    private int code;

    private String name;

    QuantTaskType(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
