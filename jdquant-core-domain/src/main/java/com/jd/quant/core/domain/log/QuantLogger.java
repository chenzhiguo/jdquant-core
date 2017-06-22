package com.jd.quant.core.domain.log;

import java.io.Serializable;
import java.util.*;

/**
 * 日志信息
 */
public class QuantLogger implements Serializable {

    private static final long serialVersionUID = 6789924576383288262L;

    private Date date;

    private List<LogOutput> logList = new ArrayList<LogOutput>();

    private Map<String, Double> plotMap = new HashMap<String, Double>();

    public static QuantLogger newInstance(Date date) {
        QuantLogger quantLogger = new QuantLogger();
        quantLogger.setDate(date);
        return quantLogger;
    }

    public List<LogOutput> getLogList() {
        return logList;
    }

    public void setLogList(List<LogOutput> logList) {
        this.logList = logList;
    }

    public void debug(Object o) {
        this.setObject(o, "DEBUG");
    }

    public void debug(String s) {
        this.setString(s, "DEBUG");
    }

    public void info(Object o) {
        this.setObject(o, "INFO");
    }

    public void info(String s) {
        this.setString(s, "INFO");
    }

    public void warn(Object o) {
        this.setObject(o, "WARN");
    }

    public void warn(String s) {
        this.setString(s, "WARN");
    }


    public void error(Object o) {
        this.setObject(o, "ERROR");
    }

    public void error(String s) {
        this.setString(s, "ERROR");
    }

    public void error(String s, Object o) {
        this.setString(s + o.toString(), "ERROR");
    }

    public void setObject(Object o, String level) {
        if (o != null) {
            setString(o.toString(), level);
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setString(String s, String level) {
        LogOutput log = new LogOutput();
        log.setDate(date);
        log.setLevel(level);
        log.setContent(s);
        this.logList.add(log);
    }

    public void plot(String key, double value) {
        this.plotMap.put(key, value);
    }

    public Map<String, Double> getPlotMap() {
        return plotMap;
    }

    public void setPlotMap(Map<String, Double> plotMap) {
        this.plotMap = plotMap;
    }
}