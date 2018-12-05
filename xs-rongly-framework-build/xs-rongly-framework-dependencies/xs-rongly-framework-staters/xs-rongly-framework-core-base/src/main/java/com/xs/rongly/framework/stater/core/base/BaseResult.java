package com.xs.rongly.framework.stater.core.base;

import lombok.Data;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/7/11 17:52
 * @Version: 1.0
 * modified by:
 */
@Data
public class BaseResult<T> {
    private String code = "100001";
    private String message = "系统异常，请联系管理员";
    private T data;
    private final static String SUCCESS_CODE = "000000";
    private final static String SUCCESS_MSG = "success";

    public BaseResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResult() {
    }

    public static BaseResult<?> fail(String code, String message){
        return new BaseResult<>(code,message);
    }

    public static BaseResult fail(){
        return new BaseResult();
    }

    public static <T> BaseResult<T> success(T data){
        BaseResult baseResult =  new BaseResult<T>();
        baseResult.setCode(SUCCESS_CODE);
        baseResult.setMessage(SUCCESS_MSG);
        baseResult.setData(data);
        return baseResult;
    }
}
