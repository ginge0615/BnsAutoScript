package bns.thread.冰气功;

import bns.BnsConst;
import bns.thread.KeyThreadAbstract;

/**
 * 双击倒
 * @author jianghb
 *
 */
public class KeyF2 extends KeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KeyF2.class);
	
	public KeyF2() {
		super("F2");
	}
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		log.debug("F2");

		int cnt = 0;
		
		while (cnt < 4) {
			mousePress(BnsConst.MOUSE_LEFT, 600);
			
			if (isColorOK("炎爆")) {
				keyPress("Z");
				mousePress(BnsConst.MOUSE_LEFT, 300);
				mousePress(BnsConst.MOUSE_LEFT, 300);
				keyPress("Z");
				break;
			} 
			
			cnt++;
		}

		return false;
	}
}
