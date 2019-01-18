package com.xs.rongly.framework.stater.web.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2019/1/16 14:58
 * @Version: 1.0
 * modified by:
 */
@Getter
@Setter
public class BizException extends RuntimeException{
    private String code;

    private String msg;

    public BizException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public BizException(Throwable cause, String code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
    }
}
