package bns.thread.暗枪手;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	
	long preLBTime = 0l;
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		
		if (isColorOkCached("F")) {
			keyPress("F");
			this.clearCachedColor("F");
		} else if (isColorOkCached("3")) {
			for (int i = 0; i < 3; i++) {
				keyPress("3");//弱点射击				
			}
			this.clearCachedColor("3");
		} else if (isColorOkCached("4")) {
			keyPress("4");//冥炎破(4)
			this.clearCachedColor("4");
		} else {
			//冥炎弹(右键)
			mousePress(BnsConst.MOUSE_RIGHT);
		}
		
		//自动回内(2内以下)
		if (System.currentTimeMillis() - preLBTime >= 1000 && isColorOkCached("无内力")) {
			preLBTime = System.currentTimeMillis();
			mousePress(BnsConst.MOUSE_LEFT);
			this.clearCachedColor("无内力");
		}
		
		return true;
	}
}
