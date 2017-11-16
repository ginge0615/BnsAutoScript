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
		
		if (isColorOkCached("双龙破") || isColorOkCached("炎龙破")) {
			mousePress(BnsConst.MOUSE_RIGHT);
			mousePress(BnsConst.MOUSE_LEFT);
			keyPress("F");
		} else {
			mousePress(BnsConst.MOUSE_RIGHT);
			mousePress(BnsConst.MOUSE_LEFT);
			keyPress("2");
			
			if (fm.getCase() != 3) {
				mousePress(BnsConst.MOUSE_RIGHT);
				mousePress(BnsConst.MOUSE_LEFT);
				keyPress("2");
				mousePress(BnsConst.MOUSE_RIGHT);
				mousePress(BnsConst.MOUSE_LEFT);
				keyPress("1");
				
				if (isColorOkCached("火莲掌即时")) {
					keyPress("X");
				}				
			}
		}
		
		return true;
	}
}
