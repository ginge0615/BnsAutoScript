package bns.thread.火气功;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	private int cntFire = 0;
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("火气功");
		
		if (isColorOkCached("双龙破") || isColorOkCached("炎龙破")) {
			mousePress(BnsConst.MOUSE_RIGHT, 500);
			mousePress(BnsConst.MOUSE_LEFT, 500);			
			keyPress("F");
			cntFire = 5;
		} else if (cntFire >= 5) {
			mousePress(BnsConst.MOUSE_RIGHT, 500);
			mousePress(BnsConst.MOUSE_LEFT, 500);	
			keyPress("1");	
			
			if (isColorOkCached("火莲掌")) {
				doSleep(800);
				keyPress("X");
				doSleep(800);
				keyPress("X");
			}
			cntFire = 0;	
		} else {
			mousePress(BnsConst.MOUSE_RIGHT);
			mousePress(BnsConst.MOUSE_LEFT);	
			keyPress("2");
			cntFire++;
		}
		
		return true;
	}
}
