package bns.thread.枪手;

import bns.BnsConst;
import bns.thread.KeyThreadAbstract;

class KeyCommon {
	
	/**
	 * 执行绝杀
	 */
	public static void doJuesha(KeyThreadAbstract th) throws InterruptedException, Exception {
		int cnt = 0;
		
		while (cnt < 3) {
			if (th.isColorOK("TAB")) {
				th.keyPress("TAB");
				long nowTime = System.currentTimeMillis();
				while (System.currentTimeMillis() - nowTime <= 8000 && !th.isPause) {
					th.mousePress(BnsConst.MOUSE_RIGHT);
				}
				
				return;
			}
			
			cnt++;
		}
	}
}
