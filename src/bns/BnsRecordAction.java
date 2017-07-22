package bns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.jnativehook.GlobalScreen;

import bns.bean.RecordBean;
import bns.listener.record.KeyListenerRecord;
import bns.listener.record.MouseListenerRecord;
import bns.listener.record.MouseMotionListenerRecord;
import bns.properties.BnsPropertiesConfig;

public class BnsRecordAction {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BnsRecordAction.class);
	
	/** 按键动作记录线程*/
	private KeyListenerRecord keyLstn = null;
	/** 鼠标动作记录线程*/
	private MouseListenerRecord mouseLstn = null;
	/** 鼠标移动记录线程*/
	private MouseMotionListenerRecord mouseMotionLstn = null;
	/** 动作记录结果List*/
	private List<RecordBean> recordList = null;
	
	private File recordFile = null;
	
	private boolean isRun = false;
	
	public BnsRecordAction(){
		recordList = new ArrayList<RecordBean>();
		keyLstn = new KeyListenerRecord(recordList);
		mouseLstn = new MouseListenerRecord(recordList);
		mouseMotionLstn = new MouseMotionListenerRecord(recordList);
		
		recordFile = new File(BnsConst.SCRIPT_PATH.concat(BnsPropertiesConfig.getInstance().getProperty("RECORD_FILE")));
	}
	
	/**
	 * 开始记录动作
	 */
	public void startRecordAction() {
		isRun = !isRun;
		
		if (isRun) {
			recordList.clear();
			GlobalScreen.addNativeMouseListener(mouseLstn);
			GlobalScreen.addNativeMouseMotionListener(mouseMotionLstn);
			GlobalScreen.addNativeKeyListener(keyLstn);
		} else {
			GlobalScreen.removeNativeMouseListener(mouseLstn);
			GlobalScreen.removeNativeMouseMotionListener(mouseMotionLstn);
			GlobalScreen.removeNativeKeyListener(keyLstn);
			
			//输出动作记录文件
			outputRecordFile();
		}
	}
	
	/**
	 * 输出动作记录文件
	 */
	private void outputRecordFile() {
		StringBuffer buf = new StringBuffer();
		long preTime = 0;
		String move = "";
		
		buf.append("#KEY=");
		buf.append(BnsPropertiesConfig.getInstance().getProperty("KEY_RECORD_PLAY")).append("\r\n");
		buf.append("#LOOP\r\n");
		buf.append("#NOAGENT\r\n");
		
		for (RecordBean bean : recordList) {			
			if (bean.getName().equals("MOVE")) {
				move = "MOVE " + bean.getX() + " " + bean.getY();
			} else {
				if (move.length() > 0) {
					buf.append(move).append("\r\n");
					move = "";
				}
				
				if (!bean.getName().equals("KEY Left Control") && !bean.getName().equals("KEY F11")) {
					if (preTime > 0) {
						buf.append("PAUSE ").append(bean.getTime() - preTime).append("\r\n");
					}
					preTime = bean.getTime();
					buf.append(bean.getName()).append("\r\n");
				}
			}			
		}
		
		if (move.length() > 0) {
			buf.append(move).append("\r\n");
		}
		
		buf.append("PAUSE 2000");
		
		OutputStreamWriter writer = null;
		
		try {
			OutputStream os = new FileOutputStream(recordFile, false);
			writer = new OutputStreamWriter(os, "UTF-8");
			writer.write(buf.toString());
			writer.flush();			
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();					
				} catch (Exception e1) {
					log.error(e1);
				}
			}
		}
	}
}
