package bns.thread.火气功;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("火气功");
		long preTime = System.currentTimeMillis();
		mousePress(BnsConst.MOUSE_RIGHT, 500);
		mousePress(BnsConst.MOUSE_LEFT, 500);
		log.debug("用时="+(System.currentTimeMillis() - preTime));
		
		if (isColorOkCached("双龙破") || isColorOkCached("炎龙破")) {
			keyPress("F");
		} else {
			keyPress("2");
		}
		
		return true;
	}
}
