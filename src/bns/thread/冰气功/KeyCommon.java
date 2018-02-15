package bns.thread.冰气功;

import bns.BnsConst;
import bns.thread.KeyThreadAbstract;

class KeyCommon {
	
	/**
	 * 执行雪冰掌
	 */
	public static void doKeyZ(KeyThreadAbstract th) throws InterruptedException, Exception {
		int cnt = 0;
		
		while (cnt < 3) {
			th.mousePress(BnsConst.MOUSE_RIGHT, 600);
			
			if (th.isColorOK("雪冰掌")) {
				th.keyPress("Z");
				th.mousePress(BnsConst.MOUSE_RIGHT, 300);
				th.keyPress("F");
				long nowTime = System.currentTimeMillis();
				while (System.currentTimeMillis() - nowTime <= 9500 && th.isRun()) {
					th.keyPress("2");
					th.doSleep(50);
				}
				
				return;
			}
			
			cnt++;
		}
	}
	
	/**
	 * 执行火系一套
	 */
	public static void doFire(KeyThreadAbstract th) throws InterruptedException, Exception {
		int cnt = 0;
		
		while (cnt < 3) {
			th.mousePress(BnsConst.MOUSE_LEFT, 400);
			
			if (th.isColorOK("流星指")) {
				//th.mousePress(BnsConst.MOUSE_LEFT, 600);
				th.keyPress("V");
				return;
			}
			
			cnt++;
		}
	}
}
