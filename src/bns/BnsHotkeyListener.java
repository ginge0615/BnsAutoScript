package bns;

import java.awt.Frame;
import java.util.HashMap;
import java.util.Iterator;

import org.jnativehook.GlobalScreen;

import bns.listener.KeyListener;
import bns.listener.MouseListener;
import bns.thread.KeyThreadBase;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class BnsHotkeyListener implements HotkeyListener {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BnsHotkeyListener.class );
	
	private BnsFrame frame = null;
	
	/** 鼠标动作监听 */
	private MouseListener mouseListener;
	
	/** 按键动作监听 */
	private KeyListener keyListener;
	
	/** 线程管理*/
	private BnsThreadManager thManager = null;
	
	/** 记录 */
	private BnsRecordAction recordAction = null;
	
	private BnsRecordColor recordColor = null;
	
	/** OFF时卸载的热键Map */
	private HashMap<String ,Integer> hotKeyMapOFF = null;
	
	/** 子技能热键Map */
	private HashMap<String ,Integer> hotKeyMapSub = null;
	
	private JIntellitype jit = null;
	
	public BnsHotkeyListener(BnsFrame frame) {
		this.frame = frame;
		this.thManager = frame.getBnsThreadManager();
		this.jit = JIntellitype.getInstance();
		this.recordAction = new BnsRecordAction();
		this.recordColor = new BnsRecordColor(frame);
		this.mouseListener = new MouseListener(this.frame);
		this.keyListener = new KeyListener(this.frame);
		this.hotKeyMapOFF = new HashMap<String ,Integer>();
		this.hotKeyMapSub = new HashMap<String ,Integer>();
	}
	
	public void init() {
		JIntellitype.getInstance().addHotKeyListener(this);
		GlobalScreen.addNativeMouseListener(mouseListener);
		GlobalScreen.addNativeKeyListener(keyListener);
		
		//==============挂载公共按键监听=================
		this.setupCommonHotKey();

		//==============挂载CASE按键监听===================
		String keyCaseString = BnsUtil.PROP_CONFIG.getProperty("KEY_CASE");
		int maxCase = BnsConst.CASE.values().length;
		for (int i = 1; i <= maxCase; i++) {
			this.setupHotKey(keyCaseString + i, BnsConst.LISTEN_ID_CASE_BASE + i, false);
		}
		
		//==============挂载技能按键监听=================
		this.addHotKeyListenerSkill();
	}
	
	private void addHotKeyListenerSkill() {
		this.setupHotKey(BnsUtil.PROP_CONFIG.getProperty("KEY_START"), BnsConst.LISTEN_ID_START);
		this.setupHotKey(BnsUtil.PROP_CONFIG.getProperty("KEY_STOP"), BnsConst.LISTEN_ID_STOP);
		this.setupHotKey(BnsUtil.PROP_CONFIG.getProperty("KEY_RUN"), BnsConst.LISTEN_ID_RUN);
		
		for (KeyThreadBase th : thManager.getThreadMap().values()) {
			
			if (th.getIndentity() <= 0 || th.getKey().equals(BnsUtil.PROP_CONFIG.getProperty("KEY_START"))) {
				continue;
			}
			
			// 记录子热键
			if (th.isSubThread()) {
				this.hotKeyMapSub.put(th.getKey(), th.getIndentity());
			} else {
				// 挂载技能热键
				this.setupHotKey(th.getKey(), th.getIndentity());
			}
		}
	}
	
	/**
	 * 刷新
	 */
	public void refresh() {
		stopFight();
		this.unsetupAllHotKey();
		
		this.hotKeyMapOFF.clear();
		this.hotKeyMapSub.clear();
		this.addHotKeyListenerSkill();
	}
	
	/**
	 * 全局按键事件监听
	 */
	@Override
	public void onHotKey(int aIdentifier) {
		this.log.debug(">>>aIdentifier=" + aIdentifier);
		if (aIdentifier == BnsConst.LISTEN_ID_START) {
			//开始战斗
			startFight();
			
		} else if (aIdentifier == BnsConst.LISTEN_ID_STOP) {
			//暂停战斗
			stopFight();
			
		} else if (aIdentifier >= BnsConst.LISTEN_ID_KEY_BASE && aIdentifier <= BnsConst.LISTEN_ID_KEY_BASE + BnsConst.LISTEN_ID_MAX_NUM ) {
			//执行技能
			thManager.startThread(aIdentifier);
			
		} else if (aIdentifier == BnsConst.LISTEN_ID_RUN) {
			//奔跑
			if (frame.getStatus() != BnsFrame.STATUS_FIGHTING) {
				thManager.startOrPauseThread(aIdentifier);
			}
			
		} else if (aIdentifier == BnsConst.LISTEN_ID_COM.ON.ordinal()) {
			//全局热键监听挂载
			doOn();
			
		} else if (aIdentifier == BnsConst.LISTEN_ID_COM.OFF.ordinal()) {
			doOff();
			
		} else if (aIdentifier == BnsConst.LISTEN_ID_COM.CLOSE.ordinal()) {
			//退出程序
			destory();			
			JIntellitype.getInstance().cleanUp();
			System.exit(0);
			
		} else if (aIdentifier == BnsConst.LISTEN_ID_COM.RECORD.ordinal()) {
			///全局热键监听卸载
			this.unsetupAllHotKeyExcept(aIdentifier);
			
			//记录动作
			recordAction.startRecordAction();
		} else if (aIdentifier == BnsConst.LISTEN_ID_COM.RECORD_PLAY.ordinal()) {
			///全局热键监听卸载
			this.unsetupAllHotKeyExcept(aIdentifier);
			
			this.refresh();
			
			//执行技能
			thManager.startThread(aIdentifier);
			
		} else if (aIdentifier == BnsConst.LISTEN_ID_COM.RECORD_COLOR.ordinal()) {
			///全局热键监听卸载
			this.unsetupAllHotKeyExcept(aIdentifier);
			
			//取色
			recordColor.doShow();
			
		} else if (aIdentifier == BnsConst.LISTEN_ID_COM.VISIABLE.ordinal()) {
			//设置窗体可见
			if (frame.getState() != Frame.NORMAL) {
				frame.setState(Frame.NORMAL);
			}
		} else if (aIdentifier == BnsConst.LISTEN_ID_COM.REFRESH.ordinal()) {
			//刷新技能
			frame.refreshSkill();
		} else if (aIdentifier == BnsConst.LISTEN_ID_COM.CACULATE.ordinal()) {
			//计算技能表示
			frame.showCalculate();
			
		} else if (aIdentifier >= BnsConst.LISTEN_ID_CAREER_BASE && aIdentifier <= BnsConst.LISTEN_ID_CAREER_BASE + BnsConst.LISTEN_ID_MAX_NUM ) {
			//职业选择
			frame.setCmbCareerIndex(aIdentifier);
			
		} else if (aIdentifier >= BnsConst.LISTEN_ID_CASE_BASE && aIdentifier <= BnsConst.LISTEN_ID_CASE_BASE + BnsConst.LISTEN_ID_MAX_NUM ) {
			//设置技能CASE
			frame.setCase(BnsConst.CASE.values()[aIdentifier - BnsConst.LISTEN_ID_CASE_BASE - 1]);
		} 
	}
	
	/**
	 * 挂载公共按键监听
	 */
	private void setupCommonHotKey() {
		for (BnsConst.LISTEN_ID_COM key : BnsConst.LISTEN_ID_COM.values()) {
			String keyName = BnsUtil.PROP_CONFIG.getProperty("KEY_" + key.name());
			this.setupHotKey(keyName, key.ordinal(), false);
		}
	}
	
	/**
	 * 挂载热键监听
	 * @param key
	 * @param indentity
	 * @param unsetupFlg 监听是否可以卸载
	 */
	private void setupHotKey(String key, int indentity, boolean unsetupFlg) {
		if (key == null || "".equals(key)) {
			return;
		}
		
		unsetupHotKey(indentity);

		if (key.length() == 1) {
			jit.registerHotKey(indentity, 0, key.toUpperCase().toCharArray()[0]);
		} else {
			jit.registerHotKey(indentity, key.toLowerCase());
		}
		
		if (unsetupFlg) {
			this.hotKeyMapOFF.put(key,indentity);
		}
	}
	
	/**
	 * 挂载热键监听
	 * @param key
	 * @param indentity
	 */
	public void setupHotKey(String key, int indentity) {
		this.setupHotKey(key, indentity, true);
	}
	
	/**
	 * 卸载热键监听
	 * @param indentity
	 */
	public void unsetupHotKey(int indentity) {
		jit.unregisterHotKey(indentity);
	}

	/**
	 * 挂载所有热键监听
	 * （子技能热键除外）
	 */
	private void setupAllHotKey() {
		Iterator<String> iter = this.hotKeyMapOFF.keySet().iterator();
		
		while (iter.hasNext()) {
			String key = iter.next();
			
			//不是子技能热键的情况下，挂载
			if (!this.hotKeyMapSub.containsKey(key)) {
				this.setupHotKey(key, this.hotKeyMapOFF.get(key));
			}
		}
	}
	
	/**
	 * 卸载所有热键监听
	 * 除了ON,OFF
	 */
	private void unsetupAllHotKey() {
		unsetupAllHotKeyExcept(-1);
	}
	
	/**
	 * 除指定热键外，卸载其他热键监听
	 */
	private void unsetupAllHotKeyExcept(int exceptIndentity) {
		for (int indentity : this.hotKeyMapOFF.values()) {
			if (indentity != exceptIndentity) {
				this.unsetupHotKey(indentity);
			}
		}
	}
	
	/**
	 * 挂载所有子技能热键监听
	 */
	private void setupAllSubHotKey() {
		Iterator<String> iter = this.hotKeyMapSub.keySet().iterator();
		
		while (iter.hasNext()) {
			String key = iter.next();
			this.setupHotKey(key, this.hotKeyMapSub.get(key));
		}
	}
	
	/**
	 * 卸载所有技能热键监听
	 * 除了ON,OFF
	 */
	private void unsetupAllSubHotKey() {
		for (int indentity : this.hotKeyMapSub.values()) {
			this.unsetupHotKey(indentity);
		}
	}
	
	/**
	 * 销毁
	 */
	public void destory() {
		JIntellitype.getInstance().removeHotKeyListener(this);		
	}
	
	/**
	 * 停止战斗
	 */
	public void stopFight() {
		// 暂停战斗
		thManager.pause();
		// 卸载所有技能热键监听
		this.unsetupAllSubHotKey();
	}
	
	/**
	 * 开始战斗
	 */
	public void startFight() {
		// 挂载所有技能热键监听
		this.setupAllSubHotKey();
		// 开始战斗线程
		thManager.startThread(BnsConst.LISTEN_ID_START);
	}
	
	/**
	 * 挂载监听
	 */
	public void doOn() {
		if (frame.getStatus() == BnsFrame.STATUS_NO_FIGHTING) return;
		
		//全局热键监听挂载
		this.setupAllHotKey();
		
		frame.setStatus(BnsFrame.STATUS_NO_FIGHTING);
	}
	
	/**
	 * 卸载监听
	 */
	public void doOff() {
		if (frame.getStatus() == BnsFrame.STATUS_OFF) return;
		
		//停止战斗
		stopFight();
		///全局热键监听卸载（ON，OFF除外）
		this.unsetupAllHotKey();
		
		frame.setStatus(BnsFrame.STATUS_OFF);
	}
}
