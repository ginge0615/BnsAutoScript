package bns;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.PropertyConfigurator;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.melloware.jintellitype.JIntellitype;
import com.melloware.jintellitype.JIntellitypeException;

import bns.properties.BnsPropertiesColor;
import bns.properties.BnsPropertiesSave;
import bns.test.BnsTest;

/**
 * 主界面
 * @author jianghb
 *
 */
public class BnsFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/** 常量*/
    public static final int STATUS_OFF = -1;
    public static final int STATUS_NO_FIGHTING = 0;
    public static final int STATUS_FIGHTING = 1;
    public static final int STATUS_RUNNING = 2;
    
    private static final String EXTEND_DOWN = "▼";
    private static final String EXTEND_UP = "▲";
    
    /** 单例对象*/
    private static BnsFrame bf = null;
    /** 按键响应处理对象*/
    private BnsHotkeyListener listener = null;
	/** 线程管理对象*/
	private BnsThreadManager thManager = null;
    
    /** 变量*/
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BnsFrame.class );
	private JPanel panel = null;
	private JLabel jLabelStatus = null;
	private JLabel jLabelCase = null;
	private TrayIcon trayIcon = null;
	private JLabel jLabelExtend = null;
	private JLabel jLabelMin = null;     //最低价格
	private JLabel jLabelMax = null;     //最高价格
	private JTextField jTxtPrice = null; //单价
	private JSpinner jSpinnerNum = null;//数量
	private JSpinner jSpinnerPersionNum = null;// 队伍人数
	
	/** 职业选择下拉框*/
	private JComboBox<String> cmbCareer = null;
	/** 职业数组*/
	private BnsConst.CAREER[] arrCareer = null;
	/** 测试用对象*/
	private BnsTest objBnsTest = null;
	/** 鼠标左键按下时的窗体位置*/
	private int mousePressedX = 0;  
	private int mousePressedY = 0;
    
    private BnsPropertiesSave propSave = BnsPropertiesSave.getInstance();
    
    private int status;
    

    /** 技能CASE*/
    private BnsConst.CASE caseKbn;
    
    public static BnsFrame getInstance() {
    	if (bf == null) {
    		bf = new BnsFrame();
    	}
    	
    	return bf;
    }
	
	public BnsFrame(){
		super();		
		this.initComponents();
		this.initListerer();
		
		thManager = new BnsThreadManager(this);
		listener = new BnsHotkeyListener(this);
	}
	
	/**
	 * 初始化画面控件
	 */
	private void initComponents() {
		// 画面控件定义
		initSize();
		this.setContentPane(getJContentPane());
		this.setTitle("BnsFrame");
		this.setResizable(false);
		this.setLocation(new Point(propSave.getPropertyInt("LOCATION_X"), propSave.getPropertyInt("LOCATION_Y")));
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		setIcon();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 初始化职业选择下拉框
		arrCareer = BnsConst.CAREER.values();

		for (int i = 0; i < arrCareer.length; i++) {
			this.cmbCareer.addItem(arrCareer[i].name());
			JIntellitype.getInstance().registerHotKey(
							BnsConst.LISTEN_ID_CAREER_BASE + i,
							BnsUtil.PROP_CONFIG.getProperty("KEY_SEL_CAREER") + (i + 1));
		}

		cmbCareer.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
					doChangeCareer(arrCareer[cmbCareer.getSelectedIndex()]);
				}
			}
		});
	}
	
	/**
	 * 初始化画面监听事件
	 */
	private void initListerer() {
		//处理拖动事件  
        this.addMouseListener(new MouseAdapter() {
            @Override  
            public void mousePressed(MouseEvent e) { 
				if (e.getButton() == 1) {
					mousePressedX = e.getX();
					mousePressedY = e.getY();
					
					log.debug("X=" + mousePressedX + "  Y=" + mousePressedY);

				} else if (e.getButton() == 3) {
					exit();
				}
			}
        });  
        
        if ("true".equalsIgnoreCase(BnsUtil.PROP_CONFIG.getProperty("MOVE_ENABLE"))) {
        	this.addMouseMotionListener(new MouseMotionAdapter() {       	
        		
        		@Override  
        		public void mouseDragged(MouseEvent e) {  
        			int xOnScreen = e.getXOnScreen();  
        			int yOnScreen = e.getYOnScreen();
        			int xx = xOnScreen - mousePressedX;  
        			int yy = yOnScreen - mousePressedY;
        			BnsFrame.this.setLocation(xx, yy);
        		}
        	});
        }
	}
	
	/**
	 * 退出处理
	 */
	private void exit() {
		//保存画面坐标
		Point pt = BnsFrame.this.getLocation();
		propSave.setProperty("LOCATION_X", (int)pt.getX());
        propSave.setProperty("LOCATION_Y", (int)pt.getY());
        propSave.saveProperties();
        
		SystemTray.getSystemTray().remove(trayIcon);
		JIntellitype.getInstance().cleanUp();
		
		try {
            GlobalScreen.unregisterNativeHook();
        }
        catch (NativeHookException ex) {
        	log.error("There was a problem registering the native hook.", ex);
        }
		
		System.exit(0);
	}
	
	/**
	 * 职业选择下拉框的选择变更事件
	 * @param career
	 */
	private void doChangeCareer(BnsConst.CAREER career) {
		BnsPropertiesColor.getInstance().load(career.name());
		
		thManager.refresh();
		listener.refresh();
		
		setCase(BnsConst.CASE.小怪);
		this.setStatus(STATUS_NO_FIGHTING);
		
		if (objBnsTest != null) {
			objBnsTest.clear();
			objBnsTest.setCareer(getCareer());
		}
		
	}
	
	/**
	 * This method initializes cmbCareer	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox<String> getCmbCareer() {
		if (cmbCareer == null) {
			cmbCareer = new JComboBox<String>();
			cmbCareer.setPreferredSize(new Dimension(78, 25));
			cmbCareer.setSize(new Dimension(78, 25));
			cmbCareer.setLocation(new Point(0, 0));
			cmbCareer.setFont(new Font("SimHei", Font.PLAIN, 14));
		}
		return cmbCareer;
	}
	
	private JLabel getStatusLabel() {
		if (jLabelStatus == null) {
			jLabelStatus = new JLabel();
			jLabelStatus.setPreferredSize(new Dimension(25, 25));
			jLabelStatus.setSize(new Dimension(25, 25));
			jLabelStatus.setLocation(new Point(81, 0));
			jLabelStatus.setFont(new Font("SimHei", Font.PLAIN, 14));
			jLabelStatus.setHorizontalAlignment(SwingConstants.CENTER);
			this.setStatus(STATUS_NO_FIGHTING);
		}
		
		return jLabelStatus;
	}
	
	private JLabel getCaseLabel() {
		if (jLabelCase == null) {
			jLabelCase = new JLabel();
			jLabelCase.setPreferredSize(new Dimension(25, 25));
			jLabelCase.setSize(new Dimension(45, 25));
			jLabelCase.setLocation(new Point(100, 0));
			jLabelCase.setFont(new Font("SimHei", Font.PLAIN, 15));
			jLabelCase.setHorizontalAlignment(SwingConstants.CENTER);
			setCase(BnsConst.CASE.小怪);
		}
		
		return jLabelCase;
	}
	
	
	private JLabel getExtendLabel() {
		if (jLabelExtend == null) {
			jLabelExtend = new JLabel();
			jLabelExtend.setPreferredSize(new Dimension(25, 25));
			jLabelExtend.setSize(new Dimension(25, 25));
			jLabelExtend.setLocation(new Point(142, 0));
			jLabelExtend.setFont(new Font("SimHei", Font.BOLD, 15));
			jLabelExtend.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelExtend.setText(EXTEND_DOWN);
			
			jLabelExtend.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					showCalculate();
				}
				
			});
		}
		
		return jLabelExtend;
	}
	
	private JLabel getPaimaiLabel() {
		JLabel jbl = new JLabel();
		jbl.setPreferredSize(new Dimension(40, 25));
		jbl.setSize(new Dimension(40, 25));
		jbl.setLocation(new Point(0, 30));
		jbl.setFont(new Font("SimHei", Font.PLAIN, 15));
		jbl.setHorizontalAlignment(SwingConstants.CENTER);
		jbl.setText("单价");
		return jbl;
	}
	
	private JTextField getPriceTextField() {
		if (jTxtPrice == null) {
			jTxtPrice = new JTextField();
			jTxtPrice.setPreferredSize(new Dimension(60, 30));
			jTxtPrice.setSize(new Dimension(60, 30));
			jTxtPrice.setLocation(new Point(40, 30));
			jTxtPrice.setFont(new Font("SimHei", Font.PLAIN, 15));
			jTxtPrice.setHorizontalAlignment(SwingConstants.CENTER);
			
			jTxtPrice.getDocument().addDocumentListener(new MyDocumentListener());
		}
		
		return jTxtPrice;
	}
	
	private JLabel getNumLabel() {
		JLabel jbl = new JLabel();
		jbl.setPreferredSize(new Dimension(60, 25));
		jbl.setSize(new Dimension(60, 25));
		jbl.setLocation(new Point(100, 30));
		jbl.setFont(new Font("SimHei", Font.PLAIN, 15));
		jbl.setHorizontalAlignment(SwingConstants.LEFT);
		jbl.setText("J 数量");
		return jbl;
	}
	
	private JSpinner getNumSpinner() {
		if (jSpinnerNum == null) {
			SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 99, 1); 			
			jSpinnerNum = new JSpinner(model);
			
			jSpinnerNum.setPreferredSize(new Dimension(40, 30));
			jSpinnerNum.setSize(new Dimension(40, 30));
			jSpinnerNum.setLocation(new Point(150, 30));
			jSpinnerNum.setFont(new Font("SimHei", Font.PLAIN, 15));
			jSpinnerNum.setFocusable(false);
			jSpinnerNum.addChangeListener(new ChangeListener() {				
				@Override
				public void stateChanged(ChangeEvent e) {
					calculate();
				}
			});
		}
		return jSpinnerNum;
	}
	
	private JLabel getPersonNumLabel() {
		JLabel jbl = new JLabel();
		jbl.setPreferredSize(new Dimension(40, 25));
		jbl.setSize(new Dimension(40, 25));
		jbl.setLocation(new Point(190, 30));
		jbl.setFont(new Font("SimHei", Font.PLAIN, 15));
		jbl.setHorizontalAlignment(SwingConstants.CENTER);
		jbl.setText("人数");
		return jbl;
	}
	
	private JSpinner getPersonNumSpinner() {
		if (jSpinnerPersionNum == null) {
			SpinnerNumberModel model = new SpinnerNumberModel(6, 1, 6, 1); 			
			jSpinnerPersionNum = new JSpinner(model);
			
			jSpinnerPersionNum.setPreferredSize(new Dimension(30, 30));
			jSpinnerPersionNum.setSize(new Dimension(30, 30));
			jSpinnerPersionNum.setLocation(new Point(230, 30));
			jSpinnerPersionNum.setFont(new Font("SimHei", Font.PLAIN, 15));			
			jSpinnerPersionNum.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					calculate();
				}
			});
		}
		return jSpinnerPersionNum;
	}
	
	private JLabel getMinLabel() {
		JLabel jbl = new JLabel();
		jbl.setPreferredSize(new Dimension(40, 25));
		jbl.setSize(new Dimension(40, 25));
		jbl.setLocation(new Point(0, 60));
		jbl.setFont(new Font("SimHei", Font.PLAIN, 15));
		jbl.setHorizontalAlignment(SwingConstants.CENTER);
		jbl.setText("竞拍");
		return jbl;
	}
	
	private JLabel getJingPaiMinLabel() {
		if (jLabelMin == null) {
			jLabelMin = new JLabel();
			jLabelMin.setPreferredSize(new Dimension(70, 25));
			jLabelMin.setSize(new Dimension(70, 25));
			jLabelMin.setLocation(new Point(40, 60));
			jLabelMin.setFont(new Font("SimHei", Font.PLAIN, 15));
			jLabelMin.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelMin;
	}
	
	private JLabel getMaxLabel() {
		JLabel jbl = new JLabel();
		jbl.setPreferredSize(new Dimension(40, 25));
		jbl.setSize(new Dimension(40, 25));
		jbl.setLocation(new Point(115, 60));
		jbl.setFont(new Font("SimHei", Font.PLAIN, 15));
		jbl.setHorizontalAlignment(SwingConstants.LEFT);
		jbl.setText("最高");
		return jbl;
	}
	
	private JLabel getJingPaiMaxLabel() {
		if (jLabelMax == null) {
			jLabelMax = new JLabel();
			jLabelMax.setPreferredSize(new Dimension(70, 25));
			jLabelMax.setSize(new Dimension(70, 25));
			jLabelMax.setLocation(new Point(155, 60));
			jLabelMax.setFont(new Font("SimHei", Font.PLAIN, 15));
			jLabelMax.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelMax;
	}
	
	private void setIcon() {
		try {
			String path = System.getProperty("user.dir") + File.separator
					+ "resource/BnsAuto.png";
			trayIcon = new TrayIcon(ImageIO.read(new File(path)));
			trayIcon.setImageAutoSize(true);
		} catch (IOException e2) {
			log.error("", e2);
		}

		trayIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SystemTray.getSystemTray().remove(trayIcon);
				setVisible(true);
				int state = getExtendedState();
				if ((state & JFrame.ICONIFIED) == JFrame.ICONIFIED) {
					state = state - JFrame.ICONIFIED;
					setExtendedState(state);
				}
			}
		});
	}
	
	private JPanel getJContentPane() {
		panel = new JPanel();
		panel.setLayout(null);
		
		panel.add(getCmbCareer(), null);
		panel.add(getStatusLabel(),null);
		panel.add(getCaseLabel(),null);
		panel.add(getExtendLabel(), null);
		
		panel.add(getPaimaiLabel(), null);
		panel.add(getPriceTextField(), null);
		panel.add(getNumLabel(), null);
		panel.add(getNumSpinner(),null);
		panel.add(getPersonNumLabel(), null);
		panel.add(getPersonNumSpinner(), null);
		
		panel.add(getMinLabel(), null);
		panel.add(getJingPaiMinLabel(), null);
		panel.add(getMaxLabel(), null);
		panel.add(getJingPaiMaxLabel(), null);
		
		return panel;
	}
	
	private void openTestWindows() {
		//打开测试窗口
		if ("1".equals(BnsUtil.PROP_CONFIG.getProperty("TEST"))) {
			new Thread() {
				public void run() {
					objBnsTest = new BnsTest();
					objBnsTest.setCareer(getCareer());
				}
			}.start();
		}
	}
	
	
	public void init() {
		BnsPropertiesColor.getInstance().load(arrCareer[0].name());
		thManager.init();
		listener.init();
		
		this.openTestWindows();
	}
	
	/**
	 * 设置职业下拉框被选择的职业
	 * @param aIdentifier
	 */
	public void setCmbCareerIndex(int aIdentifier) {
		cmbCareer.setSelectedIndex(aIdentifier - BnsConst.LISTEN_ID_CAREER_BASE);
	}
	
	/**
	 * 刷新技能
	 */
	public void refreshSkill() {
		doChangeCareer(getCareer());
	}
	
	public void setStatus(int status) {
		this.status = status;
		
		switch (status) {
		case STATUS_OFF:
			jLabelStatus.setText("OFF");
			jLabelStatus.setForeground(Color.BLACK);
			break;
		case STATUS_NO_FIGHTING:
			jLabelStatus.setText("■");
			jLabelStatus.setForeground(Color.BLACK);
			break;
		case STATUS_FIGHTING:
			jLabelStatus.setText("●");
			jLabelStatus.setForeground(Color.RED);
			
			jLabelExtend.setText(EXTEND_DOWN);
			initSize();
			break;
		case STATUS_RUNNING:
			jLabelStatus.setText("RUN");
			jLabelStatus.setForeground(Color.BLACK);
			
			jLabelExtend.setText(EXTEND_DOWN);
			initSize();
			break;
		default:
			log.error("状态错误");
		}
	}
	
	private void initSize() {
		this.setSize(175, 25);
	}
	
	/**
	 * 取得状态
	 * @return
	 */
	public int getStatus() {
		return this.status;
	}
	
	public void setCase(BnsConst.CASE c) {
		this.jLabelCase.setText(c.name());
		
		this.caseKbn = c;
	}
	
	public BnsConst.CASE getCase() {
		return this.caseKbn;
	}
	
	/**
	 * 取得职业名称
	 * @return
	 */
	public BnsConst.CAREER getCareer() {
		return arrCareer[cmbCareer.getSelectedIndex()];
	}
	
	/**
	 * 取得监听处理对象
	 * @return
	 */
	public BnsHotkeyListener getBnsHotkeyListener() {
		return this.listener;
	}
	
	/**
	 * 取得线程管理对象
	 * @return
	 */
	public BnsThreadManager getBnsThreadManager() {
		return this.thManager;
	}	
	
	public void showCalculate() {
		if (EXTEND_DOWN.equals(jLabelExtend.getText())) {
			listener.doOff();
			
			jLabelExtend.setText(EXTEND_UP);
			bf.setSize(270, 90);
			
			jTxtPrice.setText("");
			jSpinnerPersionNum.setValue(new Integer("6"));
			jSpinnerNum.setValue(new Integer("1"));
			jLabelMin.setText("");
			jLabelMax.setText("");
			
		} else {
			jLabelExtend.setText(EXTEND_DOWN);
			initSize();
			listener.doOn();
		}
	}

	private class MyDocumentListener implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {			
			calculate();
		}
		
		public void insertUpdate(DocumentEvent e) {
			calculate();
		}
		
		public void removeUpdate(DocumentEvent e) {
			calculate();
		}

	}
	
	/**
	 * 计算最低和最高竞拍价格
	 */
	private void calculate() {
		if (jTxtPrice.getText() == null || "".equals(jTxtPrice.getText()) ||
			jSpinnerNum.getValue() == null || "".equals(String.valueOf(jSpinnerNum.getValue())) ||
			jSpinnerPersionNum.getValue() == null || "".equals(String.valueOf(jSpinnerPersionNum.getValue()))) {
			jLabelMax.setText("");
			jLabelMin.setText("");
			
			return;
		}
		
		BigDecimal price = new BigDecimal(jTxtPrice.getText()); //单价
		BigDecimal num = new BigDecimal(String.valueOf(jSpinnerNum.getValue())); //数量
		BigDecimal personNum = new BigDecimal(String.valueOf(jSpinnerPersionNum.getValue())); //队伍人数
		
		BigDecimal max = price.multiply(num);
		max = max.divide(personNum, 3, BigDecimal.ROUND_HALF_UP);
		max = max.multiply(personNum.subtract(BigDecimal.ONE));
		
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
		 
		jLabelMax.setText(myformat.format(max.doubleValue()));
		
		jLabelMin.setText(myformat.format(max.multiply(new BigDecimal("0.95")).divide(new BigDecimal("1.1"), 3, BigDecimal.ROUND_HALF_UP).doubleValue()));
	}
	
	private static void loadDll(String dllName) throws JIntellitypeException {
		String dllPath = BnsConst.ROOT_PATH.concat("dll").concat(File.separator).concat(dllName);
		
		JIntellitype.setLibraryLocation(dllPath);
		
		if (JIntellitype.getLibraryLocation() == null) {
			JIntellitype.setLibraryLocation(new File(dllPath));
		}
		
		if (!JIntellitype.checkInstanceAlreadyRunning("BnsFrame")) {
			try {
				Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
				logger.setLevel(Level.OFF);
	            GlobalScreen.registerNativeHook();
	            
	            Handler[] handlers = Logger.getLogger("").getHandlers();
	            for (int i = 0; i < handlers.length; i++) {
	                handlers[i].setLevel(Level.OFF);
	            }
	        }
	        catch (NativeHookException ex) {
	            System.exit(1);
	        }
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					BnsFrame frame = BnsFrame.getInstance();
					frame.init();
					frame.setVisible(true);
				}
			});
			
		} else {
			System.exit(0);
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure(BnsConst.ROOT_PATH.concat("resource").concat(File.separator).concat("log4j.properties"));
		
		String os = System.getProperty("os.arch");
		if (os.indexOf("64") >= 0) {
			loadDll("JIntellitype64.dll");
		} else {
			loadDll("JIntellitype.dll");
		}
		
	}

}
