package bns.thread.召唤士;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("召唤 战斗主线程运行ing");
		
		//卡刀
		mousePress(BnsConst.MOUSE_RIGHT);
		mousePress(BnsConst.MOUSE_LEFT);
		
		if (isColorOkCached("荆棘藤")) {
			keyPress("2");
		}
		
		mousePress(BnsConst.MOUSE_RIGHT);
		
		return true;
	}
}
