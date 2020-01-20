package org.second.chapter.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Hello world!
 */
@Slf4j
public class AsyncThreadPoolExample2 {
    
    // 0自定义线程池
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor POOL_EXECUTOR =
            new ThreadPoolExecutor(
                    AVAILABLE_PROCESSORS,
                    AVAILABLE_PROCESSORS * 2,
                    1,
                    TimeUnit.MINUTES,
                    new LinkedBlockingQueue<>(5),
                    new ThreadPoolExecutor.CallerRunsPolicy());
    
    public static void doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
        }
        log.info("{}", "--- doSomethingA---");
    }
    
    public static void doSomethingB() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
        }
        log.info("{}", "--- doSomethingB---");
    }
    
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        
        // 1.开启异步单元执行任务A
        POOL_EXECUTOR.execute(
                () -> {
                    try {
                        doSomethingA();
                    } catch (Exception e) {
                        log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
                    }
                });
        
        // 2.执行任务B
        POOL_EXECUTOR.execute(
                () -> {
                    try {
                        doSomethingB();
                    } catch (Exception e) {
                        log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
                    }
                });
        
        // 3.同步等待线程A运行结束
        log.info("consume {}ms", System.currentTimeMillis() - start);
        
        // 4.挂起当前线程
        Thread.currentThread().join();
    }
}