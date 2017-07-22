package bns;

import java.awt.AWTException;
import java.awt.Robot;

public class BnsRobot extends Robot {
	private boolean isKeyPressing;
	private boolean isMousePressing;
	private BnsKeyAgent keyAgent;
	
	public BnsRobot() throws AWTException {
		super();
		isKeyPressing = false;
		isMousePressing = false;
		keyAgent = BnsKeyAgent.getInstance();
	}
	
	@Override
	public void keyPress(int keycode) {
		super.keyPress(keycode);
		isKeyPressing = true;
	}

	@Override
	public void keyRelease(int keycode) {
		super.keyRelease(keycode);
		isKeyPressing = false;
	}
	
	@Override
	public void mousePress(int buttons) {
		super.mousePress(buttons);
		isMousePressing = true;
	}
	
	@Override
	public void mouseRelease(int buttons) {
		super.mouseRelease(buttons);
		isMousePressing = false;
	}

	public boolean isKeyPressing() {
		return isKeyPressing;
	}

	public boolean isMousePressing() {
		return isMousePressing;
	}
	
	/**
	 * 休眠
	 * @param time
	 * @throws InterruptedException
	 */
	public void sleep(int time) throws InterruptedException {
		super.delay(time);
	}
	
	/**
	 * 鼠标按键响应
	 * @param mouseName 按键名
	 * @param releaseDelay 按键释放迟延
	 * @param sleep 按键执行后睡眠时间
	 * @param isKeyAgent 按键代理
	 * @throws Exception
	 */
	public void mousePressAndRelease(String mouseName, int releaseDelay, int sleep, boolean isKeyAgent) throws InterruptedException, Exception{
		
		int keyCode = 0;
		
		try {
			if (isKeyAgent) {
				keyPressAndRelease(mouseName, releaseDelay, sleep, isKeyAgent);				
			} else {
				keyCode = keyAgent.getKeyCode(mouseName);
				mousePress(keyCode);
				sleep(releaseDelay);
				mouseRelease(keyCode);
				sleep(sleep);	
			}
		} catch (InterruptedException e) {	
			throw e;
		} finally {
			if (isMousePressing()) {
				mouseRelease(keyCode);
			}
		}
	}
	
	/**
	 * 按键响应
	 * @param keyName 按键名
	 * @param releaseDelay 按键释放迟延
	 * @param sleep 按键执行后睡眠时间
	 * @param isKeyAgent 按键代理
	 * @throws Exception
	 */
	public void keyPressAndRelease(String keyName, int releaseDelay, int sleep, boolean isKeyAgent) throws InterruptedException, Exception{
		int keyCode = 0;

		if (isKeyAgent) {
			// 按键代理
			keyCode = keyAgent.getAgentKeyCode(keyName);
		} else {
			keyCode = keyAgent.getKeyCode(keyName);
		}

		keyPressAndRelease(keyCode, releaseDelay, sleep);
	}
	
	/**
	 * 按键响应
	 * @param keyCode 按键Code
	 * @param releaseDelay 按键释放迟延
	 * @param sleep 按键执行后睡眠时间
	 * @throws Exception
	 */
	public void keyPressAndRelease(int keyCode, int releaseDelay, int sleep) throws InterruptedException, Exception{
		try {
			keyPress(keyCode);
			sleep(releaseDelay);
			keyRelease(keyCode);			
			sleep(sleep);
		} catch (InterruptedException e) {	
			throw e;
		} finally {
			if (isKeyPressing()) {
				keyRelease(keyCode);
			}
		}
	}
}
