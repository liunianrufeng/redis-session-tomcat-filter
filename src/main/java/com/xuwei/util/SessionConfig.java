/**
 * <b>项目名：</b>redis-session-tomcat-filter<br/>
 * <b>包名：</b>com.xuwei.util<br/>
 * <b>文件名：</b>SessionConfig.java<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>版本信息：</b><br/>
 * <b>日期：</b>2015年5月25日-下午5:17:02<br/>
 * <b>Copyright (c)</b> 2015XX公司-版权所有<br/>
 * 
 */
package com.xuwei.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * 
 * <b>类名称：</b>SessionConfig<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年5月25日 下午5:17:02<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class SessionConfig {
	private static Properties props = new Properties(); 
	static{
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource("sessionparameter.properties");
			if(url ==null){
				props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sessionparameter_default.properties"));
			}else{
				props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sessionparameter.properties"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getValue(String key){
		return props.getProperty(key);
	}

    public static void updateProperties(String key,String value) {    
            props.setProperty(key, value); 
    } 
    
    public static void main(String[] args) {
		System.out.println(SessionConfig.getValue("cookie.domain"));
	}
}
