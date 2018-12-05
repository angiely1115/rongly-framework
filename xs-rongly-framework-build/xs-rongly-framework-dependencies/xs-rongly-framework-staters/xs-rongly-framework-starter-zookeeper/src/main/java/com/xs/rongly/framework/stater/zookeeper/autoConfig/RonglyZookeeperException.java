package com.xs.rongly.framework.stater.zookeeper.autoConfig;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/11/22 10:29
 * @Version: 1.0
 * modified by:
 */
public class RonglyZookeeperException extends RuntimeException{
    public RonglyZookeeperException(String message) {
        super(message);
    }

    public RonglyZookeeperException(String message, Throwable cause) {
        super(message, cause);
    }

    public RonglyZookeeperException(Throwable cause) {
        super(cause);
    }
}
