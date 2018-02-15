package bns.thread;

import bns.BnsFrame;
import bns.BnsUtil;

public abstract class AutoFightKeyThreadAbstract extends KeyThreadAbstract {
//	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightKeyThreadAbstract.class);
	private PixColorThread thPixColor = null;

	public AutoFightKeyThreadAbstract() {
		super(BnsUtil.PROP_CONFIG.getProperty("KEY_START"));
	}
	
	/**
	 * 暂停
	 */
	public synchronized void doPause() {
		fm.setStatus(BnsFrame.STATUS_NO_FIGHTING);
		super.doPause();
	}
	
	/**
	 * 开始
	 */
	public synchronized void doStart() {
		fm.setStatus(BnsFrame.STATUS_FIGHTING);
		super.doStart();
	}
	
	public PixColorThread getThPixColor() {
		return thPixColor;
	}

	public void setThPixColor(PixColorThread thPixColor) {
		this.thPixColor = thPixColor;
	}
	
	/**
	 * 取色（缓存方式）
	 * @param colorName
	 * @return
	 */
	protected boolean isColorOkCached(String colorName) {
		if (thPixColor != null) {
			return thPixColor.isColorOK(colorName);
		}
		
		return false;
	}
}
