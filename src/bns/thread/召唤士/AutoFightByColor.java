package bns.thread.召唤士;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	/** 常春藤*/
	private long preTimeChangchunteng = 0l;
	/** 牵牛花*/
	private long preTimeQianniuhua = 0l;
	/** 枫叶飘结束时间*/
	private long endTimeFengyepiao = 0l;
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("召唤");
		
		//起手
		if (isFirstRun) {
			if (fm.getCase() != BnsConst.CASE.小怪) {
//				if (isColorOK("投掷花粉")) keyPress("3", 600);//花粉
				if (isColorOkCached("灵")) keyPress("8", BnsConst.RELEASE_DELAY, BnsConst.KEY_DEFAULT_SLEEP, false);
				if (isColorOkCached("常春藤")) keyPress("1", 600);//常春藤
				if (isColorOkCached("荆棘藤")) keyPress("2");//荆棘藤
			}
		}
		
		//卡刀
		mousePress(BnsConst.MOUSE_LEFT, 50);
		mousePress(BnsConst.MOUSE_RIGHT, 50);
		
		long systime = System.currentTimeMillis();
		
		if (isColorOkCached("板栗球") && (systime - endTimeFengyepiao >= 2000)) {
			while (isColorOkCached("板栗球")) {
				keyPress("F");
			}
			
			endTimeFengyepiao = systime + 4000;

		} else if (systime <= endTimeFengyepiao ) {
			//枫叶飘期间
//			if (isColorOkCached("荆棘藤")) {
				keyPress("2");
//			}
			
		} else {
			if (isColorOkCached("荆棘藤")) {
				keyPress("2");
			}
			else if (isColorOkCached("投掷花粉") && !isColorOkCached("大向日葵")) {
				keyPress("3");
			}

			else if (isColorOkCached("常春藤") && System.currentTimeMillis() - preTimeQianniuhua >= 9500) {
				preTimeChangchunteng = System.currentTimeMillis();
				keyPress("1");
			}
			
			else if (isColorOkCached("牵牛花") && System.currentTimeMillis() - preTimeChangchunteng >= 14500) {
				preTimeQianniuhua = System.currentTimeMillis();
				keyPress("F");
			}			
		}
		
		//========================= 猫控制 ============================
		if (fm.getCase() != BnsConst.CASE.小怪) {
			if (isColorOkCached("灵")) keyPress("8", BnsConst.RELEASE_DELAY, BnsConst.KEY_DEFAULT_SLEEP, false);
			
			if (isColorOkCached("擒拿")) {
				keyPress("TAB");
			}
		}
		
		return true;
	}
}
