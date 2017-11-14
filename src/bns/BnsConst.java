package bns;

import java.io.File;

public interface BnsConst {
	/** 程序根路径*/
	String ROOT_PATH = System.getProperty("user.dir") + File.separator;
	
	/** script文件夹路径*/
	String SCRIPT_PATH = ROOT_PATH.concat("script").concat(File.separator);
	
	/** resource文件夹路径*/
	String RESOURCE_PATH = ROOT_PATH.concat("resource").concat(File.separator);	

	/** 按键弹起延迟*/
	int RELEASE_DELAY = BnsUtil.PROP_CONFIG.getPropertyInt("RELEASE_DELAY");
	/** 按键动作后默认休眠时间*/
	int KEY_DEFAULT_SLEEP = BnsUtil.PROP_CONFIG.getPropertyInt("KEY_DEFAULT_SLEEP");
	/** 鼠标动作后默认休眠时间*/
	int MOUSE_DEFAULT_SLEEP = BnsUtil.PROP_CONFIG.getPropertyInt("MOUSE_DEFAULT_SLEEP");
	/** 双击间隔*/
	int DB_SPACE = BnsUtil.PROP_CONFIG.getPropertyInt("DOUBLE_CLICK_SPACE");
	
	/** 抓举持续时长 */
	int AGANT_SPAN = BnsUtil.PROP_CONFIG.getPropertyInt("AGANT_SPAN");
	
	/** 超神持续时长 */
	int AWAKE_SPAN = BnsUtil.PROP_CONFIG.getPropertyInt("AWAKE_SPAN");
	
	String MOUSE_LEFT = "MOUSE_LEFT";
	
	String MOUSE_RIGHT = "MOUSE_RIGHT";

	
	/** 公共热键ID */
	enum LISTEN_ID_COM {
		ON,
		OFF,
		CLOSE,
		RECORD,
		RECORD_PLAY,
		RECORD_COLOR,
		VISIABLE,
		REFRESH
	}
	
	/** 战斗开始热键ID*/
	int LISTEN_ID_START = 101;
	
	/** 战斗停止热键ID*/
	int LISTEN_ID_STOP = 102;
	
	/** 奔跑热键ID*/
	int LISTEN_ID_RUN = 103;
	
	/** 战斗辅助热键ID*/
	int LISTEN_ID_HELP = 104;
	
	/** 每组ID最大数量*/
	int LISTEN_ID_MAX_NUM = 99;
	
	/** CASE热键注册序列定义*/
	int LISTEN_ID_CASE_BASE = 200;
	
	/** 技能热键起始ID*/
	int LISTEN_ID_KEY_BASE = 300;
	
	/** 职业选择热键起始ID*/
	int LISTEN_ID_CAREER_BASE = 400;
	
	/** 职业*/
	enum CAREER {
		召唤士,
		冰气功,
		火枪手,
		暗枪手,
		咒术士,
		力士,
		时空塔,
	}	
}
