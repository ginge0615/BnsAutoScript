package bns.thread.冰气功;

import bns.BnsKeyAgent;
import bns.thread.KeyThreadAbstract;

public class KeyForward extends KeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KeyForward.class);
	
	public KeyForward() {
		super(BnsKeyAgent.getInstance().getAgentKeyText("MOUSE_LISTENER_BACK"));
	}
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("KeyBack");
		
		//QE
		keyPress("E", 10);
		keyPress("Q", 10);
		
		return false;
	}
}
