package bns.thread.枪手;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
//	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	private long preLBTime = 0l;
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		//BOSS
		if (fm.getCase() == 2) {
			if (isFirstRun) {
				isFirstRun = false;
				if (isColorOK("C")) keyPress("C");//逆流
			} else {
				if (isColorOkCached("C")) keyPress("C");//逆流
			}
			
			if (isColorOkCached("V")) {
				keyPress("V", 150);//斩杀
			}
			
		}
		
		if (isColorOkCached("狂暴")) {
			if (isColorOkCached("TAB")) {
				keyPress("TAB");
				for (int i = 0; i < 200; i++) {
					mousePress(BnsConst.MOUSE_RIGHT, 20, 20, true);
				}
			}
			
		}
		
		//妄想
		mousePress(BnsConst.MOUSE_RIGHT);
		
		if (isColorOkCached("F")) {
			keyPress("F");
		} else if (isColorOkCached("3")) {
			for (int i = 0; i < 3; i++)
			keyPress("3");//弱点射击
		} else if (isColorOkCached("4")) {
			keyPress("4");//投放
		}
		
		//自动回内(2内以下)
		if (isColorOkCached("无内力")) {
			mousePress(BnsConst.MOUSE_LEFT);
		}
		
		return true;
	}
}
