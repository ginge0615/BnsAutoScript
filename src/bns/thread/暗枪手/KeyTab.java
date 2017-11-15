package bns.thread.暗枪手;

import bns.BnsConst;
import bns.thread.KeyThreadAbstract;

public class KeyTab extends KeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KeyTab.class);
	
	public KeyTab() {
		super("TAB");
		this.isContinueFight = true;
	}
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("KeyTab");
		
		if (isColorOK("绝杀")) {
			keyPress("TAB");
			long nowTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - nowTime <= 8000 && !isPause) {
				mousePress(BnsConst.MOUSE_RIGHT);
			}

		} else {
			keyPress("TAB");
		}

		return false;
	}
}
