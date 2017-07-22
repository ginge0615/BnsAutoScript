package bns.thread.召唤士;

import bns.BnsKeyAgent;
import bns.thread.KeyThreadAbstract;

public class KeyMouseRight extends KeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KeyMouseRight.class);
	
	public KeyMouseRight() {
		super(BnsKeyAgent.getInstance().getAgentKeyText("MOUSE_LISTENER_RIGHT"));
		this.isContinueFight = true;
		this.isSubThread = true;
	}
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("KeyMouseRight");
		
		int cnt = 0;
		
		//蒲公英
		while (cnt <= 6) {
			if (isColorOK("蒲公英")) {
				keyPress("4");
			}
			
			doSleep(100);
			cnt++;
		}
		
		return false;
	}
}
