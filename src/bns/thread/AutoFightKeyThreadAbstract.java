package bns.thread;

import bns.BnsFrame;
import bns.BnsUtil;

public abstract class AutoFightKeyThreadAbstract extends KeyThreadAbstract {
//	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightKeyThreadAbstract.class);
	PixColorThread thPix;
	protected boolean isFirstRun = true;
	
	public AutoFightKeyThreadAbstract() {
		super(BnsUtil.PROP_CONFIG.getProperty("KEY_START"));
		thPix = new PixColorThread();
	}
	
	/**
	 * 暂停
	 */
	public synchronized void doPause() {
		fm.setStatus(BnsFrame.STATUS_NO_FIGHTING);
		thPix.doPause();
		super.doPause();
		
	}
	
	/**
	 * 结束
	 */
	public synchronized void doOver() {
		thPix.doOver();
		super.doOver();
	}
	
	/**
	 * 开始
	 */
	public synchronized void doStart() {
		fm.setStatus(BnsFrame.STATUS_FIGHTING);
		isFirstRun = true;
		thPix.doStart();
		super.doStart();
	}
	
	/**
	 * 非即时取色Check
	 * @param key
	 * @return
	 */
	protected boolean isColorOkCached(String key) {
		return thPix.isColorOK(key);
	}
	
	protected void clearCachedColor(String key) {
		thPix.clearColor(key);
	}
}
