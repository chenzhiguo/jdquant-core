package com.jd.quant.core.dao;

import com.jd.quant.core.domain.City;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

/**
 * Description:
 *
 * @author Zhiguo.Chen <me@chenzhiguo.cn>
 */
@Mapper
public interface TestMapper {

    /**
     * 通过参数添加
     *
     * @param city
     * @return
     */
//    @Insert("insert into user(name,age) values(#{name},#{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")   //返回主键
    int addCity(City city);
}
