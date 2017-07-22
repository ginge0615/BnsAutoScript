package bns;

import java.awt.Color;
import java.awt.Robot;
import java.util.HashMap;
import java.util.regex.Pattern;

import bns.bean.ColorBean;
import bns.properties.BnsPropertiesColor;
import bns.properties.BnsPropertiesConfig;

public class BnsUtil {
	public static final BnsPropertiesConfig PROP_CONFIG = BnsPropertiesConfig.getInstance();
	public static final boolean IS_KEY_AGENT = "TRUE".equalsIgnoreCase(PROP_CONFIG.getProperty("KEY_AGENT"));
	public static final boolean COLOR_CHECK = "TRUE".equalsIgnoreCase(PROP_CONFIG.getProperty("COLOR_CHECK"));
	private static final int COLOR_OFFSET = PROP_CONFIG.getPropertyInt("COLOR_OFFSET");
	
	private static final Pattern P_NUM = Pattern.compile("[0-9]+");
	private static HashMap<String, ColorBean[]> mapColor = BnsPropertiesColor.getInstance().getColorMap();

	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
	    return P_NUM.matcher(str).matches();   
	}
	
	/**
	 * 去除连续空格
	 * @param str
	 * @return
	 */
	public static String removeNonBreakSpace(String str) {
		return str.trim().replaceAll("\\s{2,}", " ");
	}
	
	public static boolean hasColor(String colorName) {
		return mapColor.containsKey(colorName);
	}
	
	/**
	 * 取色check
	 * @param colorName
	 * @return
	 */
	public static boolean isColorOK(String colorName, Robot r) {
		if (!hasColor(colorName)) {
			return false;
		}
		
		for (ColorBean cb : mapColor.get(colorName)) {
			Color color = r.getPixelColor(cb.getX(), cb.getY());
			
			boolean isSameColor = Math.abs(color.getRed() - cb.getRed())  <= COLOR_OFFSET 
					&& Math.abs(color.getGreen() - cb.getGreen())  <= COLOR_OFFSET
					&& Math.abs(color.getBlue() - cb.getBlue())  <= COLOR_OFFSET;
			
			if ((!cb.isExceptThisColor() && isSameColor)
					|| (cb.isExceptThisColor() && !isSameColor)) {
				return true;
			}
		}

		return false;
	}
	
}
