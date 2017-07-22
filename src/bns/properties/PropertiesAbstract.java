package bns.properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public abstract class PropertiesAbstract extends Properties {
	private static final long serialVersionUID = 6475360105180584834L;
	
	public void load(String path) {
		this.clear();
		
		try {
			File file = new File(path);
			
			if (!file.exists()) {
				return;
			}
			
			InputStream inputStream = new FileInputStream(path);
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			this.load(bf);
			inputStream.close(); // 关闭流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得值并转换成整数
	 * @param key
	 * @return key对应的值不存的话，返回-1
	 */
	public int getPropertyInt(String key) {
		if (!this.containsKey(key)) {
			return -1;
		}
		
		return Integer.parseInt(this.getProperty(key));
	}
	
	/**
	 * 整数值设定
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, int value) {
		this.setProperty(key, String.valueOf(value));
	}
}