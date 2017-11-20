package bns.thread.咒术士;

import bns.BnsConst;
import bns.thread.AutoFightKeyThreadAbstract;

public class AutoFightByColor extends AutoFightKeyThreadAbstract {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(AutoFightByColor.class);
	/**
	 * 执行自定义技能
	 * @return true:循环执行   false:单次执行
	 */
	protected boolean runSkill() throws InterruptedException, Exception {
		if (isColorOkCached("超神")) {
			mousePress(BnsConst.MOUSE_RIGHT);//觉醒
			keyPress("4");       //黑炎龙
			this.clearCachedColor("超神");
			
		} else if (isColorOkCached("抓举")) {
			mousePress(BnsConst.MOUSE_LEFT);
			this.clearCachedColor("抓举");
		
		} else if (isColorOkCached("黑炎龙")) {
			log.debug("黑炎龙");
			keyPress("4");
			this.clearCachedColor("黑炎龙");
			
		} else if (isColorOkCached("破裂")) {
			log.debug("破裂");
			keyPress("F");
			this.clearCachedColor("破裂");
		
		} else if (isColorOkCached("修罗")) {
			log.debug("修罗");
			mousePress(BnsConst.MOUSE_RIGHT);
			this.clearCachedColor("修罗");
			
		} else if (isColorOK("掠夺")) {
			log.debug("掠夺");
			keyPress("F");
			this.clearCachedColor("掠夺");
			
		} else if (isColorOkCached("死灵降临即时")) {
			log.debug("死灵降临即时");
			keyPress("V");
			this.clearCachedColor("死灵降临即时");
		} else if (isColorOkCached("死灵降临")) {
			log.debug("死灵降临");
			keyPress("V");
			this.clearCachedColor("死灵降临");
			doSleep(800);
		} else if (isColorOkCached("夜叉")) {
			log.debug("夜叉");
			mousePress(BnsConst.MOUSE_RIGHT);
			this.clearCachedColor("夜叉");
		}	
		
		//自动回内(2内以下)
		if (isColorOkCached("真言")) {
			//真言
			mousePress(BnsConst.MOUSE_LEFT);
			this.clearCachedColor("真言");
		}
		
		return true;
	}
}
