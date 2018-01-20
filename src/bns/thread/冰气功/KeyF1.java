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
			keyPress("Q", 500);
			
			boolean isOkX = isColorOK("火莲掌");			
			if (isOkX) {
				keyPress("X", 3000);
				mousePress(BnsConst.MOUSE_LEFT, 500);
			}
			
			if (isColorOK("流星指")) {
				mousePress(BnsConst.MOUSE_LEFT, 500);
				keyPress("V");
			}
			
			if (isOkX) {
				mousePress(BnsConst.MOUSE_LEFT, 500);
				keyPress("X", 500);
			}
		}
		
		//执行雪冰掌
		KeyCommon.doKeyZ(this);

		return false;
	}
}
