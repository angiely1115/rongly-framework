package com.xs.rongly.framework.stater.core.base;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: lvrongzhuan
 * @Description: 业务操作异常
 * @Date: 2019/4/15 21:21
 * @Version: 1.0
 * modified by:
 */
public class BizOperationException extends RuntimeException{
    @Getter
    private String code;
    @Getter
    private String msg;
    public BizOperationException(ResultCode resultCode,String message, Throwable cause) {
        super(message, cause);
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public BizOperationException(ResultCode resultCode,Throwable cause) {
        super(resultCode.getMsg(),cause);
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }
    public BizOperationException( String code,String msg,Throwable cause) {
        super(msg,cause);
        this.code = code;
        this.msg = msg;
    }
    public BizOperationException( Throwable cause) {
        super(cause);
    }



}
