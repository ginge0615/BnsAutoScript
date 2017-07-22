package bns.listener;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import bns.BnsConst;
import bns.BnsFrame;
import bns.BnsKeyAgent;
import bns.BnsRobot;

public class MouseListener implements NativeMouseListener{

	private static int keyCodeLeft = BnsKeyAgent.getInstance().getAgentKeyCode("MOUSE_LISTENER_LEFT");
	private static int keyCodeRight = BnsKeyAgent.getInstance().getAgentKeyCode("MOUSE_LISTENER_RIGHT");
	
	private Logger logger = Logger.getLogger(MouseListener.class );
	private BnsRobot r = null;
	private BnsFrame frame;
	
	public MouseListener(BnsFrame frame) {
		this.frame = frame;
		try {
			r = new BnsRobot();
			GlobalScreen.setEventDispatcher(new VoidDispatchService());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    private class VoidDispatchService extends AbstractExecutorService {
        private boolean running = false;

        public VoidDispatchService() {
            running = true;
        }

        public void shutdown() {
            running = false;
        }

        public List<Runnable> shutdownNow() {
            running = false;
            return new ArrayList<Runnable>(0);
        }

        public boolean isShutdown() {
            return !running;
        }

        public boolean isTerminated() {
            return !running;
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return true;
        }

        public void execute(Runnable r) {
            r.run();
        }
    }

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		/* Unimplemented */
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		logger.debug(arg0.paramString());

		try {

			switch (arg0.getButton()) {
			case 1:
//				if ((arg0.getModifiers() & NativeInputEvent.ALT_MASK) != 0 && frame.getStatus() != BnsFrame.STATUS_OFF) {
//					frame.getBnsHotkeyListener().doOff();
//				} else 
				if (frame.getStatus() == BnsFrame.STATUS_FIGHTING) {
					doConsumeEvent(arg0);
					r.keyPressAndRelease(keyCodeLeft, BnsConst.RELEASE_DELAY, BnsConst.KEY_DEFAULT_SLEEP);
				}
				break;
			case 2:
				if (frame.getStatus() == BnsFrame.STATUS_FIGHTING) {
					doConsumeEvent(arg0);
					r.keyPressAndRelease(keyCodeRight, BnsConst.RELEASE_DELAY, BnsConst.KEY_DEFAULT_SLEEP);
				}
				break;
			case 3:
				doConsumeEvent(arg0);
				//鼠标中键
				if (frame.getCase() == 1) {
					frame.setCase(2);
				} else {
					frame.setCase(1);
				}
				
				break;
			case 4:
				// SS
				doConsumeEvent(arg0);
				
				for (int i = 0; i < 6; i++) {
					r.keyPressAndRelease(KeyEvent.VK_S, BnsConst.RELEASE_DELAY, BnsConst.DB_SPACE);
				}
				
				break;
			case 5:
				doConsumeEvent(arg0);
				r.keyPressAndRelease(KeyEvent.VK_DELETE, BnsConst.RELEASE_DELAY, BnsConst.KEY_DEFAULT_SLEEP);
				break;
			default:
			}

		} catch ( InterruptedException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
//		preEvent = arg0;
		
		if (frame.getStatus() == BnsFrame.STATUS_FIGHTING) {
			doConsumeEvent(arg0);
		}
	}
	
	private void doConsumeEvent(NativeMouseEvent arg0) {
		try {
            Field f = NativeInputEvent.class.getDeclaredField("reserved");
            f.setAccessible(true);
            f.setShort(arg0, (short) 0x01);

        }
        catch (Exception ex) {
        	logger.error("", ex);
        }
	}

}
