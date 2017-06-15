package com.jd.quant.core.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author Zhiguo.Chen <me@chenzhiguo.cn>
 */
@Data
public class City implements Serializable {

    /**
     * 城市编号
     */
    private Long id;

    /**
     * 省份编号
     */
    private Long provinceId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 描述
     */
    private String description;

}
