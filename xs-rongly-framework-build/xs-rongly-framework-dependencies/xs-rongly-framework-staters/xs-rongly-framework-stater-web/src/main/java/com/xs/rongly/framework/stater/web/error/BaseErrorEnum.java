package com.xs.rongly.framework.stater.web.error;

/**
 * @Author: lvrongzhuan
 * @Description: web基础错误码
 * @Date: 2019/3/15 16:27
 * @Version: 1.0
 * modified by:
 */
public enum BaseErrorEnum {
    /**
     *
     */
    PARAMETER_ERROR("000001","参数异常"),
    HTTP_METHOD_NOT_ALLOW_ERROR("000002", "http请求method不对"),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR("000003", "media类型出错"),
    ;
    private String code;
    private String message;

    BaseErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
