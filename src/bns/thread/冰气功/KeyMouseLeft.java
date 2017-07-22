package bns.thread.冰气功;

import bns.BnsKeyAgent;
import bns.thread.KeyThreadAbstract;

public class KeyMouseLeft extends KeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KeyMouseLeft.class);
	
	public KeyMouseLeft() {
		super(BnsKeyAgent.getInstance().getAgentKeyText("MOUSE_LISTENER_LEFT"));
		this.isContinueFight = true;
		this.isSubThread = true;
	}
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("KeyMouseLeft");
		
		KeyCommon.doFire(this);

		return false;
	}
}
