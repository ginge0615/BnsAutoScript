package bns;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import bns.properties.BnsPropertiesConfig;

public class BnsRecordColor extends JDialog{
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BnsRecordAction.class);
	
	private static final long serialVersionUID = 3841568563671801097L;
	private JTextField jTxtName = null;
	private JTextField jTxtX = null;
	private JTextField jTxtY = null;
	private JButton jBtn = null;
	private JLabel jLabelColor = null;
	
	private static final int LINE1_Y = 5;
	private static final int LINE2_Y = 35;
	
	private File colorRecordFile = null;

	private Magnifier magnifier = null;
	
	private Robot robot;
	
	public BnsRecordColor(Frame owner) {
		super(owner, false);
		
		initialize();
		
		this.magnifier = new Magnifier(this);
		
		this.colorRecordFile = new File(BnsConst.SCRIPT_PATH.concat(BnsPropertiesConfig.getInstance().getProperty("RECORD_FILE_COLOR")));
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			log.error(e);
		}
		
	}
	
	private void initialize() {
		int width = 300;
		int height = 120;
		
		this.setSize(width, height);
		this.setContentPane(getJContentPane());
		this.setTitle("BnsColorDialog");
		this.setResizable(false);
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(new Point((d.width - width) / 2 , (d.height - height) / 2));
		
		this.setAlwaysOnTop(true);
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.getRootPane().setDefaultButton(jBtn);
		
		this.addWindowListener(new WindowAdapter() {			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				magnifier.setVisible(false);
			}
		});
	}
	
	private JPanel getJContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(getLabelName(),null);
		panel.add(getTextFieldName(),null);
		panel.add(getButton(),null);
		panel.add(getLabelX(),null);
		panel.add(getLabelY(),null);
		panel.add(getTextFieldX(),null);
		panel.add(getTextFieldY(),null);
		jLabelColor = getLabelColor();
		panel.add(jLabelColor,null);
		panel.add(getButtonRePick(), null);
		
		return panel;
	}
	
	private JLabel getLabelName() {
		JLabel jLabel = new JLabel();
		jLabel.setPreferredSize(new Dimension(70, 25));
		jLabel.setSize(new Dimension(70, 25));
		jLabel.setLocation(new Point(0, LINE2_Y));
		jLabel.setFont(new Font("SimHei", Font.PLAIN, 14));
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel.setText("颜色名称");
		
		return jLabel;
	}
	
	private JLabel getLabelColor() {
		JLabel jLabel = new JLabel();
		jLabel.setPreferredSize(new Dimension(50, 25));
		jLabel.setSize(new Dimension(50, 25));
		jLabel.setLocation(new Point(140, LINE1_Y));
		jLabel.setFont(new Font("SimHei", Font.PLAIN, 14));
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel.setOpaque(true);
		
		return jLabel;
	}
	
	private JLabel getLabelX() {
		JLabel jLabel = new JLabel();
		jLabel.setPreferredSize(new Dimension(35, 25));
		jLabel.setSize(new Dimension(35, 25));
		jLabel.setLocation(new Point(0, LINE1_Y));
		jLabel.setFont(new Font("SimHei", Font.PLAIN, 14));
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel.setText("X:");
		
		return jLabel;
	}
	private JLabel getLabelY() {
		JLabel jLabel = new JLabel();
		jLabel.setPreferredSize(new Dimension(30, 25));
		jLabel.setSize(new Dimension(30, 25));
		jLabel.setLocation(new Point(65, LINE1_Y));
		jLabel.setFont(new Font("SimHei", Font.PLAIN, 14));
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel.setText("Y:");
		
		return jLabel;
	}
	
	private JTextField getTextFieldName() {
		jTxtName = new JTextField();
		jTxtName.setPreferredSize(new Dimension(150, 25));
		jTxtName.setSize(new Dimension(150, 25));
		jTxtName.setLocation(new Point(75, LINE2_Y));
		jTxtName.setFont(new Font("SimHei", Font.PLAIN, 14));
		jTxtName.setHorizontalAlignment(SwingConstants.LEFT);
		
		return jTxtName;
	}
	
	private JTextField getTextFieldX() {
		jTxtX = new JTextField();
		jTxtX.setPreferredSize(new Dimension(40, 25));
		jTxtX.setSize(new Dimension(40, 25));
		jTxtX.setLocation(new Point(25, LINE1_Y));
		jTxtX.setFont(new Font("SimHei", Font.PLAIN, 14));
		jTxtX.setHorizontalAlignment(SwingConstants.LEFT);
		
		return jTxtX;
	}
	
	private JTextField getTextFieldY() {
		jTxtY = new JTextField();
		jTxtY.setPreferredSize(new Dimension(40, 25));
		jTxtY.setSize(new Dimension(40, 25));
		jTxtY.setLocation(new Point(90, LINE1_Y));
		jTxtY.setFont(new Font("SimHei", Font.PLAIN, 14));
		jTxtY.setHorizontalAlignment(SwingConstants.LEFT);
		
		return jTxtY;
	}
	
	private JButton getButton() {
		jBtn = new JButton();
		jBtn.setPreferredSize(new Dimension(50, 23));
		jBtn.setSize(new Dimension(50, 23));
		jBtn.setLocation(new Point(230, LINE2_Y));
		jBtn.setFont(new Font("SimHei", Font.PLAIN, 14));
		jBtn.setHorizontalAlignment(SwingConstants.LEFT);
		jBtn.setText("OK");
		
		jBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				writeColor();
			}
		});
		
		return jBtn;
	}
	
	private JButton getButtonRePick() {
		JButton btn = new JButton();
		btn.setPreferredSize(new Dimension(80, 23));
		btn.setSize(new Dimension(80, 23));
		btn.setLocation(new Point(210, LINE1_Y));
		btn.setFont(new Font("SimHei", Font.PLAIN, 14));
		btn.setHorizontalAlignment(SwingConstants.LEFT);
		btn.setText("再取色");
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				magnifier.setVisible(false);
				setVisible(false);
				
				doShow(getPickX(), getPickY());
			}
		});
		
		return btn;
	}
	
	public void doShow() {
		//鼠标位置
		Point mousepoint = MouseInfo.getPointerInfo().getLocation();
		
		doShow(mousepoint.x, mousepoint.y);
	}
	
	private void doShow(int x, int y) {
		// 截全屏
		BufferedImage screenImage = robot.createScreenCapture(new Rectangle(0,
				0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit
						.getDefaultToolkit().getScreenSize().height));

		magnifier.doShow(screenImage, x, y);

		this.jTxtName.setText("");
		this.jTxtName.setFocusable(true);
		this.jTxtX.setText(String.valueOf(x));
		this.jTxtY.setText(String.valueOf(y));
		this.jLabelColor.setBackground(magnifier.getPixedColor());

		this.setVisible(true);
	}
	
	public void setPickX(int x) {
		this.jTxtX.setText(String.valueOf(x));
	}
	
	public void setPickY(int y) {
		this.jTxtY.setText(String.valueOf(y));
	}
	
	public void setColor(Color color) {
		this.jLabelColor.setBackground(color);
	}
	
	public int getPickX() {
		return Integer.parseInt(this.jTxtX.getText());
	}
	
	public int getPickY() {
		return Integer.parseInt(this.jTxtY.getText());
	}
	
	/**
	 * 开始取颜色
	 */
	private void writeColor() {
		OutputStreamWriter writer = null;
		StringBuffer buf = new StringBuffer();
		
		try {
			
			Color color = jLabelColor.getBackground();
			
			buf.append(this.jTxtName.getText()).append("=");
			buf.append(this.jTxtX.getText()).append(",");
			buf.append(this.jTxtY.getText()).append(",");
			buf.append(color.getRed()).append(",");
			buf.append(color.getGreen()).append(",");
			buf.append(color.getBlue()).append("\r\n");
			
			OutputStream os = new FileOutputStream(colorRecordFile, true);
			writer = new OutputStreamWriter(os , "UTF-8");
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

class Magnifier extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3902591950224693309L;

	/**
	 * 主容器
	 */
	private Container container = getContentPane();

	/**
	 * 放大镜x坐标 计算方式：setCoordinateX = absoluteCoordinateX -
	 * relativeCoordinateXWhenMousePressed
	 */
	private int setCoordinateX;

	/**
	 * 放大镜y坐标 计算方式：setCoordinateY = absoluteCoordinateY -
	 * relativeCoordinateYWhenMousePressed
	 */
	private int setCoordinateY;

	/**
	 * 鼠标绝对x坐标
	 */
	private int absoluteCoordinateX;

	/**
	 * 鼠标绝对y坐标
	 */
	private int absoluteCoordinateY;

	/**
	 * 鼠标按下时的相对x坐标
	 */
	private int relativeCoordinateXWhenMousePressed;

	/**
	 * 鼠标按下时的相对y坐标
	 */
	private int relativeCoordinateYWhenMousePressed;

	/**
	 * 标记鼠标是否按下。如果按下则为true，否则为false
	 */
	private boolean mousePressedNow;

	/**
	 * 放大镜尺寸
	 */
	private static final int MAGNIFIERSIZE = 200;
	
	/**
	 * 中心点相对位置
	 */
	private static final int CENTER_POINT = MAGNIFIERSIZE / 2;


	/**
	 * 放大镜内容面板
	 */
	private MagnifierPanel magnifierPanel;
	
	private BnsRecordColor parent;
	
	public Magnifier(BnsRecordColor parent) {
		this.parent = parent;
		
		setUndecorated(true); // 禁用窗体装饰
		setResizable(false);
		magnifierPanel = new MagnifierPanel(MAGNIFIERSIZE);
		magnifierPanel.setBorder(new LineBorder(Color.RED, 2));
		container.add(magnifierPanel);
		addMouseListener(new MouseFunctions());
		addMouseMotionListener(new MouseMotionFunctions());
		addKeyListener(new KeyListener());
		
	}
	
	public void doShow(BufferedImage screenImage, int x, int y) {
		setCoordinateX = x - CENTER_POINT;
		setCoordinateY = y - CENTER_POINT;
		setLocation(setCoordinateX,setCoordinateY);
		
		magnifierPanel.setScreenImage(screenImage);
		magnifierPanel.setMagnifierLocation(setCoordinateX,setCoordinateY);
		
		updateSize(MAGNIFIERSIZE);
		
		this.setVisible(true);
	}

	/**
	 * 更新窗体
	 * 
	 * @param magnifierSize
	 *            放大镜尺寸
	 */
	public void updateSize(int magnifierSize) {
		magnifierPanel.setMagnifierSize(magnifierSize);
		setSize(magnifierSize, magnifierSize);
		validate(); // 更新所有子控件
	}

	private class MouseFunctions extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.getClickCount() == 1) {// 如果鼠标左键点了一下，说明按住了窗体
				mousePressedNow = true;
				relativeCoordinateXWhenMousePressed = e.getX();
				relativeCoordinateYWhenMousePressed = e.getY();
			}
		}

		public void mouseReleased(MouseEvent e) {
			mousePressedNow = false;
			
			parent.setPickX(getPixedColorX());
			parent.setPickY(getPixedColorY());
			parent.setColor(getPixedColor());
			
		}
	}

	private class MouseMotionFunctions extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			if (mousePressedNow == true) {// 如果此时鼠标按下了，说明在拖拽窗体
				absoluteCoordinateX = Magnifier.this.getLocationOnScreen().x
						+ e.getX();
				absoluteCoordinateY = Magnifier.this.getLocationOnScreen().y
						+ e.getY();
				setCoordinateX = absoluteCoordinateX
						- relativeCoordinateXWhenMousePressed;
				setCoordinateY = absoluteCoordinateY
						- relativeCoordinateYWhenMousePressed;
				magnifierPanel.setMagnifierLocation(setCoordinateX,
						setCoordinateY);
				setLocation(setCoordinateX, setCoordinateY);
				
			}
		}
	}
	
	private class KeyListener extends KeyAdapter {
		
		private static final int OFFSET = 1;
		
		public void keyPressed(KeyEvent e) {
			
			switch (e.getKeyCode()) {
			case  KeyEvent.VK_LEFT:
				setCoordinateX -= OFFSET;
				break;
			case  KeyEvent.VK_RIGHT:
				setCoordinateX += OFFSET;
				break;
			case  KeyEvent.VK_UP:
				setCoordinateY -= OFFSET;
				break;
			case  KeyEvent.VK_DOWN:
				setCoordinateY += OFFSET;
				break;
			default:
				return;
			}
			
			magnifierPanel.setMagnifierLocation(setCoordinateX, setCoordinateY);
			setLocation(setCoordinateX, setCoordinateY);
		}
		
		public void keyReleased(KeyEvent e) {
			parent.setPickX(getPixedColorX());
			parent.setPickY(getPixedColorY());
			parent.setColor(getPixedColor());
		}

	}
	
	public int getPixedColorX() {
		return setCoordinateX + CENTER_POINT;
	}
	
	public int getPixedColorY() {
		return setCoordinateY + CENTER_POINT;
	}
	
	public Color getPixedColor() {
		return magnifierPanel.getColor(getPixedColorX(), getPixedColorY());
	}
}

class MagnifierPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3944847444424251937L;

	private BufferedImage screenImage;

	/**
	 * 放大镜的尺寸
	 */
	private int magnifierSize;

	private int locationX;

	private int locationY;
	
	private int pointSize = 60;
	
	
	/**
	 * 放大率
	 */
	private static final int ZOOM = 4;

	/**
	 * 带参数的构造函数
	 * 
	 * @param magnifierSize
	 *            放大尺寸
	 */
	public MagnifierPanel(int magnifierSize) {
		this.magnifierSize = magnifierSize;
	}

	/**
	 * 设置放大镜的位置
	 * 
	 * @param locationX
	 *            x坐标
	 * @param locationY
	 *            y坐标
	 */
	public void setMagnifierLocation(int locationX, int locationY) {
		this.locationX = locationX;
		this.locationY = locationY;
		repaint(); // 注意重画控件
	}

	/**
	 * 设置放大镜的尺寸
	 * 
	 * @param magnifierSize
	 *            放大镜尺寸
	 */
	public void setMagnifierSize(int magnifierSize) {
		this.magnifierSize = magnifierSize;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent((Graphics2D) g);
		
		int centerPoint = magnifierSize / 2;
		
		// 关键处理代码
		g.drawImage(screenImage, // 要画的图片
				0, // 目标矩形的第一个角的x坐标
				0, // 目标矩形的第一个角的y坐标
				magnifierSize, // 目标矩形的第二个角的x坐标
				magnifierSize, // 目标矩形的第二个角的y坐标
				locationX + (centerPoint - magnifierSize / (ZOOM * 2)), // 源矩形的第一个角的x坐标
				locationY + (centerPoint - magnifierSize / (ZOOM * 2)), // 源矩形的第一个角的y坐标
				locationX + (centerPoint + magnifierSize / (ZOOM * 2)), // 源矩形的第二个角的x坐标
				locationY + (centerPoint + magnifierSize / (ZOOM * 2)), // 源矩形的第二个角的y坐标
				this);
		
		drawThickLine(g, centerPoint - pointSize, centerPoint, centerPoint + pointSize, centerPoint, 1, Color.BLACK);
		drawThickLine(g, centerPoint, centerPoint - pointSize, centerPoint, centerPoint + pointSize, 1, Color.BLACK);
	}
	
	/**
	 * 取得坐标颜色
	 * @param x
	 * @param y
	 * @return
	 */
	public Color getColor(int x, int y) {
		return new Color(screenImage.getRGB(x, y));
	}
	
	private void drawThickLine(Graphics g, int x1, int y1, int x2, int y2,
			int thickness, Color c) {
		// The thick line is in fact a filled polygon
		g.setColor(c);
		int dX = x2 - x1;
		int dY = y2 - y1;
		// line length
		double lineLength = Math.sqrt(dX * dX + dY * dY);

		double scale = (double) (thickness) / (2 * lineLength);

		// The x and y increments from an endpoint needed to create a
		// rectangle...
		double ddx = -scale * (double) dY;
		double ddy = scale * (double) dX;
		ddx += (ddx > 0) ? 0.5 : -0.5;
		ddy += (ddy > 0) ? 0.5 : -0.5;
		int dx = (int) ddx;
		int dy = (int) ddy;

		// Now we can compute the corner points...
		int xPoints[] = new int[4];
		int yPoints[] = new int[4];

		xPoints[0] = x1 + dx;
		yPoints[0] = y1 + dy;
		xPoints[1] = x1 - dx;
		yPoints[1] = y1 - dy;
		xPoints[2] = x2 - dx;
		yPoints[2] = y2 - dy;
		xPoints[3] = x2 + dx;
		yPoints[3] = y2 + dy;

		g.fillPolygon(xPoints, yPoints, 4);
	}

	public BufferedImage getScreenImage() {
		return screenImage;
	}

	public void setScreenImage(BufferedImage screenImage) {
		this.screenImage = screenImage;
	}
}
