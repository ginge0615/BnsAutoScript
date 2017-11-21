package bns.thread.暗枪手;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		
		if (isColorOkCached("F")) {
			keyPress("F");
		} else if (isColorOkCached("3")) {
			for (int i = 0; i < 3; i++)
			keyPress("3");//弱点射击
		} else if (isColorOkCached("4")) {
			keyPress("4");//冥炎破(4)
		} else {
			//冥炎弹(右键)
			mousePress(BnsConst.MOUSE_RIGHT);
		}
		
		//自动回内(2内以下)
		if (isColorOkCached("无内力")) {
			mousePress(BnsConst.MOUSE_LEFT);
		}
		
		return true;
	}
}
