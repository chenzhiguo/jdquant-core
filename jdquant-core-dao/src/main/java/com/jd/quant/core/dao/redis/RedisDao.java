package com.jd.quant.core.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作
 *
 * @author Zhiguo.Chen
 */
@Repository
public class RedisDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDao.class);

    @Autowired
    Environment env;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOperations;

    private final String PREFIX_DEFAULT = "jdquant:";

    private String prefix;

    @PostConstruct
    public void init() {
        prefix = env.getProperty("redisDao.prefix", PREFIX_DEFAULT);
    }


    public void set(String key, String value) {
        valueOperations.set(prefix + key, value);
    }

    public void setex(String key, String value, int exTime) {
        valueOperations.set(prefix + key, value, exTime, TimeUnit.SECONDS);
    }

    public boolean setnx(String key, String value, int exTime) {
        boolean result = valueOperations.setIfAbsent(prefix + key, value);
        redisTemplate.expire(prefix + key, exTime, TimeUnit.SECONDS);
        return result;
    }

    public String get(String key) {
        return valueOperations.get(prefix + key);
    }

    public void valueDelete(String key) {
        valueOperations.set(prefix + key, null);
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(prefix + key);
    }

    public void delete(String key) {
        redisTemplate.delete(prefix + key);
    }

    public void delete(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        List<String> delKeys = new ArrayList<>();
        for (String key : keys) {
            delKeys.add(prefix + key);
        }
        redisTemplate.delete(delKeys);
    }

    public void rpush(String key, String value, int exTime) {
        listOperations.rightPush(prefix + key, value);
        redisTemplate.expire(prefix + key, exTime, TimeUnit.SECONDS);
    }

    public Long rset(String key, String value, int exTime) {
        try {
            listOperations.rightPop(prefix + key);
            Long resultLength = listOperations.rightPush(prefix + key, value);
            redisTemplate.expire(prefix + key, exTime, TimeUnit.SECONDS);
            return resultLength;
        } catch (Throwable t) {
            LOGGER.error("rpush error[key={}]: {}", prefix + key, t.getMessage());
            throw new RuntimeException(t);
        }
    }

    public List<String> lrangeAll(String key, int start, int end) {
        return listOperations.range(prefix + key, start, end);
    }
}