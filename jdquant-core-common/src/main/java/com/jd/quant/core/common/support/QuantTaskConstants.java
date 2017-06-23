package com.jd.quant.core.common.support;

/**
 * 任务常量
 *
 * @author Zhiguo.Chen
 */
public interface QuantTaskConstants {

    //回测
    Integer TASK_TYPE_REGRESSION = 1;

    //实盘模拟
    Integer TASK_TYPE_SIMULATE = 2;

    String RUN_TYPE_RUNNING ="running";

    String RUN_TYPE_END = "end";

    String RUN_TYPE_EXCEPTION ="exception";

    String RUN_TYPE_CANCEL ="cancel";

}
