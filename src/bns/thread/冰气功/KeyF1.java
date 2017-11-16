package bns.thread.冰气功;

import bns.BnsConst;
import bns.thread.KeyThreadAbstract;

public class KeyF1 extends KeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KeyF1.class);
	
	public KeyF1() {
		super("F1");
		isContinueFight = true;
	}
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("F1");

		if (isColorOK("天龙炮")) {
			keyPress("G", BnsConst.RELEASE_DELAY, 4000, false);
		} else {
			//切火姿态
			keyPress("Q");
			
			keyPress("X", BnsConst.RELEASE_DELAY, 4000, false);
			
			//执行火系一套	
			KeyCommon.doFire(this);
			//切冰姿态
			mousePress(BnsConst.MOUSE_RIGHT);
		}
		
		
		//执行雪冰掌
		KeyCommon.doKeyZ(this);
		
		// 玄冰掌
		mousePress(BnsConst.MOUSE_RIGHT);

		return false;
	}
}
