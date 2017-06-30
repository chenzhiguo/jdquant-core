package com.jd.quant.core.service.utils;


/**
 * Created by sunguowei on 2016-3-29.
 */
public class KeyUtil {
    public static final String SIMULATE_RESULT = "sim_result";
    public static final String SIMULATE_PROFIT = "sim_profit";
    public static final String SIMULATE_INFO = "sim_info";
    public static final String SIMULATE_UNIVERSE = "sim_universe";
    public static final String SIMULATE_STRATEGY = "sim_strategy";
    public static final String SIMULATE_INITIALIZER = "sim_initializer";

    public static final String REGRESSION_RESULT = "reg_result";

    private static final String SOURCE_CODE = "source";
    public static final String TRANSACTION_STRATEGY = "transaction_strategy";

    private static final String USER_TOKEN = "user_token";

    public static String getResultKey(String userPin, Long strategyId, Long version, Integer strategyType) {
        String keyType = SIMULATE_RESULT;
        return userPin + "_" + String.valueOf(strategyId) + "_" + version + "_" + keyType;
    }

    public static String getDayProfitKey(String userPin, Long strategyId, Long version, Integer strategyType) {
        String keyType = SIMULATE_PROFIT;
        return userPin + "_" + String.valueOf(strategyId) + "_" + version + "_" + keyType;
    }

    public static String getInfoKey(String userPin, Long strategyId, Long version, Integer strategyType) {
        String keyType = SIMULATE_INFO;
        return userPin + "_" + String.valueOf(strategyId) + "_" + version + "_" + keyType;
    }

    public static String getUniverseKey(String userPin, Long strategyId, Long version, Integer strategyType) {
        String keyType = SIMULATE_UNIVERSE;
        return userPin + "_" + String.valueOf(strategyId) + "_" + version + "_" + keyType;
    }

    public static String getStrategyKey(String userPin, Long strategyId, Long version, Integer strategyType) {
        String keyType = SIMULATE_STRATEGY;
        return userPin + "_" + String.valueOf(strategyId) + "_" + version + "_" + keyType;
    }

    public static String isRunningKey(String pin, Long strategyId, Long requestTime, Integer strategyType) {
        return "isRunning_" + pin + "-" + strategyId + "-" + requestTime + "-" + strategyType;
    }

    public static String getTaskLockKey(String pin, Long strategyId, Long requestTime, Integer strategyType) {
        return "taskLock_" + pin + "-" + strategyId + "-" + requestTime + "-" + strategyType;
    }

    public static String regressionResultKey(String pin, Long strategyId, Long requestTime, Integer strategyType) {
        String keyType = REGRESSION_RESULT;
        return keyType + pin + "-" + strategyId + "-" + requestTime;
    }

    public static String getErrorResultKey(String userPin, Long strategyId, Long requestTime) {
        return "errorkey_" + userPin + "_" + strategyId + "_" + requestTime;
    }

    public static String reactorKey(String pin, Long strategyId, Long requestTime, Integer strategyType) {
        return "quant_run_" + pin + "-" + strategyId + "-" + requestTime + "-" + strategyType;
    }

    /**
     * 日志的keyu
     *
     * @param pin
     * @param strategyId
     * @param requestTime
     * @param strategyType
     * @return
     */
    public static String printLogKey(String pin, Long strategyId, Long requestTime, Integer strategyType) {
        return "quant_log_" + pin + "-" + strategyId + "-" + requestTime + "-" + strategyType;
    }

    public static String riskOfMonthResultKey(String pin, Long strategyId, Long requestTime, Integer strategyType) {
        return "riskOfMonthResult_" + pin + "-" + strategyId + "-" + requestTime + "-" + strategyType;
    }

    public static String getSimulateCancelKey(String userPin, Long strategyId, Integer strategyType) {
        return "SimulationCanceled_" + userPin + "-" + strategyId + "-" + strategyType;
    }

    public static String getSourceCodeKey(String userPin, Long strategyId, Integer version) {
        return userPin + "_" + strategyId + "_" + version + "_" + SOURCE_CODE;
    }

    public static String getContestRegressionKey(String userPin, Long strategyId) {
        return userPin + "_" + strategyId + "_contest_reg_result";
    }

    public static String maxDrawDownKey(String pin, Long strategyId, Long requestTime, String type, Integer strategyType) {
        return "quant_maxDrawDown_" + pin + "-" + strategyId + "-" + requestTime + "-" + strategyType + "-" + type;
    }

    public static String getCalRiskRedisKey(String pin, Long strategyId, Integer strategyType) {
        return "calRisk_redis_" + pin + "-" + strategyId + "-" + strategyType;
    }

    public static String getMaxDrawDownRedisKey(String pin, Long strategyId, Integer strategyType) {
        return "maxDrawDown_redis" + pin + "-" + strategyId + "-" + strategyType;
    }

    public static String getLastProfitKey(String userPin, Long strategyId, long version, Integer strategyType) {
        return "profit_" + userPin + "_" + String.valueOf(strategyId) + "_" + version + "_" + strategyType;
    }

    public static String getCancelTaskKey(String userPin, Long strategyId, Long requestTime, Integer strategyType) {
        return "userCancelTask_" + userPin + "_" + strategyId + "_" + requestTime + "_" + strategyType;
    }

    public static String getCalContestScoreKey(Long contest_id, String userPin, Long strategyId) {
        return "calContestScore_" + contest_id + "_" + userPin + "_" + strategyId;
    }

    public static String getUniverseCacheKey(String userPin, Integer strategyType, Long strategyId, Long requestTime) {
        return "universeCache_" + userPin + "_" + strategyType + "_" + strategyId + "_" + requestTime;
    }

    public static String getBarraKey(String userPin, Long strategyId, Long resultId, String strategyType) {
        String key;
        if (strategyType.equals("simulationIng")) {
            key = "barra_" + userPin + "_" + strategyId + "_" + strategyType;
        } else {
            key = "barra_" + userPin + "_" + strategyId + "_" + resultId + "_" + strategyType;
        }
        return key;
    }

    public static String getStarKey(String userPin, Long strategyId, Long resultId, String strategyType) {
        String key;
        if (strategyType.equals("simulationIng")) {
            key = "star_" + userPin + "_" + strategyId + "_" + strategyType;
        } else {
            key = "star_" + userPin + "_" + strategyId + "_" + resultId + "_" + strategyType;
        }
        return key;
    }

    public static String uuidKey(String userPin, Long strategyId, Integer strategyType) {
        return "uuid_" + userPin + "_" + strategyId + "_" + strategyType;
    }

    public static String minuteProfitKey(String userPin, Long strategyId, Long requestTime, Integer strategyType) {
        return "profitKey" + "-" + userPin + "-" + strategyId + "-" + requestTime + "-" + strategyType;
    }

    public static String getTransactionStrategyKey(String userPin, Long transactionStrategyId) {
        return TRANSACTION_STRATEGY + "_" + userPin + "_" + transactionStrategyId;
    }

    public static String getUserTokenKey(String username) {
        return USER_TOKEN + "_" + username;
    }
}
