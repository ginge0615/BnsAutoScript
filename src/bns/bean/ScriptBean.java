package bns.bean;

import bns.BnsConst;
import bns.BnsUtil;

public class ScriptBean {
	/** 动作区分*/
	private String kbn = "";
	/** 鼠标按键名*/
	private String name = "";
	/** 按键弹起延迟*/
	private int releaseDelay = BnsConst.RELEASE_DELAY;
	/** 动作后睡眠时间*/
	private int sleep = 0;
	/** 是否需要按键代理*/
	private boolean isAgent = true;
	/** 是否需要取色Check*/
	private boolean isColorCheck = false;
	/** 循环Flag*/
	private boolean isLoop = true;
	/** 坐标X*/
	private int x;
	/** 坐标Y*/
	private int y;
	/** 注释*/
	private String comment;
	/** 下一次运行的间隔时间*/
	private long nextRunSpace = 0;
	/** 最近执行时间*/
	private long lastRunTime = 0l;
	
	public ScriptBean(String s) {
		this.setBean(s.toUpperCase());
	}
	
	private void setBean(String s){
		//真实按键，不进行代理
		if (s.contains("NOLOOP")) {
			isLoop = false;
			s = s.replaceAll("NOLOOP", "");
		}
		
		//进行取色check
		if (s.contains("COLOR")) {
			isColorCheck = true;
			s = s.replaceAll("COLOR", "");
		}
		
		//真实按键，不进行代理
		if (s.contains("REAL")) {
			isAgent = false;
			s = s.replaceAll("REAL", "");
		}
		
		int index = s.indexOf("NEXT");
		if (index > 0) {
			int indexE = s.indexOf(")", index);
			this.nextRunSpace = Integer.parseInt(s.substring(index + 5, indexE));
			s = s.replaceAll("NEXT\\(\\d+\\)", "");
		}
		
		//注释
		index = s.indexOf("//");
		if (index > 0) {
			comment = s.substring(index);
			s = s.substring(0, index);
		}
		
		String[] data = BnsUtil.removeNonBreakSpace(s).split(" ");
		
		int size = data.length;
		
		if (size == 0) {
			return;
		}
		
		kbn = data[0];//区分
		
		if (isKey()) {
			name = data[1];
			this.sleep = BnsConst.KEY_DEFAULT_SLEEP;
			
			//KEY_SLEEP的场合
			if (s.contains("[")) {
				int indexS = s.indexOf("[");
				int indexE = s.indexOf("]");
				sleep = Integer.parseInt(s.substring(indexS + 1, indexE));
			}
			
			if (size >= 4) {
				if (BnsUtil.isNumeric(data[2])) {
					this.releaseDelay = Integer.parseInt(data[2]);
				}
				
				if (BnsUtil.isNumeric(data[3])) {
					this.sleep = Integer.parseInt(data[3]);
				}
			} else if (size >= 3) {
				if (BnsUtil.isNumeric(data[2])) {
					sleep = Integer.parseInt(data[2]);
				} else {
					this.comment = data[2];
				}
			}
			
		} else if (isMouse()) {
			this.sleep = BnsConst.MOUSE_DEFAULT_SLEEP;
			
			if (s.contains("_")) {
				name = kbn;
				
				if (size >= 3) {
					releaseDelay = Integer.parseInt(data[1]);
					sleep = Integer.parseInt(data[2]);
				} else if (size >= 2) {
					sleep = Integer.parseInt(data[1]);
				}
			} else {
				if ("L".equals(data[1])) {
					data[1] = "LEFT";
				} else if ("R".equals(data[1])) {
					data[1] = "RIGHT";
				}
				
				name = kbn + "_" + data[1];
				
				if (size >= 4) {
					releaseDelay = Integer.parseInt(data[2]);
					sleep = Integer.parseInt(data[3]);
				} else if (size >= 3) {
					sleep = Integer.parseInt(data[2]);
				}
			}

		} else if (isSleep()) {
			sleep = Integer.parseInt(data[1]);			
		} else if (isMove()) {
			x = Integer.parseInt(data[1]);
			y = Integer.parseInt(data[2]);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(" 区分=").append(kbn);
		buf.append(" 名称=").append(name);
		buf.append(" 按键弹起延迟=").append(releaseDelay);
		buf.append(" 休眠时间=").append(sleep);
		buf.append(" 是否需要代理=").append(this.isAgent);
		buf.append(" 是否需要取色Check=").append(this.isColorCheck);
		buf.append(" 下一次运行的间隔时间=").append(this.nextRunSpace);
		buf.append(" 注释=").append(comment);
		return buf.toString();
	}

	public String getKbn() {
		return kbn;
	}

	public void setKbn(String kbn) {
		this.kbn = kbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReleaseDelay() {
		return releaseDelay;
	}

	public void setReleaseDelay(int releaseDelay) {
		this.releaseDelay = releaseDelay;
	}

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	public boolean isAgent() {
		return isAgent;
	}

	public void setAgent(boolean isAgent) {
		this.isAgent = isAgent;
	}

	public boolean isColorCheck() {
		return isColorCheck;
	}

	public void setColorCheck(boolean isColorCheck) {
		this.isColorCheck = isColorCheck;
	}

	public boolean isLoop() {
		return isLoop;
	}

	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isKey() {
		return kbn.startsWith("KEY");
	}
	
	public boolean isMouse() {
		return kbn.startsWith("MOUSE");
	}
	
	public boolean isSleep() {
		return kbn.equals("PAUSE") || kbn.equals("SLEEP");
	}
	
	public boolean isMove() {
		return kbn.equals("MOVE");
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getNextRunSpace() {
		return nextRunSpace;
	}

	public void setNextRunSpace(long nextRunSpace) {
		this.nextRunSpace = nextRunSpace;
	}

	public long getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(long lastRunTime) {
		this.lastRunTime = lastRunTime;
	}
}
