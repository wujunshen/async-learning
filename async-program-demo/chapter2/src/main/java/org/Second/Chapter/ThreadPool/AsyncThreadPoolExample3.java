package org.second.chapter.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class AsyncThreadPoolExample3 {
    /**
     * 自定义线程池
     */
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor POOL_EXECUTOR =
            new ThreadPoolExecutor(
                    AVAILABLE_PROCESSORS,
                    AVAILABLE_PROCESSORS * 2,
                    1,
                    TimeUnit.MINUTES,
                    new LinkedBlockingQueue<>(5),
                    new NamedThreadFactory("ASYNC-POOL"),
                    new ThreadPoolExecutor.CallerRunsPolicy());
    
    public static String doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
        }
        log.info("{}", "--- doSomethingA---");
        return "A Task Done";
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 1.开启异步单元执行任务A
        Future<?> resultA = POOL_EXECUTOR.submit(AsyncThreadPoolExample3::doSomethingA);
        
        // 2.同步等待执行结果
        log.info("is A Task Done? {},content is:{}", "A Task Done".equals(resultA.get()), resultA.get());
    }
}
