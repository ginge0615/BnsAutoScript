package bns.thread.火气功;

import bns.BnsConst;
import bns.thread.KeyThreadAbstract;

public class KeyF1 extends KeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KeyF1.class);
	
	public KeyF1() {
		super("F1");
		this.isContinueFight = true;
	}
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("KeyF1");
		boolean isOkX = false;
		
		if (isColorOK("天龙炮")) {
			keyPress("G", BnsConst.RELEASE_DELAY, 4000, false);
			mousePress(BnsConst.MOUSE_LEFT, 500);
			if (isColorOK("重力掌")) {
				keyPress("4",500);
				keyPress("4");				
			}
			
		} else {
			keyPress("Q", 500);
			
			isOkX = isColorOK("火莲掌");			
			if (isOkX) {
				keyPress("X", 3000);
				mousePress(BnsConst.MOUSE_LEFT, 500);
			}
			
			if (isColorOK("重力掌")) {
				keyPress("4",500);
				keyPress("4");				
			}
			
			if (isOkX) {
				mousePress(BnsConst.MOUSE_LEFT, 500);
				keyPress("X", 500);
			}
		}
		
		mousePress(BnsConst.MOUSE_LEFT, 500);
		if (isColorOK("流星指")) {
			mousePress(BnsConst.MOUSE_LEFT, 500);
			keyPress("V");
		}
		
		isOkX = isColorOK("火莲掌");	
		
		mousePress(BnsConst.MOUSE_RIGHT, 500);
		mousePress(BnsConst.MOUSE_LEFT, 500);
		keyPress("F");
		
		mousePress(BnsConst.MOUSE_RIGHT, 500);
		mousePress(BnsConst.MOUSE_LEFT, 500);
		keyPress("1");
		
		if (isOkX) {
			mousePress(BnsConst.MOUSE_LEFT, 500);
			keyPress("X", 700);
			keyPress("X", 500);
		}
		
		return false;
	}
}
