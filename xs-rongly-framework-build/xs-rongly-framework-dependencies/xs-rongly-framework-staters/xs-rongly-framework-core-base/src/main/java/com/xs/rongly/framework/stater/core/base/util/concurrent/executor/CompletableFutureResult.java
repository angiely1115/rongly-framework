package com.xs.rongly.framework.stater.core.base.util.concurrent.executor;

import com.xs.rongly.framework.stater.core.base.BizOperationException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: lvrongzhuan
 * @Description: 异步执行获取返回结果
 * @Date: 2018/11/12 14:47
 * @Version: 1.0
 * modified by:
 */
@Slf4j
public class CompletableFutureResult<U> {
    private CompletableFutureResult() {
    }

    /**
     * 获取结果超时时间
     */
    private final static Long FUTURE_GET_TIME_OUT = 10L;
    public static <U> U  getFutureResult(CompletableFuture<U> completableFuture){
        try {
            return completableFuture.get(FUTURE_GET_TIME_OUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("获取结果线程中断异常",e);
            Thread.currentThread().interrupt();
            throw new BizOperationException(e);
        } catch (ExecutionException e) {
            log.error("获取结果执行异常",e);
            throw new BizOperationException(e);
        } catch (TimeoutException e) {
            log.error("获取结果超时异常",e);
            throw new BizOperationException(e);
        }
    }
}
