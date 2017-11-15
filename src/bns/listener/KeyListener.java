package bns.listener;

import org.apache.log4j.Logger;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import bns.BnsConst;
import bns.BnsFrame;
import bns.BnsRobot;
import bns.thread.KeyThreadBase;

public class KeyListener implements NativeKeyListener {
	
	private NativeKeyEvent preEvent;
	private BnsFrame frame = null;
	private BnsRobot r = null;
	
	private Logger logger = Logger.getLogger(KeyListener.class );
	
	public KeyListener(BnsFrame frame) {
		this.frame = frame;
		try {
			r = new BnsRobot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void nativeKeyPressed(NativeKeyEvent e) {
		logger.debug(e.paramString());
		logger.debug("Modifiers=" + e.getModifiers() + "  code=" + e.getKeyCode());
		
//		//Off的状态下连续按2次Q开始战斗
//		if (e.getKeyCode() == NativeKeyEvent.VC_Q && 
//				preEvent != null && (preEvent.getKeyCode() == NativeKeyEvent.VC_Q) ) {
//			if (frame.getStatus() == BnsFrame.STATUS_OFF) {
//				frame.getBnsHotkeyListener().doOn();
//				frame.getBnsHotkeyListener().startFight();
//			}
//			return;
//		}
		
		if (frame.getStatus() == BnsFrame.STATUS_OFF) return;
		
		if (e.getKeyCode() == NativeKeyEvent.VC_ENTER || 
			(e.getModifiers()== 1 && e.getKeyCode() == NativeKeyEvent.VC_1)	) {
			frame.getBnsHotkeyListener().doOff();
			return;
		}
		
		//除了~, ESC, UP, SPACE 按键外，如果正在奔跑按其他键则暂停奔跑。
		if (e.getKeyCode() != NativeKeyEvent.VC_BACKQUOTE && 
				e.getKeyCode() != NativeKeyEvent.VC_ESCAPE &&  
				e.getKeyCode() != NativeKeyEvent.VC_UP && 
				e.getKeyCode() != NativeKeyEvent.VC_SPACE) {
			
			KeyThreadBase th = frame.getBnsThreadManager().getRuningThread();
			
			if (th != null && th.isRun() &&  th.getIndentity() == BnsConst.LISTEN_ID_RUN) {
				th.doPause();
				return;
			}
		}
		
		//召唤枪手专用
		if (frame.getCareer().equals(BnsConst.CAREER.召唤士) || frame.getCareer().equals(BnsConst.CAREER.火枪手)) {
			// E貓
			if ((e.getKeyCode() == NativeKeyEvent.VC_E) && preEvent != null
					&& (preEvent.getKeyCode() == NativeKeyEvent.VC_E)) {

				try {
					r.keyPressAndRelease("E", BnsConst.RELEASE_DELAY, 0, true);
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		}
	}	
	
	public void nativeKeyReleased(NativeKeyEvent e) {
		preEvent = e;
	}
	
	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}
}
