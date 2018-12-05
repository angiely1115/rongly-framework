package com.xs.rongly.framework.stater.core.base.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

/**
 * @Author: lvrongzhuan
 * @Description: 并发测试类
 * @Date: 2018/11/24 14:22
 * @Version: 1.0
 * modified by:
 */
@Slf4j
public class ConcurrentTest<T> {
   public  T t;
    public  <T> void start(int clientTotal, int treadTotal, Executor executor, Consumer<T> consumer) {
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        Semaphore semaphore = new Semaphore(treadTotal);
        for (int i=0;i<clientTotal;i++){
                executor.execute(()->{
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("当前线程：{}",Thread.currentThread().getName());
                    consumer.accept((T) t);
                    semaphore.release();
                    countDownLatch.countDown();
                });

        }
        try {
            countDownLatch.await();
            log.info("任务执行完成..........");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
