package com.jd.quant.core.service.utils;

import com.jd.quant.core.common.support.QuantTaskConstants;
import com.jd.quant.core.common.utils.BeanJsonUtil;
import com.jd.quant.core.common.utils.ComponentContext;
import com.jd.quant.core.common.utils.DateUtils;
import com.jd.quant.core.common.utils.FileUtil;
import com.jd.quant.core.dao.redis.RedisDao;
import com.jd.quant.core.domain.common.QuantTaskRequest;
import com.jd.quant.core.domain.position.InfoPacks;
import com.jd.quant.core.domain.profit.DayProfit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 结果操作工具类
 *
 * @author Zhiguo.Chen
 */
public class QuantResultUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(QuantResultUtils.class);

    /**
     * 分钟级数据中间临时存储数据
     */
    public static ConcurrentMap<String, DayProfit> dayProfitMap = new ConcurrentHashMap<>();

    /**
     * RedisDao
     */
    static RedisDao redisDao;

//    static Map<String, DayProfit> lastProfitResultMap = new HashMap<>();

//    private static JSSOperateRPC jssOperateRPC;

    static {
        redisDao = (RedisDao) ComponentContext.getBean("redisDao");
//        jssOperateRPC = (JSSOperateRPC) BeanFactoryUtil.getBean("jSSOperateRPC");
    }


    public static boolean isRunning(String pin, Long strategyId, Long requestTime, Integer strategyType) {
        requestTime = determineRequestTime(strategyType, requestTime);
        String key = KeyUtil.isRunningKey(pin, strategyId, requestTime, strategyType);

        String type = redisDao.get(key);

        return type != null && type.equals(QuantTaskConstants.RUN_TYPE_RUNNING);
    }

    /**
     * 更新运行状态
     *
     * @param pin
     * @param strategyId
     * @param requestTime
     * @param strategyType
     */
    public static void updateRunningStatus(String pin, Long strategyId, Long requestTime, Integer strategyType) {
        try {
            requestTime = determineRequestTime(strategyType, requestTime);

            int exTime = strategyType.equals(QuantTaskConstants.TASK_TYPE_SIMULATE) ? 600 : 24 * 3600;
            String key = KeyUtil.isRunningKey(pin, strategyId, requestTime, strategyType);
            String value = redisDao.get(key);
            if (StringUtils.isBlank(value) || !QuantTaskConstants.RUN_TYPE_EXCEPTION.equals(value)) {
                redisDao.setex(key, QuantTaskConstants.RUN_TYPE_RUNNING, exTime);
                LOGGER.info("updateRunningStatus成功！key:{}，exTime：{}，runType：{}", key, exTime, QuantTaskConstants.RUN_TYPE_RUNNING);
            }
        } catch (Exception e) {
            LOGGER.error("更新运行状态失败：{}", e.getMessage(), e);
        }
    }

    public static void setRunType(String pin, Long strategyId, Long requestTime, Integer strategyType, String runType) {
        try {
            requestTime = determineRequestTime(strategyType, requestTime);
            int exTime = strategyType.equals(QuantTaskConstants.TASK_TYPE_SIMULATE) ? 600 : 24 * 3600;
            String key = KeyUtil.isRunningKey(pin, strategyId, requestTime, strategyType);
            String value = redisDao.get(key);
            if (StringUtils.isBlank(value) || !QuantTaskConstants.RUN_TYPE_EXCEPTION.equals(value) || !QuantTaskConstants.RUN_TYPE_END.equals(runType)) {
                redisDao.setex(key, runType, exTime);
            }

        } catch (Exception e) {
            LOGGER.error("设置运行状态异常：{}", e.getMessage(), e);
        }
    }

    /**
     * 获取分布式锁
     *
     * @param key    分布式锁Key
     * @param expire 超期时间
     * @return
     */
    public static boolean getDistributedLock(String key, int expire) {
        boolean result = redisDao.setnx(key, String.valueOf(System.currentTimeMillis()), expire);
        LOGGER.info("获取分布式锁结果：{}", result);
        return result;
    }

    /**
     * 存储回测结果
     *
     * @param pin
     * @param strategyId
     * @param requestTime
     * @param strategyType
     * @param dayProfit
     * @return
     */
    public static String putRegressionResult(String pin, Long strategyId, Long requestTime, Integer strategyType, DayProfit dayProfit) {
        String key = KeyUtil.regressionResultKey(pin, strategyId, requestTime, strategyType);
        try {
            String jsonStr = BeanJsonUtil.bean2Json(dayProfit);
//            lastProfitResultMap.put(key, dayProfit);

            String cancelKey = KeyUtil.getCancelTaskKey(pin, strategyId, requestTime, strategyType);
            return saveToCacheAndFile(pin, key, jsonStr, 600, cancelKey);
        } catch (Exception e) {
            LOGGER.error("保存任务结果异常：{}, key={}", e.getMessage(), key, e);
            return "error";
        }
    }

    private static String saveToCacheAndFile(String pin, String key, String jsonStr, int exTime, String cancelKey) {
        //Redis存储一份结果
        redisDao.lpush(key, jsonStr, exTime);
        //本地写一份结果
        FileUtil.writeResultFile(pin, key, jsonStr);
        if (redisDao.get(cancelKey) != null) {
            redisDao.delete(cancelKey);
            return "cancel";
        }
        return "success";
    }


    public static boolean clearRegressionResult(String pin, Long strategyId, Long requestTime, Integer strategyType) {
        String key = KeyUtil.regressionResultKey(pin, strategyId, requestTime, strategyType);
        try {
            redisDao.delete(key);
            return true;
        } catch (Exception e) {
            LOGGER.error("clearRegressionResult：{}, key={}", e.getMessage(), key, e);
        }
        return false;
    }

    public static boolean putErrorResult(String userPin, Long strategyId, Long requestTime, String errorMessage, Integer strategyType) {
        String key = KeyUtil.getErrorResultKey(userPin, strategyId, requestTime);
        try {
            setRunType(userPin, strategyId, requestTime, strategyType, QuantTaskConstants.RUN_TYPE_EXCEPTION);
            redisDao.setex(key, errorMessage == null ? "NULL" : errorMessage, 3600);
            clearRegressionResult(userPin, strategyId, requestTime, strategyType);
            return true;
        } catch (Exception e) {
            LOGGER.error("putErrorResult：{}, key={}", e.getMessage(), key, e);
        }
        return false;
    }


    public static DayProfit getMinuteDayProfit(String pin, Long strategyId, Long requestTime, Integer strategyType) {
        String key = KeyUtil.reactorKey(pin, strategyId, requestTime, strategyType);
        DayProfit dayProfit = dayProfitMap.get(key);
        dayProfitMap.remove(key);
        return dayProfit;
    }

    public static void addMinuteProfit(DayProfit dayProfit, String pin, Long strategyId, Long requestTime, Integer strategyType) {
        String key = KeyUtil.reactorKey(pin, strategyId, requestTime, strategyType);
        if (dayProfitMap.get(key) != null) {
            DayProfit cache = dayProfitMap.get(key);
            dayProfit.setBuy(dayProfit.getBuy().add(cache.getBuy()));
            dayProfit.setSell(dayProfit.getSell().add(cache.getSell()));
            cache.getTransactionDetailList().addAll(dayProfit.getTransactionDetailList());
            dayProfit.setTransactionDetailList(cache.getTransactionDetailList());
        }
        dayProfitMap.put(key, dayProfit);
    }

    /**
     * 模拟结果保存到Redis, 如果实盘模拟的天数大于10天，则只按天保存结果数据，否则按分钟保存
     *
     * @param key
     * @param dayProfit
     */
    public static void cacheSimulateResult(String key, DayProfit dayProfit) {
        if (dayProfit.getDaysPassed() != null && dayProfit.getDaysPassed() > 10) {
            List<DayProfit> dayProfitCacheList = QuantResultUtils.mergeMinuteProfit(dayProfit, key);

            redisDao.delete(key);
            for (DayProfit dayProfitCache : dayProfitCacheList) {
                redisDao.rpush(key, BeanJsonUtil.bean2Json(dayProfitCache), 24 * 3600 * 10);
            }
        } else {
            redisDao.rpush(key, BeanJsonUtil.bean2Json(dayProfit), 24 * 3600 * 10);
        }
    }

    /**
     * 替换最后一个dayProfit
     *
     * @param key
     * @param dayProfit
     */
    public static void cacheSimulateResultForReal(String key, DayProfit dayProfit) {
        redisDao.rset(key, BeanJsonUtil.bean2Json(dayProfit), 24 * 3600 * 10);
    }

    private static List<DayProfit> mergeMinuteProfit(DayProfit dayProfit, String key) {
        int start = 0;
        List<String> cacheValues = new LinkedList<>();
        List<String> cacheValueTemp;
        while (!CollectionUtils.isEmpty((cacheValueTemp = redisDao.lrangeAll(key, start, start + 1000)))) {
            cacheValues.addAll(cacheValueTemp);
            start += 1000;
        }
        List<DayProfit> resultList = new ArrayList<>(1);

        if (!CollectionUtils.isEmpty(cacheValues)) {
            for (String cacheValue : cacheValues) {
                DayProfit cachedDayProfit = BeanJsonUtil.json2Object(cacheValue, DayProfit.class);

                //合并今天之前的数据
                if (DateUtils.getBetweenDays(cachedDayProfit.getDate(), dayProfit.getDate()) != 0) {
                    if (!CollectionUtils.isEmpty(resultList)) {
                        DayProfit previousResult = resultList.get(resultList.size() - 1);
                        //已经存在合并中的结果，则累加数据
                        if (DateUtils.getBetweenDays(cachedDayProfit.getDate(), previousResult.getDate()) == 0) {
                            cachedDayProfit.setBuy(cachedDayProfit.getBuy().add(previousResult.getBuy()));//当日买入
                            cachedDayProfit.setSell(cachedDayProfit.getSell().add(previousResult.getSell()));//当日卖出
                            cachedDayProfit.getTransactionDetailList().addAll(previousResult.getTransactionDetailList());
                            cachedDayProfit.getLogList().addAll(previousResult.getLogList());
                            resultList.set(resultList.size() - 1, cachedDayProfit);
                            continue;
                        }
                    }

                    //否则把缓存的结果直接保存
                    resultList.add(cachedDayProfit);
                } else {
                    //当天数据以传入参数为基础累加数据
                    dayProfit.setBuy(dayProfit.getBuy().add(cachedDayProfit.getBuy()));//当日买入
                    dayProfit.setSell(dayProfit.getSell().add(cachedDayProfit.getSell()));//当日卖出
                    cachedDayProfit.getTransactionDetailList().addAll(dayProfit.getTransactionDetailList());
                    dayProfit.setTransactionDetailList(cachedDayProfit.getTransactionDetailList());
                    cachedDayProfit.getHoldDetails().clear();
                    cachedDayProfit.getHoldDetails().addAll(dayProfit.getHoldDetails());
                    dayProfit.getLogList().addAll(cachedDayProfit.getLogList());
                }
            }
        }

        resultList.add(dayProfit);

        return resultList;
    }

//    /**
//     * 保存到jss
//     *
//     * @param key
//     * @param mergeMinuteProfit
//     */
//    public static void flushToJss(String key, Boolean mergeMinuteProfit) {
//        try {
//            LOGGER.info("开始保存jss |" + key);
//            redisDao.flushToJss(key, mergeMinuteProfit);
//            LOGGER.info("保存jss 成功|" + key);
//        } catch (Exception e) {
//            LOGGER.error("保存到jss失败：{}", e.getMessage(), e);
//        }
//    }
    public static void cacheInfoPack(QuantTaskRequest taskRequest, InfoPacks info) {

        String key = KeyUtil.getInfoKey(taskRequest.getUserPin(), taskRequest.getStrategyId(), 0L, taskRequest.getStrategyType());
        try {
            if (info != null) {
                redisDao.setex(key, BeanJsonUtil.bean2Json(info), 24 * 3600 * 10);
            }
        } catch (Exception e) {
            LOGGER.error("保存Info信息失败：{}", e.getMessage(), e);
        }
    }

    public static void cacheInfoPack(String key, InfoPacks info) {
        try {
            if (info != null) {
                redisDao.setex(key, BeanJsonUtil.bean2Json(info), 24 * 3600 * 10);
            }
        } catch (Exception e) {
            LOGGER.error("保存Info信息失败：{}", e.getMessage(), e);
        }
    }

    public static InfoPacks loadInfoFromRedis(String key) throws Exception {
        try {
            String value = redisDao.get(key);
            if (value != null) {
                return BeanJsonUtil.json2Object(value, InfoPacks.class);
            }
        } catch (Exception e) {
            LOGGER.error("从redis加载实盘模拟持仓信息失败：{}, key={}", e.getMessage(), key, e);
            throw new Exception("从redis加载实盘模拟持仓信息失败:" + e.getMessage());
        }

        return null;
    }

    public static void clearInfoCached(String key) {
        try {
            redisDao.delete(key);
        } catch (Exception e) {
            LOGGER.error("从redis删除实盘模拟持仓信息失败：{}, key={}", e.getMessage(), key, e);
        }
    }


    public static void clearInstrumentCached(String key) {
        try {
            redisDao.delete(key);
        } catch (Exception e) {
            LOGGER.error("从redis删除实盘模拟股票池信息失败：{}, key={}", e.getMessage(), key, e);
        }
    }


    /**
     * 复制保存实盘模拟的结果记录
     *
     * @param keyFrom
     * @param keyTo
     */
//    public static void moveResult(String keyFrom, String keyTo) {
//        jssOperateRPC.move(keyFrom, keyTo);
//    }


    /**
     * 设置实盘模拟状态为停止运行
     *
     * @param userPin
     * @param strategyId
     * @param strategyType
     */
    public static void setSimulationCanceled(String userPin, Long strategyId, Integer strategyType, boolean cancel) {
        try {
            String key = KeyUtil.getSimulateCancelKey(userPin, strategyId, strategyType);
            if (cancel) {
                redisDao.setex(key, "1", 24 * 3600);
            } else {
                redisDao.delete(key);
            }
        } catch (Exception e) {
            LOGGER.error("Error to set simulation canceled: {}", e.getMessage(), e);
        }
    }

    public static boolean isSimulationCanceled(String userPin, Long strategyId, Integer strategyType) {
        return redisDao.exists(KeyUtil.getSimulateCancelKey(userPin, strategyId, strategyType));
    }


    /**
     * 删除缓存的strategy对象
     *
     * @param key
     */
    public static void clearStrategyCached(String key) {
        try {
            redisDao.delete(key);
        } catch (Exception e) {
            LOGGER.error("从redis删除实盘模拟持仓信息失败：{}, key={}", e.getMessage(), key, e);
        }
    }

    public static void cacheLastProfit(String profitKey, DayProfit lastProfit) {
        try {
            redisDao.setex(profitKey, BeanJsonUtil.bean2Json(lastProfit), 24 * 3600 * 10);
        } catch (Exception e) {
            LOGGER.error("保存DayProfit信息失败：{}", e.getMessage(), e);
        }
    }


    public static void putLastProfit(String profitKey, DayProfit lastProfit) {
        try {
            redisDao.setex(profitKey, BeanJsonUtil.bean2Json(lastProfit), 24 * 3600);
        } catch (Exception e) {
            LOGGER.error("保存lastProfit信息失败：{}", e.getMessage(), e);
        }
    }

    public static DayProfit loadDayProfitFromRedis(String key) throws Exception {
        try {
            if (redisDao.exists(key)) {
                return BeanJsonUtil.json2Object(redisDao.get(key), DayProfit.class);
            }
        } catch (Exception e) {
            LOGGER.error("从redis加载DayProfit信息失败：{}, key={}", e.getMessage(), key, e);
            throw new Exception("从redis加载实盘模拟持仓信息失败:" + e.getMessage());
        }

        return null;
    }


    /**
     * 删除缓存的保存DayProfit信息失败对象
     *
     * @param key
     */
    public static void clearDayProfitCached(String key) {
        try {
            redisDao.delete(key);
        } catch (Exception e) {
            LOGGER.error("从redis删除DayProfit信息失败：{}, key={}", e.getMessage(), key, e);
        }
    }


    public static Long determineRequestTime(Integer taskType, Long initialRequestTime) {
        if (taskType.equals(QuantTaskConstants.TASK_TYPE_SIMULATE)) {
            return 0L;
        }

        return initialRequestTime;
    }

    public static boolean checkUUID(String userPin, Long strategyId, Integer strategyType, String uuid) {
        String key = KeyUtil.uuidKey(userPin, strategyId, strategyType);
        String currentUUID = redisDao.get(key);
        return StringUtils.isNotBlank(currentUUID) && StringUtils.isNotBlank(uuid) && currentUUID.equalsIgnoreCase(uuid);
    }

    public static void setStrategyUUID(String userPin, Long strategyId, Integer strategyType, String uuid) {
        String key = KeyUtil.uuidKey(userPin, strategyId, strategyType);
        redisDao.setex(key, uuid, 24 * 3600);

    }

    public static void clearUUID(String userPin, Long strategyId, Integer strategyType) {
        String key = KeyUtil.uuidKey(userPin, strategyId, strategyType);
        redisDao.delete(key);
    }

    public static void removeMinuteProfit(QuantTaskRequest taskRequest) {
        String key = KeyUtil.minuteProfitKey(taskRequest.getUserPin(), taskRequest.getStrategyId(), taskRequest.getRequestTime(), taskRequest.getStrategyType());
        dayProfitMap.remove(key);
    }

}
