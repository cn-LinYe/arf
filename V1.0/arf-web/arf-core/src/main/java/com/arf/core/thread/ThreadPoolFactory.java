package com.arf.core.thread;

import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池工厂方法
 * @author Caolibao
 * 2016年6月15日 下午9:26:27
 *
 */
public class ThreadPoolFactory {
	protected final static Logger log = LoggerFactory.getLogger(ThreadPoolFactory.class);
	// 线程池对象
	private static ThreadPoolExecutor pool;

	// 单例对象
	private static ThreadPoolFactory factory;

	// 池中所保存的线程数，包括空闲线程。
	private static int corePoolSize = 30;

	// 池中允许的最大线程数。
	private static int maximumPoolSize = 300;

	// 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
	private static long keepAliveTime = 60 * 3;

	/**
	 * 私有构造函数
	 */
	private ThreadPoolFactory() {
	}

	/**
	 * 获取工厂对象
	 * 
	 * @param config
	 * @return
	 */
	public synchronized static ThreadPoolFactory getInstance() {
		if (factory == null) {
			factory = new ThreadPoolFactory();
		}

		if (pool == null) {
			pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
					new SynchronousQueue<Runnable>(), new PoolRejectedExecutionHandler());
		}
		return factory;
	}

	/**
	 * 添加线程池任务
	 * 
	 * @param run
	 */
	public synchronized void addTask(Runnable run) {
		pool.execute(run);
	}

	/**
	 * 添加线程池任务
	 * 
	 * @param runs
	 */
	public synchronized void addTask(List<Runnable> runs) {
		if (runs != null) {
			for (Runnable r : runs) {
				this.addTask(r);
			}
		}
	}

	/**
	 * 关闭线程池
	 */
	public void closePool() {
		pool.shutdown();
	}
}