package com.arf.core.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * 线程池异常处理类
 * 
 * @author
 * 
 */
public class PoolRejectedExecutionHandler implements RejectedExecutionHandler
{
    protected final static Logger log = Logger.getLogger(PoolRejectedExecutionHandler.class);
    /**  
     * @see java.util.concurrent.RejectedExecutionHandler#rejectedExecution(java.lang.Runnable, java.util.concurrent.ThreadPoolExecutor)    
     */
    //如果超过最大线程数，则启用新的线程启动
    public void rejectedExecution(Runnable task, ThreadPoolExecutor executor)
    {
        log.error("=============线程池最大容量使用完毕，使用原始线程执行=============");
        // 执行失败任务
        new Thread(task, "exception by pool").start();
        // 打印线程池的对象
    }
}