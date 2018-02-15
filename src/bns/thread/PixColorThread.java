package bns.thread;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import bns.BnsUtil;
import bns.bean.ColorBean;
import bns.properties.BnsPropertiesColor;

public class PixColorThread extends KeyThreadAbstract{
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(PixColorThread.class);
	
	private Map<String, Boolean> mapColorStatus;
	private Map<String, ColorBean[][]> mapColor;
	private boolean isReady = false;
	
	public PixColorThread() {
		super();
		mapColorStatus = new HashMap<String, Boolean>();
		mapColor = BnsPropertiesColor.getInstance().getColorMap();
	}
	
	protected boolean runSkill() throws InterruptedException, Exception{
		log.debug("取色线程运行ing");
		
		Iterator<String> iter = mapColor.keySet().iterator();
		String key;
		
		while (iter.hasNext()) {
			key = iter.next();
			mapColorStatus.put(key, BnsUtil.isColorOK(key, r));
		}
		
		isReady = true;
		
		this.doSleep(50);
		
		return true;
	}
	
	/**
	 * 取色check（缓存）
	 * @param colorName
	 * @return
	 */
	public boolean isColorOK(String colorName) {
		//如果不存在
		if (!mapColorStatus.containsKey(colorName)) {
			return BnsUtil.isColorOK(colorName, r);
		}
		
		return mapColorStatus.get(colorName);
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
}
