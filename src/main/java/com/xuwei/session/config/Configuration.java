
package com.xuwei.session.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 
 * 
 * <b>类名称：</b>Configuration<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年3月4日 下午3:21:44<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public class Configuration {
	/**
	 * 
	 */
	public static final String SERVERS = "servers";
	/**
	 * 
	 */
	public static final String MAX_IDLE = "maxIdle";
	/**
	 * 
	 */
	public static final String INIT_IDLE_CAPACITY = "initIdleCapacity";
	/**
	 * 
	 */
	public static final String SESSION_TIMEOUT = "sessionTimeout";
	/**
	 * 
	 */
	public static final String TIMEOUT = "timeout";
	/**
	 * 
	 */
	public static final String POOLSIZE = "poolSize";

	/**
	 * 
	 */
	public static final String CFG_NAME = ".cfg.properties";

	/**
	 * 
	 */
	private static Configuration instance;

	/**
	 * 
	 */
	private Properties config;

	/**
	 * 
	 * 创建一个新的实例 Configuration.
	 *
	 */
	protected Configuration() {
		this.config = new Properties();

		String basedir = System.getProperty("user.home");
		File file = new File(basedir, CFG_NAME);
		try {
			boolean exist = file.exists();
			if (!exist) {
				file.createNewFile();
			}

			this.config.load(new FileInputStream(file));

			if (!exist) {
				this.config.setProperty(SERVERS, "www.storevm.org");
				this.config.setProperty(MAX_IDLE, "8");
				this.config.setProperty(INIT_IDLE_CAPACITY, "4");
				this.config.setProperty(SESSION_TIMEOUT, "5");
				this.config.setProperty(TIMEOUT, "5000");
				this.config.setProperty(POOLSIZE, "5000");
				this.config.store(new FileOutputStream(file), "");
			}
		} catch (Exception ex) {
			// do nothing...
		}
	}

	/**
	 * 
	 * getInstance(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @return 
	 * Configuration
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}

	/**
	 * 
	 * getString(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param defaultValue
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public String getString(String key, String defaultValue) {
		if (config != null) {
			return config.getProperty(key) != null ? config.getProperty(key)
					: defaultValue;
		}
		return defaultValue;
	}

	/**
	 * 
	 * getString(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public String getString(String key) {
		return getString(key, null);
	}

	@Override
	public String toString() {
		return "Configuration [config=" + config + "]";
	}
}
