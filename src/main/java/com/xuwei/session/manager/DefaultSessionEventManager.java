/**
 * <b>项目名：</b>redis-session-tomcat-filter<br/>
 * <b>包名：</b>com.xuwei.session.manager<br/>
 * <b>文件名：</b>DefaultSessionEventManager.java<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>版本信息：</b><br/>
 * <b>日期：</b>2015年5月23日-下午2:44:42<br/>
 * <b>Copyright (c)</b> 2015XX公司-版权所有<br/>
 * 
 */
package com.xuwei.session.manager;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * <b>类名称：</b>DefaultSessionEventManager<br/>
 * <b>类描述：</b>默认的session事件触发实现<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年5月23日 下午2:44:42<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class DefaultSessionEventManager implements SessionEventManager{

	

	/**
	 * 
	 */
	private HttpSessionListener[] sessionListeners;
	
	/**
	 * 
	 */
	private HttpSessionAttributeListener[] sessionAttributeListeners;
	
	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#sessionCreatedEvent(javax.servlet.http.HttpSession)    
	 */
	@Override
	public void sessionCreatedEvent(HttpSession session) {
		HttpSessionEvent e = new HttpSessionEvent(session);
		for (HttpSessionListener l : this.sessionListeners) {
			if (l == null)
				continue;
			l.sessionCreated(e);
		}
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#sessionDestroyedEvent(javax.servlet.http.HttpSession)    
	 */
	@Override
	public void sessionDestroyedEvent(HttpSession paramHttpSession) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#unbindAttribute(javax.servlet.http.HttpSession, java.lang.String, java.lang.Object)    
	 */
	@Override
	public void unbindAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#bindAttribute(javax.servlet.http.HttpSession, java.lang.String, java.lang.Object)    
	 */
	@Override
	public void bindAttribute(HttpSession paramHttpSession, String paramString,
			Object paramObject) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#setAttribute(javax.servlet.http.HttpSession, java.lang.String, java.lang.Object)    
	 */
	@Override
	public void setAttribute(HttpSession paramHttpSession, String paramString,
			Object paramObject) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#removeAttribute(javax.servlet.http.HttpSession, java.lang.String, java.lang.Object)    
	 */
	@Override
	public void removeAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#replaceAttribute(javax.servlet.http.HttpSession, java.lang.String, java.lang.Object, java.lang.Object)    
	 */
	@Override
	public void replaceAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject1, Object paramObject2) {
		// TODO Auto-generated method stub
		
	}

	/**    
	 * sessionListeners    
	 *    
	 * @return  the sessionListeners    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public HttpSessionListener[] getSessionListeners() {
		return sessionListeners;
	}

	/**    
	 * @param sessionListeners the sessionListeners to set    
	 */
	public void setSessionListeners(HttpSessionListener[] sessionListeners) {
		this.sessionListeners = sessionListeners;
	}

	/**    
	 * sessionAttributeListeners    
	 *    
	 * @return  the sessionAttributeListeners    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public HttpSessionAttributeListener[] getSessionAttributeListeners() {
		return sessionAttributeListeners;
	}

	/**    
	 * @param sessionAttributeListeners the sessionAttributeListeners to set    
	 */
	public void setSessionAttributeListeners(
			HttpSessionAttributeListener[] sessionAttributeListeners) {
		this.sessionAttributeListeners = sessionAttributeListeners;
	}

	
}
