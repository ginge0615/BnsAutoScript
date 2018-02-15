package bns.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import bns.bean.ScriptBean;


public class KeyThreadScript extends KeyThreadAbstract {
	private Logger logger = Logger.getLogger(KeyThreadScript.class );
	
	private HashMap<Integer, ArrayList<ScriptBean>> skillCase = new HashMap<Integer, ArrayList<ScriptBean>>();
	private List<ScriptBean> skills = null;
	
	private boolean isOff = false;
	protected boolean isLoop = false;
	private int index;

	public KeyThreadScript(File file) {
		super();
		loadSkill(file);
	}
	
	/**
	 * 开始
	 */
	public synchronized void doStart() {
		int kbnCase = fm.getCase().ordinal();
		
		if (skillCase.containsKey(kbnCase)) {
			skills = skillCase.get(kbnCase);
		} else {
			skills = skillCase.get(1);
		}
		
		index = 0;
		
		super.doStart();
		
	}
	
	protected boolean runSkill() throws InterruptedException, Exception {
		logger.debug("runSkill");
		
		if (index >= skills.size()) index = 0;
		
		ScriptBean bean = skills.get(index);
		if (bean.isKey()) {
			keyPress(bean);
			
		} else if (bean.isMouse()) {
			mousePress(bean);
			
		} else if (bean.isSleep()) {
			doSleep(bean.getSleep());
			
		} else if (bean.isMove()) {
			r.mouseMove(bean.getX(), bean.getY());
		}
		
		//去掉只执行一次的技能
		if (isLoop && !bean.isLoop()) skills.remove(index);
		
		index++;
		
		return isLoop || index < skills.size();
	}
	
	/**
	 * 读技能文件
	 * @param file
	 */
	private void loadSkill(File file) {
		try {
			// 文件缓冲输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			
			logger.info("=============================================================");
			String s = br.readLine();
			
			ArrayList<ScriptBean> list = new ArrayList<ScriptBean>();
			ArrayList<ScriptBean> listBlock = new ArrayList<ScriptBean>();
			skillCase.put(1, list);
			
			boolean isBlock = false;
			
			while (s != null) {
				s = s.trim().toUpperCase();
				logger.info(s);
				
				if (!"".equals(s)) {
					if (s.startsWith("#OFF")) {
						this.isOff = true;
						break;
					} else if (s.startsWith("#KEY=")) {				
						key = s.substring(s.indexOf("=") + 1, s.length());
					} else if (s.startsWith("#LOOP")) {
						isLoop = true;
					} else if (s.startsWith("#CONTINUE")) { 
						isContinueFight = true;
					} else if (s.startsWith("#SUB")) {
						this.isSubThread = true;
					}
					else if (s.startsWith("#CASE")) {
						int kbn = Integer.parseInt(s.replace("#CASE", ""));
						
						if (!skillCase.containsKey(kbn)) {
							list = new ArrayList<ScriptBean>();
							skillCase.put(kbn, list);
						}
						
					}else if (s.startsWith("{")) {
						isBlock = true;
						listBlock.clear();
					}else if (s.startsWith("}")) {
						isBlock = false;
						
						int loopCnt = Integer.parseInt(s.substring(1));
						
						for (int i = 0; i < loopCnt; i++) {
							list.addAll(listBlock);
						}
						
					}else if (!s.contains("#")){
						ScriptBean bean = new ScriptBean(s);
						
						if (isBlock) {
							listBlock.add(bean);
						} else {
							list.add(bean);
						}
					}
				}
				
				// 读取下一行
				s = br.readLine();
			}			
			
			// 关闭输入流
			br.close();
						
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public boolean isOff() {
		return isOff;
	}

	public void setOff(boolean isOff) {
		this.isOff = isOff;
	}
}
