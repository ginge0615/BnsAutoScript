package bns.thread.召唤士;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	private boolean isAutoQ = false;
	private long startFightTime = 0l;
	private long preTabTime = 0l;
	
	/** 常春藤*/
	private long preTimeChangchunteng = 0l;
//	/** 飞栗球*/
//	private long preTimeFeiliqiu = 0l;
	/** 牵牛花*/
	private long preTimeQianniuhua = 0l;
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("召唤");
		
		//起手
		if (isFirstRun) {
			isFirstRun = false;
			isAutoQ = false;
			startFightTime = System.currentTimeMillis();
			
//			if (fm.getCase() != 1) {
				if (isColorOK("投掷花粉")) keyPress("3", 600);//花粉
				if (isColorOkCached("常春藤")) keyPress("1", 600);//常春藤
				if (fm.getCase() != 3 && isColorOkCached("荆棘藤")) keyPress("2");//荆棘藤
//			}
		}
		 
		//卡刀
		mousePress(BnsConst.MOUSE_LEFT, 50);
		mousePress(BnsConst.MOUSE_RIGHT, 50);
		
		//小怪:手动擒拿，自动花粉，手动扣猫，自动荆棘藤
		//Boss:自动擒拿，自动花粉，自动扣猫，自动荆棘藤
		//监狱:自动擒拿，自动花粉，自动扣猫，手动荆棘藤
		//PVP:自动擒拿，自动花粉，手动扣猫，自动荆棘藤
		if (fm.getCase() != 3 && isColorOkCached("荆棘藤")) {
			keyPress("2");
		} 
		
		else if (isColorOkCached("投掷花粉") && !isColorOkCached("大向日葵")) {
			keyPress("3");
		}
		
		else if (isColorOkCached("常春藤") && System.currentTimeMillis() - preTimeQianniuhua >= 9500) {
			preTimeChangchunteng = System.currentTimeMillis();
			keyPress("1");
		}
		
		else if (isColorOkCached("板栗球") && System.currentTimeMillis() - preTimeQianniuhua >= 5500) {
			keyPress("F");
		}
		
		else if (isColorOkCached("牵牛花") && System.currentTimeMillis() - preTimeChangchunteng >= 14500) {
			preTimeQianniuhua = System.currentTimeMillis();
			keyPress("F");
		}
		
		//========================= 猫控制 ============================
		if (fm.getCase() != 1 ) {
			if (isColorOkCached("擒拿")) {
				preTabTime = System.currentTimeMillis();
				keyPress("TAB");
			}  else if (fm.getCase() != 4 && isAutoQ && (System.currentTimeMillis() - preTabTime >= 4000) && isColorOkCached("畏缩")) {
				keyPress("Q");
			}
		}
		
		if (!isAutoQ && System.currentTimeMillis() - startFightTime >= 4000) {
			isAutoQ = true;
		}
		
		return true;
	}
}
