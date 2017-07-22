package bns.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import bns.BnsConst;
 
/**
 * 值保存用
 * @author jianghb
 *
 */
public class BnsPropertiesSave extends PropertiesAbstract {
	private static final long serialVersionUID = -3104136375768327879L;
	private static BnsPropertiesSave obj;
	
	public static BnsPropertiesSave getInstance() {
		if (obj == null) {
			obj = new BnsPropertiesSave();
			obj.load(obj.getFullPath());
		}
		
		return obj;
	}

	/**
	 * 值保存
	 */
	public void saveProperties() {
		try {
			OutputStream os = new FileOutputStream(this.getFullPath());
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			this.store(osw, null);
			osw.close();
			os.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得资源文件的全路径
	 * @return
	 */
	private String getFullPath() {
		return BnsConst.RESOURCE_PATH  + "save.properties";
	}
}