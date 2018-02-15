package bns.thread.召唤士;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightHelp extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	/** 常春藤*/
	private long preTimeChangchunteng = 0l;
	/** 牵牛花*/
	private long preTimeQianniuhua = 0l;
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("召唤 辅助线程运行ing");
		
		//起手
		if (isFirstLoop) {
			if (isColorOK("常春藤")) keyPress("1");//常春藤
		}
		
		if (fm.getCase() != BnsConst.CASE.小怪) {
			if (isColorOkCached("灵核") && isColorOkCached("灵")) {
				keyPress("8", BnsConst.RELEASE_DELAY, BnsConst.KEY_DEFAULT_SLEEP, false);
			}
		}
				
		if (isColorOkCached("板栗球")) {
			this.doSleep(750);
			for (int i = 0; i < 8; i++) keyPress("F", 50);
				
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
		
		this.doSleep(50);
		
		return true;
	}
}
