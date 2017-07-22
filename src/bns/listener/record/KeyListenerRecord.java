package bns.listener.record;

import java.util.List;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import bns.bean.RecordBean;

public class KeyListenerRecord implements NativeKeyListener {
	private List<RecordBean> recordList;
	
	public KeyListenerRecord(List<RecordBean> recordList) {
		this.recordList = recordList;
	}

	public void nativeKeyPressed(NativeKeyEvent e) {
		RecordBean bean = new RecordBean();
		bean.setTime(System.currentTimeMillis());
		bean.setKey(true);
		bean.setName("KEY " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		recordList.add(bean);
	}
	
	public void nativeKeyReleased(NativeKeyEvent e) {
	}
	
	public void nativeKeyTyped(NativeKeyEvent e) {
		
	}
	
}
