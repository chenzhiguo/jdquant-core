package com.jd.quant.core.domain.common;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 通用应答类
 *
 * @author Zhiguo.Chen
 */
public class CommonResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 是否请求成功
     *
     * @return
     */
    public boolean isSuccess() {
        return get("success") != null && (Boolean) get("success");
    }

    /**
     * 获取请求返回消息
     *
     * @return
     */
    public String getMessage() {
        if (get("message") != null) {
            return (String) get("message");
        }
        return "";
    }

    /**
     * 默认构造方法
     */
    public CommonResponse() {
        super();
        this.put("success", false);
    }

    /**
     * 设置为成功应答
     */
    public CommonResponse success() {
        this.put("success", true);
        return this;
    }

    /**
     * 设置带指定消息的成功应答
     *
     * @param message 成功提示消息
     */
    public CommonResponse success(String message) {
        this.put("success", true);
        this.put("message", message);
        return this;
    }

    /**
     * 返回一个失败消息
     *
     * @param message 失败的提示消息
     */
    public CommonResponse fail(String message) {
        this.put("success", false);
        this.put("message", message);
        return this;
    }

    /**
     * 返回一个包含字段错误信息的错误消息
     *
     * @param message
     * @param errors
     */
    public CommonResponse fail(String message, List<FieldError> errors) {
        this.put("success", false);
        StringBuilder messageBuilder = new StringBuilder(message);
        messageBuilder.append("<ul>");
        for (FieldError fieldError : errors) {
            messageBuilder.append("<li>").append(fieldError.getDefaultMessage()).append("</li>");
        }
        messageBuilder.append("</ul>");
        this.put("message", messageBuilder.toString());
        return this;
    }

    /**
     * 重定向地址
     *
     * @param url 要重定向到的URL
     */
    public CommonResponse redirect(String url) {
        this.put("redirect", url);
        return this;
    }

    /**
     * 向通用应答内设置一项数据对象
     *
     * @param data
     */
    public CommonResponse setData(Object data) {
        Collection collection;
        if (!containsKey("data") || get("data") == null) {
            collection = new ArrayList();
            put("data", collection);
        } else {
            collection = (Collection) get("data");
        }
        collection.add(data);
        return this;
    }

    /**
     * 向通用应答内设置一项数据对象
     *
     * @param key
     * @param data
     */
    public CommonResponse setData(String key, Object data) {
        this.put(key, data);
        return this;
    }

    /**
     * 向通用应答内设置一个集合对象
     *
     * @param collection
     */
    public CommonResponse setData(Collection collection) {
        this.put("data", collection);
        return this;
    }

    /**
     * 快速创建一个成功应答对象
     *
     * @return
     */
    public static CommonResponse createCommonResponse() {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.success();
        return commonResponse;
    }

    /**
     * 快速创建一个应答对象, 可传入一个数据对象, 并置为success
     *
     * @param data
     * @return
     */
    public static CommonResponse createCommonResponse(Object data) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.success();
        commonResponse.setData(data);
        return commonResponse;
    }
}