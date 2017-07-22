package bns.properties;

import bns.BnsConst;
 
/**
 * 各种设定值取得用
 * @author jianghb
 *
 */
public class BnsPropertiesConfig extends PropertiesAbstract {
	private static final long serialVersionUID = -3104136375768327879L;
	private static BnsPropertiesConfig obj;
	
	public static BnsPropertiesConfig getInstance() {
		if (obj == null) {
			obj = new BnsPropertiesConfig();
			obj.load(BnsConst.RESOURCE_PATH  + "config.properties");
		}
		
		return obj;
	}
}