package bns.thread.召唤士;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	/** 常春藤*/
	private long preTimeChangchunteng = 0l;
	/** 牵牛花*/
	private long preTimeQianniuhua = 0l;
//	/** 枫叶飘结束时间*/
//	private long endTimeFengyepiao = 0l;
	/** 下一次飞栗球时间*/
	private long nextFeileiTime = 0l;
	
	private boolean isSecondFeilei = false;
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("召唤");
		
		//起手
		if (isFirstRun) {
			if (isColorOK("常春藤")) keyPress("1", 600);//常春藤
			if (isColorOkCached("荆棘藤")) keyPress("2");//荆棘藤
			nextFeileiTime = 0;
			isFirstRun = false;
			isSecondFeilei = false;
		}
		
		if (fm.getCase() != BnsConst.CASE.小怪) {
			if (isColorOkCached("灵核") && isColorOkCached("灵")) {
				keyPress("8", BnsConst.RELEASE_DELAY, BnsConst.KEY_DEFAULT_SLEEP, false);
			}
		}
		
		if (isColorOkCached("板栗球")) {
			if (isSecondFeilei && System.currentTimeMillis() >= nextFeileiTime ) {
				for (int i = 0; i < 8; i++) keyPress("F", 50);
				nextFeileiTime = System.currentTimeMillis() + 1000;
				isSecondFeilei = false;
			} else {
				isSecondFeilei = true;
			}
		}
		
		//卡刀
		mousePress(BnsConst.MOUSE_LEFT, 50);
		mousePress(BnsConst.MOUSE_RIGHT, 50);
		
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
		
		//========================= 猫控制 ============================
		if (fm.getCase() == BnsConst.CASE.单人) {
			if (isColorOkCached("擒拿")) {
				keyPress("TAB");
			}
		}
		
		return true;
	}
}
