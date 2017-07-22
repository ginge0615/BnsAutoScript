package bns.listener.record;

import java.util.List;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import bns.bean.RecordBean;

public class MouseListenerRecord implements NativeMouseListener{
	private List<RecordBean> recordList;

	public MouseListenerRecord(List<RecordBean> recordList) {
		this.recordList = recordList;
	}
	

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {
		/* Unimplemented */
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {
		RecordBean bean = new RecordBean();
		bean.setTime(System.currentTimeMillis());
		bean.setKey(false);
		
		switch (arg0.getButton()) {
		case 1:
			bean.setName("MOUSE_LEFT");
			break;
		case 2:
			bean.setName("MOUSE_RIGHT");
			break;
		case 3:
			bean.setName("MOUSE_CENTER");
			break;
		default:
			break;
		}
		
		recordList.add(bean);
	}	

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {
	}	

}
