package bns.listener.record;

import java.util.List;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

import bns.bean.RecordBean;

public class MouseMotionListenerRecord implements NativeMouseMotionListener{
	private List<RecordBean> recordList;
	
	public MouseMotionListenerRecord(List<RecordBean> recordList) {
		this.recordList = recordList;
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
		RecordBean bean = new RecordBean();
		bean.setTime(System.currentTimeMillis());
		bean.setKey(false);
		bean.setName("MOVE");
		bean.setX(e.getX());
		bean.setY(e.getY());
		recordList.add(bean);
	}
	
	public void nativeMouseDragged(NativeMouseEvent e) {
		
	}
}
