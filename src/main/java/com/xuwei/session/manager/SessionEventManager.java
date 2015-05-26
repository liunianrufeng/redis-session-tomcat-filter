/**
 * <b>项目名：</b>redis-session-tomcat-filter<br/>
 * <b>包名：</b>com.xuwei.session.manager<br/>
 * <b>文件名：</b>SessionEventManager.java<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>版本信息：</b><br/>
 * <b>日期：</b>2015年5月23日-下午2:41:40<br/>
 * <b>Copyright (c)</b> 2015XX公司-版权所有<br/>
 * 
 */
package com.xuwei.session.manager;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * <b>类名称：</b>SessionEventManager<br/>
 * <b>类描述：</b>session事件管理<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年5月23日 下午2:41:40<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public interface SessionEventManager {
	/**
	 * 
	 * sessionCreatedEvent(这里用一句话描述这个方法的作用)<br/>
	 * (触发session创建事件)<br/>
	 * @param paramHttpSession 
	 * void
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public void sessionCreatedEvent(HttpSession paramHttpSession);

	/**
	 * 
	 * sessionDestroyedEvent(这里用一句话描述这个方法的作用)<br/>
	 * (触发session销毁事件)<br/>
	 * @param paramHttpSession 
	 * void
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public void sessionDestroyedEvent(HttpSession paramHttpSession);

	public void unbindAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject);

	public void bindAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject);

	public void setAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject);

	public void removeAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject);

	public void replaceAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject1, Object paramObject2);
	
	/**    
	 * sessionListeners    
	 *    
	 * @return  the sessionListeners    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public HttpSessionListener[] getSessionListeners();

	/**    
	 * @param sessionListeners the sessionListeners to set    
	 */
	public void setSessionListeners(HttpSessionListener[] sessionListeners);

	/**    
	 * sessionAttributeListeners    
	 *    
	 * @return  the sessionAttributeListeners    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public HttpSessionAttributeListener[] getSessionAttributeListeners();

	/**    
	 * @param sessionAttributeListeners the sessionAttributeListeners to set    
	 */
	public void setSessionAttributeListeners(
			HttpSessionAttributeListener[] sessionAttributeListeners);
}
