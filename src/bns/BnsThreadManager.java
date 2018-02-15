package bns;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bns.thread.AutoFightKeyThreadAbstract;
import bns.thread.KeyThreadBase;
import bns.thread.KeyThreadRun;
import bns.thread.KeyThreadScript;
import bns.thread.PixColorThread;

public class BnsThreadManager {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BnsThreadManager.class);
	
	private BnsFrame frame = null;
	
	/** 全部线程List*/
	private Map<Integer, KeyThreadBase> mapThread = null;
	/** 默认战斗线程*/
	private List<KeyThreadBase> thFights = null;
	/** 取色线程 */
	private PixColorThread thPixColor = null;
	/** 线程的ID*/
	private int indentifyId;
		
	public BnsThreadManager(BnsFrame frame) {
		this.frame = frame;
		mapThread = new HashMap<Integer, KeyThreadBase>();
		thFights = new ArrayList<KeyThreadBase>();
	}
	
	public void init() {
		this.indentifyId = BnsConst.LISTEN_ID_KEY_BASE;
		
		//奔跑线程
		addThread(new KeyThreadRun());
		
		//取色线程
		thPixColor = new PixColorThread();
		addThread(thPixColor);
		thFights.add(thPixColor);
		
		//根据脚本文件创建线程
		createScriptThreads(frame.getCareer());
		
		//创建自定义技能线程
		createCustormizeSkillThreads(frame.getCareer());
	}	

	/**
	 * 取得取色线程
	 * @return
	 */
	public PixColorThread getPixColorThread() {
		return thPixColor;
	}

	public Map<Integer, KeyThreadBase> getThreadMap() {
		return mapThread;
	}
	
	public void refresh() {
		for (KeyThreadBase th : mapThread.values()) {
			th.doOver();
		}
		
		mapThread.clear();
		thFights.clear();
		
		this.init();
	}
	
	/**
	 * 暂停所有线程
	 */
	public void pause() {
		for (KeyThreadBase th : mapThread.values()) {
			if (th.isRun()) {
				th.doPause();
			}
		}
	}
	
	/**
	 * 启动或者暂停线程
	 * @param indentity
	 */
	public void startOrPauseThread(int indentity) {
		pause();
		
		if (!mapThread.containsKey(indentity)) return;
		
		KeyThreadBase th = mapThread.get(indentity);
		
		if (th.isRun()) {
			th.doPause();
		} else {
			th.doStart();
		}
	}
	
	/**
	 * 启动线程
	 * @param indentity
	 */
	public void startThread(int indentity) {
		if (BnsConst.LISTEN_ID_START == indentity) {
			this.pause();
			
			//战斗热键
			for (KeyThreadBase th : thFights) {
				if (!th.isRun()) {
					th.doStart();
				}
			}
			
		} else if (mapThread.containsKey(indentity)) {
			// 战斗热键以外
			KeyThreadBase th = mapThread.get(indentity);

			if (!th.isRun()) {
				this.pause();
				th.doStart();
			}
		}
	}
	
	/**
	 * 创建自定义线程
	 * @param career
	 * @param indentifyId
	 */
	private void createCustormizeSkillThreads(BnsConst.CAREER career) {
		String packageName = "bns.thread.".concat(career.name());
		
		StringBuffer packagePath = new StringBuffer(BnsConst.ROOT_PATH);
		packagePath.append("bin").append(File.separator);
		packagePath.append("bns").append(File.separator);
		packagePath.append("thread").append(File.separator);
		packagePath.append(career.name());
		
		Set<Class<?>> classes = new HashSet<Class<?>>();
		findAndAddClassesInPackageByFile(packageName, packagePath.toString(), false, classes);
		
		try {
			for (Class<?> cl : classes) {
				if (cl.getSuperclass().getName().contains("KeyThreadAbstract")) {
					KeyThreadBase th = (KeyThreadBase)cl.newInstance();
					
					//设置取色线程
					if (th instanceof AutoFightKeyThreadAbstract) {
						((AutoFightKeyThreadAbstract) th).setThPixColor(thPixColor);
					}
					
					th.setIndentity(++this.indentifyId);
					addThread(th);
				}
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得职业技能脚本
	 * @param career
	 * @return
	 */
	private void createScriptThreads(BnsConst.CAREER career) {
		File dir = new File(BnsConst.SCRIPT_PATH.concat(career.name()));

		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.getName().startsWith("KEY")) {
					KeyThreadScript th = new KeyThreadScript(file);

					if (!th.isOff()) {
						th.setIndentity(++this.indentifyId);
						addThread(th);
					}
				}
			}
		}
		
		//动作记录文件
		File file = new File(BnsConst.SCRIPT_PATH.concat(BnsUtil.PROP_CONFIG.getProperty("RECORD_FILE")));
		KeyThreadScript th = new KeyThreadScript(file);
		th.setIndentity(BnsConst.LISTEN_ID_COM.RECORD_PLAY.ordinal());
		addThread(th);
	}
	
	/**
     * 以文件的形式来获取包下的所有Class
     * 
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    private void findAndAddClassesInPackageByFile(String packageName,
            String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "."
                        + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    //classes.add(Class.forName(packageName + '.' + className));
                                         //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                                        classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));  
                } catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void addThread(KeyThreadBase th) {
    	mapThread.put(th.getIndentity(), th);
    	
    	if (th.getKey().equals(BnsUtil.PROP_CONFIG.getProperty("KEY_START"))) {
			this.thFights.add(th);
		}
    }

}
