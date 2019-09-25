
package com.rongdu.loans.service.queue;

/**
 * 采用即时阻塞读取Queue中消息策略的Consumer.
 */
public abstract class BlockingConsumer extends QueueConsumer {

	/**
	 * 线程执行函数,阻塞获取消息并调用processMessage()进行处理.
	 */
	@Override
	public void run() {
		//循环阻塞获取消息直到线程被中断.
		try {
			while (!Thread.currentThread().isInterrupted()) {//如果线程没有被中断就继续运行
				Object message = queue.take();
				processMessage(message);
			}
		} catch (InterruptedException e) {
			// Ignore.
		} finally {
			//退出线程前调用清理函数.
			clean();
		}
	}

	/**
	 * 消息处理函数.
	 */
	protected abstract void processMessage(Object message);

	/**
	 * 退出清理函数.
	 */
	protected abstract void clean();
}
