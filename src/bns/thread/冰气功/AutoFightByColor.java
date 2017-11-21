package bns.thread.冰气功;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	private long preMouseLeftTime;
	
	/**
	 * 执行自定义技能
	 * 
	 * @return true:循环执行 false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("气功");
		
		//卡刀
		//冰白寒炮
		keyPress("2", 50);
		// 玄冰掌
		mousePress(BnsConst.MOUSE_RIGHT, 300);
		
		if ((System.currentTimeMillis()) - preMouseLeftTime >= 6000) {
			mousePress(BnsConst.MOUSE_LEFT);
			preMouseLeftTime = System.currentTimeMillis();
		}
		
		if (isColorOkCached("双龙破") || isColorOkCached("冰龙破")) {
			doSleep(BnsConst.KEY_DEFAULT_SLEEP);
			keyPress("F");
		} 

		return true;
	}
}
