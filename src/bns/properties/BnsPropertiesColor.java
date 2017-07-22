package bns.properties;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import bns.BnsConst;
import bns.bean.ColorBean;
 
/**
 * 取色用
 * @author jianghb
 *
 */
public class BnsPropertiesColor extends PropertiesAbstract {
	private static final long serialVersionUID = -5642025311261095799L;

	private static BnsPropertiesColor obj;
	
	private HashMap<String, ColorBean[]> map = new HashMap<String, ColorBean[]>();
	private String career;
	
	public static BnsPropertiesColor getInstance() {
		if (obj == null) {
			obj = new BnsPropertiesColor();
		}
		
		return obj;
	}
	
	public void load(String career) {
		this.career = career;
		super.load(BnsConst.SCRIPT_PATH  + this.career + File.separator + "COLOR.properties");
		
		ColorBean[] beans = null;
		map.clear();
		
		if (BnsPropertiesConfig.getInstance().containsKey("RUN_COLOR")) {
			put("RUN_COLOR", BnsPropertiesConfig.getInstance().getProperty("RUN_COLOR"));
		}
		
		Iterator<String> iter = this.stringPropertyNames().iterator();

		while (iter.hasNext()) {
			String key = iter.next();
			String value = this.getProperty(key);
			
			if (value == null || value.trim().length() == 0) {
				continue;
			}

			if (value.contains("|")) {
				String[] arr = value.split("\\|");
				beans = new ColorBean[arr.length];

				for (int i = 0; i < arr.length; i++) {
					beans[i] = toBean(arr[i]);
				}
			} else {
				beans = new ColorBean[1];
				beans[0] = toBean(value);
			}

			map.put(key.toUpperCase(), beans);
		}
	}
	
	private ColorBean toBean(String value) {
		ColorBean bean = new ColorBean();
		String[] arr = value.trim().split(",");
		
		if (arr.length == 5) {
			if (arr[0].startsWith("^")) {
				bean.setExceptThisColor(true);
				arr[0] = arr[0].substring(1);
			}
			
			bean.setX(Integer.parseInt(arr[0].trim()));
			bean.setY(Integer.parseInt(arr[1].trim()));
			bean.setRed(Integer.parseInt(arr[2].trim()));
			bean.setGreen(Integer.parseInt(arr[3].trim()));
			bean.setBlue(Integer.parseInt(arr[4].trim()));
		}
		
		return bean;
	}
	
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}
	
	public ColorBean[] getColorBean(String key) {
		return map.get(key);
	}
	
	public HashMap<String, ColorBean[]> getColorMap() {
		return this.map;
	}
}