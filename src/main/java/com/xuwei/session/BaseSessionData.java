
package com.xuwei.session;

import java.io.Serializable;

import org.apache.log4j.Logger;
/**
 * 
 * 
 * <b>类名称：</b>BaseSessionData<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年3月13日 下午2:30:54<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public class BaseSessionData implements Serializable {
	private transient Logger log = Logger.getLogger(BaseSessionData.class);

	private static final long serialVersionUID = 8981221809545051405L;

	private final long creationTimeMillis;
	private long lastAccessTimeMillis;
	private int maxInactiveIntervalSeconds;

	public BaseSessionData(long creationTimeMillis, int maxInactiveIntervalSeconds) {
		this.creationTimeMillis = creationTimeMillis;
		this.maxInactiveIntervalSeconds = maxInactiveIntervalSeconds;
		this.lastAccessTimeMillis = 0L;
	}

	public long getCreationTimeMillis() {
		return this.creationTimeMillis;
	}

	public int getMaxInactiveIntervalSeconds() {
		return this.maxInactiveIntervalSeconds;
	}

	public void setMaxInactiveIntervalSeconds(int maxInactiveInterval) {
		this.maxInactiveIntervalSeconds = maxInactiveInterval;
	}

	public long getLastAccessTimeMillis() {
		return this.lastAccessTimeMillis;
	}

	public void setLastAccessTimeMillis(long lastAccessTimeMillis) {
		this.lastAccessTimeMillis = lastAccessTimeMillis;
	}

	public boolean isExpired(String id, boolean debug) {
		long now = System.currentTimeMillis();
		long maxInactive = this.maxInactiveIntervalSeconds * 1000L;
		long idle = now - this.lastAccessTimeMillis;
		boolean expired = idle > maxInactive;

		if (maxInactive < 0L) {
			if (debug) {
				log.info(new StringBuilder().append("Session ").append(id)
						.append(" is set to never expire").toString());
			}
			return false;
		}

		if (debug) {
			log.info(new StringBuilder().append("Session ").append(id)
					.append(" is ").append(expired ? "" : "NOT ")
					.append("expired (idle=").append(idle)
					.append(",maxInactive=").append(maxInactive).toString());
		}

		return expired;
	}

}
