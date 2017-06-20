package com.jd.quant.core.domain.position;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 运行时间 <br>
 * Created by yujianming on 2016/1/28.
 */
public class IRuntime implements Serializable {

    private static final long serialVersionUID = 1L;

    private static SimpleDateFormat timeSDF = new SimpleDateFormat("HHmmss");
    private static SimpleDateFormat dateSDF = new SimpleDateFormat("yyyyMMdd");

    /**
     * 当前回测日期
     */
    private Date date;

    /**
     * 已运行天数
     */
    private Integer daysPassed = 0 ;

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCurrentTime(){
        if(this.date == null){
            return 0;
        }
        return Integer.parseInt(timeSDF.format(this.date));
    }

    public int getCurrentDate(){
        if(this.date == null){
            return 0;
        }
        return Integer.parseInt(dateSDF.format(this.date));
    }

    public Date getRunDate(){
        return this.date;
    }

    public Date getDate() {
        return date;
    }

    public Integer getDaysPassed() {
        if(daysPassed == 0){
            return 1;
        }
        return daysPassed;
    }

    public void setDaysPassed(Integer daysPassed) {
        this.daysPassed = daysPassed;
    }

    public void increment(){
        this.daysPassed = this.daysPassed + 1;
    }

}
