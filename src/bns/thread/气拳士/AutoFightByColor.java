package bns.thread.气拳士;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		if (isColorOkCached("拳姿态")) {
			if (isColorOkCached("铁山靠") || isColorOkCached("迅疾脚")) {
				keyPress("F");
			} else if (isColorOkCached("寒气风暴")) {
				keyPress("V");
			} else if (isColorOkCached("迅猛冲击")) {
				keyPress("2");
			}
			
			//右臂拳
			mousePress(BnsConst.MOUSE_RIGHT);
			
		} else {
			if (isColorOkCached("寒冰掌")) {
				mousePress(BnsConst.MOUSE_LEFT);
			}
			
			if (isColorOkCached("冰河掌")) {
				mousePress(BnsConst.MOUSE_RIGHT);
			}
		}
		
		return true;
	}
}
