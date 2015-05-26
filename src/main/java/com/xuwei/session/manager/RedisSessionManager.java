
package com.xuwei.session.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

/**
 * 
 * 
 * <b>类名称：</b>RedisSessionManager<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年3月13日 下午2:29:58<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public interface RedisSessionManager {
	
	/**
	 * 
	 * init(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/> 
	 * void
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public void init();

	/**
	 * getOrCreateSession(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param sessionId
	 * @param request
	 * @param response
	 * @param create
	 * @return 
	 * HttpSession
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	*/
	public HttpSession getOrCreateSession(String sessionId,HttpServletRequest request, HttpServletResponse response,
			boolean create);
	
	/**
	 * 
	 * setSessionTimeoutSeconds(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param sessionTimeoutSeconds 
	 * void
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public void setSessionTimeoutSeconds(int sessionTimeoutSeconds);
	
	/**    
	 * sessionListeners    
	 *    
	 * @return  the sessionListeners    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public HttpSessionListener[] getSessionListeners() ;

	/**    
	 * @param sessionListeners the sessionListeners to set    
	 */
	public void setSessionListeners(HttpSessionListener[] sessionListeners) ;

	/**    
	 * sessionAttributeListeners    
	 *    
	 * @return  the sessionAttributeListeners    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public HttpSessionAttributeListener[] getSessionAttributeListeners() ;

	/**  
	 * @param sessionAttributeListeners the sessionAttributeListeners to set    
	 */
	public void setSessionAttributeListeners(HttpSessionAttributeListener[] sessionAttributeListeners);
}
