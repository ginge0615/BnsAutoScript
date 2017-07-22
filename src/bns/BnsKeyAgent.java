package bns;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
 
/**
 * BnsKeyAgent
 * @author jianghb
 *
 */
public class BnsKeyAgent {
	private static final String PATH = System.getProperty("user.dir") + File.separator  + "resource/map.properties";
	private static BnsKeyAgent obj;
	private Properties properties;
	private int mouseLeftCode;
	private int mouseRightCode;
	
	private HashMap<String, Integer> keyMap;
	
	private Logger logger = Logger.getLogger(BnsKeyAgent.class );
	
	public static BnsKeyAgent getInstance() {
		if (obj == null) {
			obj = new BnsKeyAgent();
		}
		
		return obj;
	}
	
	public BnsKeyAgent() {
		loadProperties();
		initKeyMap();
		mouseLeftCode = getAgentKeyCode("MOUSE_LEFT");
		mouseRightCode = getAgentKeyCode("MOUSE_RIGHT");
	}
	
	private void loadProperties() {		
		properties = new Properties();	
		try  
        {  
            InputStream inputStream = new FileInputStream(PATH);  
            BufferedReader bf=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            properties.load(bf);  
            inputStream.close(); //关闭流  
        }  
        catch (IOException e)  
        {  
        	logger.error("", e);
        }
	}
	
	private void initKeyMap() {
		keyMap = new HashMap<String, Integer>();
		for (int keyCode = KeyEvent.VK_0; keyCode <= KeyEvent.VK_9; keyCode++) {
			keyMap.put(KeyEvent.getKeyText(keyCode), keyCode);
		}
		
		for (int keyCode = KeyEvent.VK_A; keyCode <= KeyEvent.VK_Z; keyCode++) {
			keyMap.put(KeyEvent.getKeyText(keyCode), keyCode);
		}
		
		for (int keyCode = KeyEvent.VK_F1; keyCode <= KeyEvent.VK_F12; keyCode++) {
			keyMap.put(KeyEvent.getKeyText(keyCode), keyCode);
		}
		
		keyMap.put(KeyEvent.getKeyText(KeyEvent.VK_TAB).toUpperCase(), KeyEvent.VK_TAB);
		
		keyMap.put("CAPS_LOCK", KeyEvent.VK_CAPS_LOCK);
		keyMap.put(KeyEvent.getKeyText(KeyEvent.VK_SPACE).toUpperCase(), KeyEvent.VK_SPACE);
		
		keyMap.put("BACK_QUOTE", KeyEvent.VK_BACK_QUOTE);//"`"
		keyMap.put("OPEN_BRACKET", KeyEvent.VK_OPEN_BRACKET);//"["
		keyMap.put("CLOSE_BRACKET", KeyEvent.VK_CLOSE_BRACKET);//"]"
		keyMap.put("SLASH", KeyEvent.VK_SLASH);//"/"
		keyMap.put("BACK_SLASH", KeyEvent.VK_BACK_SLASH);//"\"
		keyMap.put("MINUS", KeyEvent.VK_MINUS);//-
		keyMap.put("EQUALS", KeyEvent.VK_EQUALS);//=
		keyMap.put("SEMICOLON", KeyEvent.VK_SEMICOLON);//;
		keyMap.put("QUOTE", KeyEvent.VK_QUOTE);//'
		keyMap.put("PAGE_UP", KeyEvent.VK_PAGE_UP);
		keyMap.put("PAGE_DOWN", KeyEvent.VK_PAGE_DOWN);
		keyMap.put("DELETE", KeyEvent.VK_DELETE);
		keyMap.put("COMMA", KeyEvent.VK_COMMA);
		keyMap.put("PERIOD", KeyEvent.VK_PERIOD);
		keyMap.put("INSERT", KeyEvent.VK_INSERT);
		keyMap.put("UP", KeyEvent.VK_UP);
		keyMap.put("DOWN", KeyEvent.VK_DOWN);
		
		keyMap.put("`", KeyEvent.VK_BACK_QUOTE);//"`"
		keyMap.put("[", KeyEvent.VK_OPEN_BRACKET);//"["
		keyMap.put("]", KeyEvent.VK_CLOSE_BRACKET);//"]"
		keyMap.put("/", KeyEvent.VK_SLASH);//"/"
		keyMap.put("\\", KeyEvent.VK_BACK_SLASH);//"\"
		keyMap.put("-", KeyEvent.VK_MINUS);//-
		keyMap.put("=", KeyEvent.VK_EQUALS);//=
		keyMap.put(";", KeyEvent.VK_SEMICOLON);//;
		keyMap.put("'", KeyEvent.VK_QUOTE);//'
		keyMap.put(",", KeyEvent.VK_COMMA);//，
		keyMap.put(".", KeyEvent.VK_PERIOD);//，
		keyMap.put("MOUSE_LEFT", InputEvent.BUTTON1_MASK);
		keyMap.put("MOUSE_RIGHT", InputEvent.BUTTON3_MASK);
		
	}
	
	public HashMap<String, Integer> getKeyMap() {
		return keyMap;
	}
	
	/**
	 * 取得KeyCode
	 * @param keyText
	 * @return
	 */
	public int getKeyCode(String keyText) {
		return keyMap.containsKey(keyText) ? keyMap.get(keyText) : -1;
	}
	
	/**
	 * 取得代理键的名称
	 * @param key
	 * @return
	 */
	public String getAgentKeyText(String key) {
		String strKey = key.toUpperCase();
		if (properties.containsKey(strKey)) {
			return properties.getProperty(strKey);
		}
		
		return strKey;
	}
	
	/**
	 * 取得代理按键Code
	 * 
	 * @param key
	 * @return
	 */
	public int getAgentKeyCode(String key) {
		String keyText = getAgentKeyText(key);
		int convKeyCode = -1;
		
		if (keyText != null && !"".equals(keyText) ) {
			convKeyCode = this.getKeyCode(keyText);
		} 
		
		return convKeyCode;
	}
	
	/**
	 * 取得代理按键Code
	 * @param keyCode
	 * @return
	 */
	public int getAgentKeyCode(int keyCode) {
		return getAgentKeyCode(KeyEvent.getKeyText(keyCode).toUpperCase());
	}
	
	/**
	 * 取得鼠标代理按键Code
	 * @param keyCode
	 * @return
	 */
	public int getMouseAgentCode(int keyCode) {
		if (InputEvent.BUTTON1_MASK == keyCode) {
			return mouseLeftCode;
		} else if (InputEvent.BUTTON3_MASK == keyCode) {
			return mouseRightCode;
		}
		
		return -1;
	}
	
}