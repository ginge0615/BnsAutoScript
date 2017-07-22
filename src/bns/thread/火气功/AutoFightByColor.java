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
		
//		long nowTime = 0;
//		
//		nowTime = System.currentTimeMillis();
		
		if (isColorOkCached("MOUSE_RIGHT")) {
			mousePress(BnsConst.MOUSE_RIGHT);
		}
		
		if (isColorOkCached("MOUSE_LEFT")) {
			mousePress(BnsConst.MOUSE_LEFT);
		}
		
		if (isColorOkCached("双龙破") || isColorOkCached("炎龙破")) {
			keyPress("F");
		} else if (isColorOkCached("爆裂炎炮") || isColorOkCached("炎龙啸")) {
			keyPress("2");
		}
		
		
		
		return true;
	}
}
