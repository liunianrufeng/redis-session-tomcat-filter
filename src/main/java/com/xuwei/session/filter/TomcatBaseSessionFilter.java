/**
 * <b>项目名：</b>redis-session-tomcat-filter<br/>
 * <b>包名：</b>com.xuwei.session.filter<br/>
 * <b>文件名：</b>TomcatBaseSessionFilter.java<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>版本信息：</b><br/>
 * <b>日期：</b>2015年5月23日-下午1:47:38<br/>
 * <b>Copyright (c)</b> 2015XX公司-版权所有<br/>
 * 
 */
package com.xuwei.session.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

import com.xuwei.session.manager.RedisSessionManager;
import com.xuwei.util.Expression;

/**
 * 
 * <b>类名称：</b>TomcatBaseSessionFilter<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年5月23日 下午1:47:38<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class TomcatBaseSessionFilter extends BaseSessionFilter{

	/* (non-Javadoc)    
	 * @see com.xuwei.session.filter.BaseSessionFilter#setSessionListener(javax.servlet.FilterConfig, com.xuwei.session.manager.RedisSessionManager)    
	 */
	@Override
	protected void setSessionListener(FilterConfig filterConfig,
			RedisSessionManager sessionManager) {
		ServletContext servletContext = filterConfig.getServletContext();
		//在tomcat中ServletContext，实现类为ApplicationContextFacade
		//该类对ApplicationContext进行了装饰，相应字段名称为context
		Object appContext = Expression.eval(servletContext, ".context");
		//ApplicationContext类中StandardContext属性，相应字段为context
		Object standardContext = Expression.eval(appContext, ".context");
		ArrayList<HttpSessionListener> sessionListeners = new ArrayList<HttpSessionListener>();
		
		ArrayList<HttpSessionAttributeListener> sessionAttributeListeners = new ArrayList<HttpSessionAttributeListener>();
		
		setListener(standardContext, sessionListeners, sessionAttributeListeners);
		
		if(sessionAttributeListeners.size()>0){
			sessionManager.setSessionAttributeListeners(sessionAttributeListeners.toArray(new HttpSessionAttributeListener[sessionAttributeListeners.size()]));
		}
		if(sessionListeners.size()>0){
			sessionManager.setSessionListeners(sessionListeners.toArray(new HttpSessionListener[sessionListeners.size()]));
		}
	}
	
	/**
	 * 
	 * setListener(反射获取session监听器列表)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param standardContext
	 * @param sessionListeners
	 * @param sessionAttributeListener 
	 * void
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	private void setListener(Object standardContext,
			List<HttpSessionListener> sessionListeners,
			List<HttpSessionAttributeListener> sessionAttributeListener){
		Object[] applicationEventListeners = (Object[])Expression.eval(standardContext, ".getApplicationEventListeners()");
		Object[] applicationLifecycleListeners = (Object[]) Expression.eval(standardContext, ".getApplicationLifecycleListeners()");
		addToList(applicationEventListeners, sessionListeners, sessionAttributeListener);
		addToList(applicationLifecycleListeners, sessionListeners, sessionAttributeListener);
	}
	
	/**
	 * 
	 * addToList(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param objects
	 * @param sessionListeners
	 * @param sessionAttributeListener 
	 * void
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	private void addToList(Object[] objects,
			List<HttpSessionListener> sessionListeners,
			List<HttpSessionAttributeListener> sessionAttributeListener){
		if(objects==null||objects.length==0){
			return;
		}
		for(Object object:objects){
			if(object instanceof HttpSessionListener){
				sessionListeners.add((HttpSessionListener)object);
			}
			if(object instanceof HttpSessionAttributeListener){
				sessionAttributeListener.add((HttpSessionAttributeListener)object);
			}
		}
	}

}
