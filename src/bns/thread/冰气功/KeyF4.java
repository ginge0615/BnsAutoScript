package bns.thread.冰气功;

import bns.BnsConst;
import bns.thread.KeyThreadAbstract;

/**
 * 重力掌
 * @author jianghb
 *
 */
public class KeyF4 extends KeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KeyF4.class);
	
	public KeyF4() {
		super("F4");
		this.isContinueFight = true;
	}
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("F4");
		
		//重力掌
		keyPress("4");

		int cnt = 0;
		
		while (cnt < 4) {
			if (isColorOK("流星指")) {
				keyPress("V");
				break;
			} else {
				mousePress(BnsConst.MOUSE_LEFT);
			}
			
			cnt++;
		}

		return false;
	}
}
