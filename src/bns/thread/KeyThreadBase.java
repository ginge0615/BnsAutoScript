package bns.thread;

import org.apache.log4j.Logger;

import bns.BnsFrame;
import bns.BnsRobot;

public abstract class KeyThreadBase extends Thread {
	protected boolean isFirstRun = true;
	protected int indentity = 0;
	protected String key;
	protected boolean isContinueFight = false;
	public boolean isPause = true;
	protected boolean isOver = false;
	protected boolean isSubThread = false;
	protected boolean isBreakRuning = true;
	protected BnsFrame fm = BnsFrame.getInstance();
	protected BnsRobot r = null;

	private Logger logger = Logger.getLogger(KeyThreadBase.class );
	
	public KeyThreadBase() {
		try {
			r = new BnsRobot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public KeyThreadBase(String key) {
		this();
		this.key = key;
	}
	
	/**
	 * 线程是否运行
	 * @return
	 */
	public boolean isRun() {
		return !isPause;
	}
	
	/**
	 * 暂停
	 */
	public synchronized void doPause() {
		isPause = true;
		logger.debug(" doPause=" + isPause);
		this.interrupt();
	}
	
	/**
	 * 结束
	 */
	public synchronized void doOver() {
		isOver = true;
		this.interrupt();
	}
	
	/**
	 * 开始
	 */
	public synchronized void doStart() {
		if (isFirstRun) {
			isFirstRun = false;
			isPause = false;
			this.start();
		} else if (isPause) {
			isPause = false;
			this.notify();
		}
	}
	
	/**
	 * 等待
	 */
	public synchronized void doWait() {
		isPause = true;

		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
			if (!isOver) {
				doWait();
			}
		}
	}


	public int getIndentity() {
		return indentity;
	}

	public void setIndentity(int indentity) {
		this.indentity = indentity;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public boolean isSubThread() {
		return isSubThread;
	}

	public void setSubThread(boolean isSubThread) {
		this.isSubThread = isSubThread;
	}

	public boolean isBreakRuning() {
		return isBreakRuning;
	}

	public void setBreakRuning(boolean isBreakRuning) {
		this.isBreakRuning = isBreakRuning;
	}
	
	/**
	 * 休眠
	 * @param time
	 * @throws InterruptedException
	 */
	public void doSleep(long time) throws InterruptedException {
		if (time > 0) {
			Thread.sleep(time);
		}
	}
}
