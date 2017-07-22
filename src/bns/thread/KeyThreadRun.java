package bns.thread;

import java.awt.event.KeyEvent;

import bns.BnsConst;
import bns.BnsFrame;
import bns.properties.BnsPropertiesConfig;

public class KeyThreadRun extends KeyThreadBase {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KeyThreadRun.class );
	
	private static BnsPropertiesConfig PropConf = BnsPropertiesConfig.getInstance();
	
	private static final int DB_SPACE = PropConf.getPropertyInt("DOUBLE_CLICK_SPACE");
	private static final int SLEEP = PropConf.getPropertyInt("RUN_SPACE");	
	private static final int KEY_CODE = KeyEvent.VK_UP;

	public KeyThreadRun() {
		super();
		this.indentity = BnsConst.LISTEN_ID_RUN;
	}	
	
	public synchronized void doStart() {
		this.fm.setStatus(BnsFrame.STATUS_RUNNING);
		if (isFirstRun) {
			isFirstRun = false;
			isPause = false;
			
			this.start();
		} else if (isPause) {
			isPause = false;
			this.notify();
		}
	}


	public void run() {		
		while (!isOver) {
			
				if (isPause) {
					try {
						if (r.isKeyPressing()) {
							r.keyRelease(KEY_CODE);
						}
					} catch (Exception e1) {
						log.error(e1);
					} finally {
						this.fm.setStatus(BnsFrame.STATUS_NO_FIGHTING);
						doWait();
						if (isOver) {
							return;
						}
					}
				}
				// 按键
				try {
					r.keyPress(KEY_CODE);
					doSleep(BnsConst.RELEASE_DELAY);
					r.keyRelease(KEY_CODE);
					doSleep(DB_SPACE);
					r.keyPress(KEY_CODE);
					
//					while (!isPause) {
//						if (BnsUtil.isColorOK("RUN_COLOR", r)) {
//							//继续奔跑
//							doSleep(SLEEP);
//						} else {
							//走一回再跑
							doSleep(SLEEP);
							r.keyRelease(KEY_CODE);
//							break;
//						}
//					}
					
				} catch (Exception e) {
					isPause = true;
				}
		}
	}
}
