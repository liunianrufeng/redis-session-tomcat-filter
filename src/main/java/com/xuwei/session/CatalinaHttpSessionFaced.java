/**
 * <b>项目名：</b>redis-filter-tomcat-session<br/>
 * <b>包名：</b>com.xuwei.session<br/>
 * <b>文件名：</b>CatalinaHttpSessionFaced.java<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>版本信息：</b><br/>
 * <b>日期：</b>2015年3月4日-下午4:34:24<br/>
 * <b>Copyright (c)</b> 2015XX公司-版权所有<br/>
 * 
 */
package com.xuwei.session;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.catalina.session.StandardSessionFacade;

/**
 * 
 * <b>类名称：</b>CatalinaHttpSessionFaced<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年3月4日 下午4:34:24<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class CatalinaHttpSessionFaced extends StandardSessionFacade{

	/**
	 * 创建一个新的实例 CatalinaHttpSessionFaced.
	 *
	 * @param session
	 */
	public CatalinaHttpSessionFaced(HttpSession session) {
		super(session);
	}

	@Override
	public String getId() {
		return super.getId();
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		super.setMaxInactiveInterval(interval);
	}

	@Override
	public Object getAttribute(String name) {
		return super.getAttribute(name);
	}

	@Override
	public Object getValue(String name) {
		return super.getValue(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return super.getAttributeNames();
	}

	@Override
	public String[] getValueNames() {
		return super.getValueNames();
	}

	@Override
	public void setAttribute(String name, Object value) {
		super.setAttribute(name, value);
	}

	@Override
	public void putValue(String name, Object value) {
		super.putValue(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		super.removeAttribute(name);
	}

	@Override
	public void removeValue(String name) {
		super.removeValue(name);
	}

	@Override
	public void invalidate() {
		super.invalidate();
	}
	
	

}
