package bns.test;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import bns.BnsConst;

public class BnsTest extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panel = null;
	
	private JTextArea jTxtArea = null;
	private JScrollPane jspane = null;
	private JScrollBar bar = null;
	
	private Long preTime = 0L;
//	private Point p = new Point();
	
	private HashMap<Integer, String> skillMapQigongFire = null;
	private HashMap<Integer, String> skillMapQigongIce = null;
	private HashMap<Integer, String> skillMapLish = null;
	private HashMap<Integer, String> skillMapZhoushu = null;
	private HashMap<Integer, String> skillMapZhaohuan = null;
	
	private boolean isQigong = false;
	
	private boolean isFireSkill = false;
	private BnsConst.CAREER career;
	
	private int cntLeftMouse = 0;
	
	public BnsTest(){
		super();
		initialize();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub				
				//if (JOptionPane.showConfirmDialog(getComponent(0), "Sure To Quit ?", "Confirm", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
					
				System.exit(0);
				//}
			}
		});

		initSkillMap();
		
		jTxtArea = new JTextArea();
		jTxtArea.setEditable(false);
		jTxtArea.setLineWrap(true);
		jTxtArea.setWrapStyleWord(true);
		jTxtArea.setFont(new Font("SimHei", Font.PLAIN, 12));
		
		jTxtArea.addKeyListener(new TxtKeyAdapter());
		jTxtArea.addMouseListener(new TxtMouseAdapter());
		
		jspane = new JScrollPane(jTxtArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspane.setPreferredSize(new Dimension(563, 600));
		jspane.setSize(new Dimension(563, 600));
		jspane.setLocation(new Point(0, 0));
		bar = jspane.getVerticalScrollBar();
		
		panel.add(jspane);
		panel.add(getBtnClear(), null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void initialize() {
		this.setSize(579, 700);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.setResizable(false);
	}
	
	private JPanel getJContentPane() {
		panel = new JPanel();
		panel.setLayout(null);
		return panel;
	}
	
	private JButton getBtnClear() {
		JButton btnSave = new JButton();
			btnSave.setBounds(new Rectangle(437, 620, 105, 28));
			btnSave.setFont(new Font("SimSun", Font.PLAIN, 18));
			btnSave.setText("Clear");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					clear();
				}
			});
		return btnSave;
	}
	
	public void clear() {
		jTxtArea.setText("");
	}
	
	private class TxtKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			String skill = "";
			
			if (BnsConst.CAREER.冰气功.equals(career)) {
				if (e.getKeyCode() == KeyEvent.VK_T || e.getKeyCode() == KeyEvent.VK_SEMICOLON) {
					isFireSkill = true;
				} else if (e.getKeyCode() == KeyEvent.VK_Y || e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_QUOTE) {
					isFireSkill = false;
				}
				
				if (isFireSkill) {
					skill = skillMapQigongFire.get(e.getKeyCode());
				} else {
					skill = skillMapQigongIce.get(e.getKeyCode());
				}
				
			} else if (BnsConst.CAREER.力士.equals(career)) {
				skill = skillMapLish.get(e.getKeyCode());
			} else if (BnsConst.CAREER.咒术士.equals(career)) {
				skill = skillMapZhoushu.get(e.getKeyCode());
				if (String.valueOf(e.getKeyChar()).toUpperCase().equals(";")) {
					if (cntLeftMouse < 3) {
						cntLeftMouse++;
					}
					
					if (cntLeftMouse == 2) {
						skill = "爆";
					} else if (cntLeftMouse >= 3) {
						skill = "真言";
					}
				} else {
					cntLeftMouse = 0;
				}
			} else if (BnsConst.CAREER.召唤士.equals(career)) {
				skill = skillMapZhaohuan.get(e.getKeyCode());
			}
			jTxtArea.append(padString(String.valueOf(e.getKeyChar()).toUpperCase(), 12) + "技能=" + padString(skill,30) + "间隔=" + getSpace() + "\n");
//			jTxtArea.append(e.getKeyChar() + " " + KeyEvent.getKeyText(e.getKeyCode()).toUpperCase() + " " + e.getKeyCode() + "\n");
			jTxtArea.invalidate();
			bar.setValue(bar.getMaximum());
		
		}
	}
	
	private String padString(String str, int len) {
		String rtnString;
		final String pAD_String = "                                                            ";
		if (str == null) str = "";
		if (str.getBytes().length < len) {
			rtnString = new String(str.concat(pAD_String).getBytes(), 0, len);
		} else {
			rtnString = str;
		}
		
		return rtnString;
	}
	
	private class TxtMouseAdapter extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1 ) {
				isFireSkill = true;
				jTxtArea.append(padString("左键", 42)+ "间隔：" + getSpace() + "\n");
			} else if (e.getButton() == MouseEvent.BUTTON3 ) {
				isFireSkill = false;
				jTxtArea.append(padString("右键", 42)+ "间隔：" + getSpace() + "\n");
			}
			
			bar.setValue(bar.getMaximum());
		}
	}
	
	private long getSpace() {
		long nowTime = System.currentTimeMillis();
		long space = nowTime - preTime;
		preTime = nowTime;
		return space;
		
	}
	
	private void initSkillMap() {
		HashMap<Integer, String> skillMapQigongCommon = new HashMap<Integer, String>();
		skillMapQigongCommon.put(KeyEvent.VK_1, "气功炮");
		skillMapQigongCommon.put(KeyEvent.VK_9, "气功炮");		
		skillMapQigongCommon.put(KeyEvent.VK_3, "冰龙吟/冰白寒炮");
		skillMapQigongCommon.put(KeyEvent.VK_MINUS, "冰龙吟/冰白寒炮");
		skillMapQigongCommon.put(KeyEvent.VK_4, "重力掌");
		skillMapQigongCommon.put(KeyEvent.VK_EQUALS, "重力掌");
		skillMapQigongCommon.put(KeyEvent.VK_6, "吃药");
		skillMapQigongCommon.put(KeyEvent.VK_7, "吃药");
		skillMapQigongCommon.put(KeyEvent.VK_COMMA, "吃药");
		skillMapQigongCommon.put(KeyEvent.VK_PERIOD, "吃药");
		skillMapQigongCommon.put(KeyEvent.VK_T, "霜火步");
		skillMapQigongCommon.put(KeyEvent.VK_Y, "霜火步");
		skillMapQigongCommon.put(KeyEvent.VK_W, "奔跑");
		skillMapQigongCommon.put(KeyEvent.VK_UP, "奔跑");
		skillMapQigongCommon.put(KeyEvent.VK_S, "逆风行");
		skillMapQigongCommon.put(KeyEvent.VK_SEMICOLON, "烈火掌");
		skillMapQigongCommon.put(KeyEvent.VK_QUOTE, "寒冰掌");
		skillMapQigongCommon.put(KeyEvent.VK_2, "炎龙啸/冰河神掌");
		skillMapQigongCommon.put(KeyEvent.VK_0, "炎龙啸/冰河神掌");
		
		skillMapQigongFire = new HashMap<Integer, String>();
		skillMapQigongFire.putAll(skillMapQigongCommon);
		skillMapQigongFire.put(KeyEvent.VK_Z, "星星之火");
		skillMapQigongFire.put(KeyEvent.VK_N, "星星之火");
		skillMapQigongFire.put(KeyEvent.VK_B, "火莲掌");
		skillMapQigongFire.put(KeyEvent.VK_X, "火莲掌");
		skillMapQigongFire.put(KeyEvent.VK_C, "莲花指");
		skillMapQigongFire.put(KeyEvent.VK_H, "莲花指");
		skillMapQigongFire.put(KeyEvent.VK_V, "流星指");
		skillMapQigongFire.put(KeyEvent.VK_L, "流星指");
		skillMapQigongFire.put(KeyEvent.VK_TAB, "燎原之火");
		skillMapQigongFire.put(KeyEvent.VK_OPEN_BRACKET, "燎原之火");
		
		
		skillMapQigongIce = new HashMap<Integer, String>();
		skillMapQigongIce.putAll(skillMapQigongCommon);
		skillMapQigongIce.put(KeyEvent.VK_Z, "破冰");
		skillMapQigongIce.put(KeyEvent.VK_N, "破冰");
		skillMapQigongIce.put(KeyEvent.VK_B, "冰河掌");
		skillMapQigongIce.put(KeyEvent.VK_X, "冰河掌");
		skillMapQigongIce.put(KeyEvent.VK_C, "气功罩");
		skillMapQigongIce.put(KeyEvent.VK_H, "气功罩");
		skillMapQigongIce.put(KeyEvent.VK_V, "冰牢");
		skillMapQigongIce.put(KeyEvent.VK_L, "冰牢");
		skillMapQigongIce.put(KeyEvent.VK_TAB, "冰箱");
		skillMapQigongIce.put(KeyEvent.VK_OPEN_BRACKET, "冰箱");
		
		skillMapLish = new HashMap<Integer, String>();
		skillMapLish.put(KeyEvent.VK_1, "撼地");
		skillMapLish.put(KeyEvent.VK_9, "撼地");
		skillMapLish.put(KeyEvent.VK_2, "冲锋");
		skillMapLish.put(KeyEvent.VK_0, "冲锋");
		skillMapLish.put(KeyEvent.VK_3, "天崩地裂");
		skillMapLish.put(KeyEvent.VK_MINUS, "天崩地裂");
		skillMapLish.put(KeyEvent.VK_4, "灭绝");
		skillMapLish.put(KeyEvent.VK_EQUALS, "灭绝");
		skillMapLish.put(KeyEvent.VK_Z, "碎金脚");
		skillMapLish.put(KeyEvent.VK_N, "碎金脚");
		skillMapLish.put(KeyEvent.VK_X, "霸王击鼎/执行");
		skillMapLish.put(KeyEvent.VK_B, "霸王击鼎/执行");
		skillMapLish.put(KeyEvent.VK_C, "烈火轮");
		skillMapLish.put(KeyEvent.VK_H, "烈火轮");
		skillMapLish.put(KeyEvent.VK_V, "金刚不坏");
		skillMapLish.put(KeyEvent.VK_L, "金刚不坏");
		skillMapLish.put(KeyEvent.VK_TAB, "狂风");
		skillMapLish.put(KeyEvent.VK_OPEN_BRACKET, "狂风");
		skillMapLish.put(KeyEvent.VK_Y, "愤怒");
		skillMapLish.put(KeyEvent.VK_W, "奔跑");
		skillMapLish.put(KeyEvent.VK_UP, "奔跑");
		skillMapLish.put(KeyEvent.VK_S, "逆风行");
		skillMapLish.put(KeyEvent.VK_SEMICOLON, "削骨");
		skillMapLish.put(KeyEvent.VK_QUOTE, "毁灭/歼灭");
		skillMapLish.put(KeyEvent.VK_F, "扼喉");
		skillMapLish.put(KeyEvent.VK_G, "扼喉");
		
		skillMapZhoushu = new HashMap<Integer, String>();
		skillMapZhoushu.put(KeyEvent.VK_1, "断空");
		skillMapZhoushu.put(KeyEvent.VK_9, "断空");
		skillMapZhoushu.put(KeyEvent.VK_2, "戒行枷");
		skillMapZhoushu.put(KeyEvent.VK_0, "戒行枷");
		skillMapZhoushu.put(KeyEvent.VK_3, "神凝咒");
		skillMapZhoushu.put(KeyEvent.VK_MINUS, "神凝咒");
		skillMapZhoushu.put(KeyEvent.VK_4, "苍龙应天");
		skillMapZhoushu.put(KeyEvent.VK_EQUALS, "苍龙应天");
		skillMapZhoushu.put(KeyEvent.VK_Z, "保护符");
		skillMapZhoushu.put(KeyEvent.VK_N, "保护符");
		skillMapZhoushu.put(KeyEvent.VK_X, "警戒斩");
		skillMapZhoushu.put(KeyEvent.VK_B, "警戒斩");
		skillMapZhoushu.put(KeyEvent.VK_C, "降魔阵");
		skillMapZhoushu.put(KeyEvent.VK_H, "降魔阵");
		skillMapZhoushu.put(KeyEvent.VK_V, "死灵降临");
		skillMapZhoushu.put(KeyEvent.VK_L, "死灵降临");
		skillMapZhoushu.put(KeyEvent.VK_TAB, "毁灭");
		skillMapZhoushu.put(KeyEvent.VK_OPEN_BRACKET, "召唤兽");
		skillMapZhoushu.put(KeyEvent.VK_W, "奔跑");
		skillMapZhoushu.put(KeyEvent.VK_S, "逆风行");
		skillMapZhoushu.put(KeyEvent.VK_SEMICOLON, "炎");
		skillMapZhoushu.put(KeyEvent.VK_QUOTE, "次元弹");
		
		skillMapZhaohuan = new HashMap<Integer, String>();
		skillMapZhaohuan.put(KeyEvent.VK_1, "常青藤");
		skillMapZhaohuan.put(KeyEvent.VK_9, "常青藤");
		skillMapZhaohuan.put(KeyEvent.VK_2, "荆棘藤");
		skillMapZhaohuan.put(KeyEvent.VK_0, "荆棘藤");
		skillMapZhaohuan.put(KeyEvent.VK_3, "花粉");
		skillMapZhaohuan.put(KeyEvent.VK_MINUS, "花粉");
		skillMapZhaohuan.put(KeyEvent.VK_4, "蒲公英");
		skillMapZhaohuan.put(KeyEvent.VK_EQUALS, "蒲公英");
		skillMapZhaohuan.put(KeyEvent.VK_Z, "鼓励");
		skillMapZhaohuan.put(KeyEvent.VK_N, "鼓励");
		skillMapZhaohuan.put(KeyEvent.VK_X, "友情");
		skillMapZhaohuan.put(KeyEvent.VK_B, "友情");
		skillMapZhaohuan.put(KeyEvent.VK_C, "大榔头");
		skillMapZhaohuan.put(KeyEvent.VK_H, "大榔头");
		skillMapZhaohuan.put(KeyEvent.VK_V, "下段斩");
		skillMapZhaohuan.put(KeyEvent.VK_L, "下段斩");
		skillMapZhaohuan.put(KeyEvent.VK_TAB, "喵喵出击");
		skillMapZhaohuan.put(KeyEvent.VK_OPEN_BRACKET, "喵喵出击");
		skillMapZhaohuan.put(KeyEvent.VK_W, "奔跑");
		skillMapZhaohuan.put(KeyEvent.VK_S, "逆风行");
		skillMapZhaohuan.put(KeyEvent.VK_SEMICOLON, "玫瑰");
		skillMapZhaohuan.put(KeyEvent.VK_QUOTE, "向日葵");
		
	}
		

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new BnsTest("qigong");
	}

	public boolean isQigong() {
		return isQigong;
	}

	public void setQigong(boolean isQigong) {
		this.isQigong = isQigong;
	}
	
	public void setCareer(BnsConst.CAREER career) {
		this.career = career;
	}

}
