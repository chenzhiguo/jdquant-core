package com.jd.quant.core.domain.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志类
 *
 * @author Zhiguo.Chen
 */
@Data
public class LogOutput implements Serializable {
    
    private Date date;

    private String level;

    private String content;

}