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
				if (isColorOK("C")) keyPress("C");//流星坠
			} else {
				if (isColorOkCached("C")) keyPress("C");//流星坠
			}
			
			if (isColorOkCached("V")) {
				keyPress("V", 150);//斩杀
			}
			
		}
		
		if (isColorOkCached("F")) {
			keyPress("F");
		} else {
			//爆炎弹
			mousePress(BnsConst.MOUSE_RIGHT);
		}
		
		if (isColorOkCached("3")) {
			keyPress("3");//弱点射击
		} else
		if (isColorOkCached("4")) {
			for (int i = 0; i < 3; i++) {
				keyPress("4");//火力时刻
				keyPress("F");
			}
		}
		
		//自动回内(2内以下)
		if (System.currentTimeMillis() - preLBTime >= 1000 && isColorOkCached("无内力")) {
			preLBTime = System.currentTimeMillis();
			mousePress(BnsConst.MOUSE_LEFT);
		}
		
		return true;
	}
}
