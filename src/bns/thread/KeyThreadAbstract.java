package bns.thread;

import org.apache.log4j.Logger;

import bns.BnsConst;
import bns.BnsUtil;
import bns.bean.ScriptBean;

public abstract class KeyThreadAbstract extends KeyThreadBase {
	private Logger log = Logger.getLogger(KeyThreadAbstract.class);
	
	protected boolean isFirstLoop = true;
	
	public KeyThreadAbstract() {
		super();
	}
	
	public KeyThreadAbstract(String key) {
		this();
		this.key = key;
	}
	
	/**
	 * 运行线程
	 */
	public void run() {
		
		while (!isOver) {
			try {
				if (isPause) {
					this.doWait();
					if (isOver) {
						return;
					}
					this.isFirstLoop = true;
				}
				
				if (runSkill()) {
					this.isFirstLoop = false;
				} else {
					isPause = true;
					if (isContinueFight) {
						fm.getBnsHotkeyListener().startFight();
					}
				}
			
			} catch (InterruptedException e) {
//				log.error("InterruptedException>>>>>>>>>");
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	
	protected abstract boolean runSkill() throws InterruptedException, Exception;
	
	/**
	 * 鼠标按键响应
	 * @param bean
	 * @throws Exception
	 */
	public void mousePress(ScriptBean bean) throws InterruptedException, Exception{	
		mousePress(bean.getName(), bean.getReleaseDelay(), bean.getSleep(), bean.isAgent());
	}
	
	/**
	 * 鼠标按键响应
	 * @param mouseName 按键名
	 * @throws Exception
	 */
	public void mousePress(String mouseName) throws InterruptedException, Exception{
		mousePress(mouseName, BnsConst.RELEASE_DELAY, BnsConst.MOUSE_DEFAULT_SLEEP, true);
	}
	
	/**
	 * 鼠标按键响应
	 * @param mouseName 按键名
	 * @param sleep 按键执行后睡眠时间
	 * @throws Exception
	 */
	public void mousePress(String mouseName, int sleep) throws InterruptedException, Exception{
		mousePress(mouseName, BnsConst.RELEASE_DELAY, sleep, true);
	}
	
	/**
	 * 鼠标按键响应
	 * @param mouseName 按键名
	 * @param releaseDelay 按键释放迟延
	 * @param sleep 按键执行后睡眠时间
	 * @param isKeyAgent 按键代理
	 * @throws Exception
	 */
	public void mousePress(String mouseName, int releaseDelay, int sleep, boolean isKeyAgent) throws InterruptedException, Exception{
		if (isPause) throw new InterruptedException();
		r.mousePressAndRelease(mouseName, releaseDelay, sleep, isKeyAgent);
	}
	
	/**
	 * 按键响应
	 * @param bean
	 * @throws Exception
	 */
	public void keyPress(ScriptBean bean) throws InterruptedException, Exception {
		keyPress(bean.getName(), bean.getReleaseDelay(), bean.getSleep(), bean.isAgent());
	}
	
	/**
	 * 按键响应
	 * @param keyName
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void keyPress(String keyName) throws InterruptedException, Exception {
		keyPress(keyName, BnsConst.RELEASE_DELAY, BnsConst.KEY_DEFAULT_SLEEP, true);
	}
	
	/**
	 * 按键响应
	 * @param keyName 按键名
	 * @param sleep 按键执行后睡眠时间
	 * @throws Exception
	 */
	public void keyPress(String keyName, int sleep) throws InterruptedException, Exception{
		keyPress(keyName, BnsConst.RELEASE_DELAY, sleep, true);
	}
	
	/**
	 * 按键响应
	 * @param keyName 按键名
	 * @param releaseDelay 按键释放迟延
	 * @param sleep 按键执行后睡眠时间
	 * @param isKeyAgent 按键代理
	 * @param isCheckColor
	 * @throws Exception
	 */
	public void keyPress(String keyName, int releaseDelay, int sleep, boolean isKeyAgent) throws InterruptedException, Exception{
		if (isPause) throw new InterruptedException();
		r.keyPressAndRelease(keyName, releaseDelay, sleep, isKeyAgent);
	}
	
	/**
	 * 取色check
	 * @param colorName
	 * @return
	 */
	public boolean isColorOK(String colorName) {
		return BnsUtil.isColorOK(colorName, r);
	}
}
