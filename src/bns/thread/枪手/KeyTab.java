package bns.thread.枪手;

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
		
		int cnt = 0;
		
		while (cnt < 3) {
			if (isColorOK("TAB")) {
				Thread.sleep(500);
				keyPress("TAB");
				long nowTime = System.currentTimeMillis();
				while (System.currentTimeMillis() - nowTime <= 8000 && !isPause) {
					if (isColorOK("F")) {
						keyPress("F");
					} else {
						mousePress(BnsConst.MOUSE_LEFT);
					}
				}
				
				return false;
			}
			
			cnt++;
		}
		
		keyPress("TAB");
		
		return false;
	}
}
