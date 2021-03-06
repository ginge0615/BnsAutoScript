package bns.thread.火气功;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	private int cntFire = 0;
	
	private boolean isOkX;
	
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
		} else if (cntFire >= 5) {
			mousePress(BnsConst.MOUSE_RIGHT, 500);
			mousePress(BnsConst.MOUSE_LEFT, 500);
			isOkX = isColorOkCached("火莲掌");	
			keyPress("1");	
			cntFire = 0;
			
			if (fm.getCase() != BnsConst.CASE.小怪 && isOkX) {
				mousePress(BnsConst.MOUSE_LEFT, 500);
				keyPress("X", 1000);
				keyPress("X");
			}
		} else {
			mousePress(BnsConst.MOUSE_RIGHT);
			mousePress(BnsConst.MOUSE_LEFT);		
			keyPress("2");
			cntFire++;
		}
		
		return true;
	}
}
