package com.xs.rongly.framework.stater.core.base;

public enum ResultCode {
    SUCCESS("000000", "success"),
    INNER_ERROR("500", "服务器内部错误"),
    HTTP_METHOD_NOT_ALLOW_ERROR("601", "http请求method不对"),
    HTTP_CONNECTION_TIME_OUT("602", "http请求连接超时"),
    HTTP_READ_TIME_OUT("603", "http读超时"),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED_ERROR("604", "media类型出错"),
    HTTP_INNTER_CONNECTION_TIME_OUT("652", "内部系统调用连接超时"),
    HTTP_INNTER_READ_TIME_OUT("653", "内部系统调用读取超时"),
    HTTP_INNTER_SERVICE_NOT_AVAILABLE("654", "内部系统调用服务不可用"),
    HTTP_INNTER_ERROR("689", "http内部调用服务错误"),
    HTTP_ERROR("699", "http请求错误"),
    SQL_ERROR("799", "sql操作异常"),
    REDIS_ERROR("899", "redis操作异常"),
    FILE_ERROR("1199", "文件操作异常"),
    ZOOKEEPER_ERROR("1200", "zookeeper异常"),
    KAFKA_PRODUCER_ERROR("1300", "kafkaProducer异常"),
    SECURITY_ACCESS_DENIED("1403", "未授权访问!"),
    SECURITY_AUTHENTICATION("1401", "身份验证失败,请输入正确信息!"),
    SECURITY_TOKEN_NOT_ACTIVE("1402", "TOKEN 已失效，刷新token或者重新获取!"),
    PARAM_ERROR("10001", "参数校验错误");

    private String code;
    private String msg;

    private ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getCode() {
        return this.code;
    }
}