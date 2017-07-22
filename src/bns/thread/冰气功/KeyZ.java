package bns.thread.冰气功;

import bns.thread.KeyThreadAbstract;

public class KeyZ extends KeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KeyZ.class);
	
	public KeyZ() {
		super("Z");
		this.isContinueFight = true;
		this.isSubThread = true;
	}
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("KeyZ");
		
		KeyCommon.doKeyZ(this);

		return false;
	}
}
