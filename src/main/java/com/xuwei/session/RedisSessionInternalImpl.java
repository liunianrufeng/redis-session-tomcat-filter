package com.xuwei.session;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionContext;

import com.xuwei.servlet.RedisServletRequest;
import com.xuwei.session.manager.RedisSessionManager;
import com.xuwei.util.RedisUtilInstanceUtil;
import com.xuwei.util.SerializeUtils;
import com.xuwei.util.interfaces.RedisUtilInterface;

/**
 * 
 * 
 * <b>类名称：</b>RedisSessionInternalImpl<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年3月13日 下午2:37:54<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
@SuppressWarnings("deprecation")
public class RedisSessionInternalImpl implements RedisSessionInternal {

	/**
	 * 
	 */
	private final RedisServletRequest request;
	/**
	 * 是否是新建的session
	 */
	private final boolean isNew;
	/**
	 * session管理器
	 */
	private final RedisSessionManager sessionManager;
	
	/**
	 * SessionId
	 */
	private String sessionId;
	
	/**
	 * 
	 */
	private BaseSessionData baseSessionData;
	
	/**
	 * 
	 */
	private Set<String> attributeNameSet;
	
	/**
	 * 
	 */
	private RedisUtilInterface redisUtil = RedisUtilInstanceUtil.getRedisUtilInterfaceInstance();
	
	/**
	 * redis session属性名称集合对应后缀名
	 */
	private final String SESSION_ATTR_NAMES = "ATTR_NAMES";
	/**
	 * redis session基础属性名称对应后缀名
	 */
	private final String SESSION_BASEDATA = "BASESESSIONDATA";
	
	private int sessionTimeOut;

	/**
	 * 创建一个新的实例 RedisSessionInternalImpl.
	 *
	 * @param sessionId
	 * @param request2
	 * @param isNew2
	 * @param sessionManager2
	 */
	public RedisSessionInternalImpl(String sessionId,
			RedisServletRequest request, boolean isNew,
			RedisSessionManager sessionManager) {
		this.sessionId = sessionId;
		this.request = request;
		this.isNew = isNew;
		this.sessionManager = sessionManager;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xuwei.session.BaseRedisSession#isValid()
	 */
	@Override
	public boolean isValid() {
		baseSessionData = (BaseSessionData)redisUtil.hget(sessionId.getBytes(), SESSION_BASEDATA.getBytes());
		long currentTime = System.currentTimeMillis();
		if (currentTime > (baseSessionData.getMaxInactiveIntervalSeconds()*1000+baseSessionData.getLastAccessTimeMillis())){
			//session过时
			this.invalidate();
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xuwei.session.BaseRedisSession#setTransientAttribute(java.lang.String
	 * , java.lang.Object)
	 */
	@Override
	public void setTransientAttribute(String paramString, Object paramObject) {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xuwei.session.BaseRedisSession#removeTransientAttribute(java.lang
	 * .String)
	 */
	@Override
	public void removeTransientAttribute(String paramString) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xuwei.session.BaseRedisSession#getTransientAttribute(java.lang.String
	 * )
	 */
	@Override
	public Object getTransientAttribute(String paramString) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xuwei.session.BaseRedisSession#getTransientAttributeNames()
	 */
	@Override
	public Set<String> getTransientAttributeNames() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getCreationTime()
	 */
	@Override
	public long getCreationTime() {
		return baseSessionData.getCreationTimeMillis();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	@Override
	public String getId() {
		return sessionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	@Override
	public long getLastAccessedTime() {
		return baseSessionData.getLastAccessTimeMillis();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	@Override
	public ServletContext getServletContext() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	@Override
	public void setMaxInactiveInterval(int interval) {
		baseSessionData.setMaxInactiveIntervalSeconds(interval);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
	 */
	@Override
	public int getMaxInactiveInterval() {
		return baseSessionData.getMaxInactiveIntervalSeconds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getSessionContext()
	 */
	@Override
	public HttpSessionContext getSessionContext() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(String name) {
		return redisUtil.hget(sessionId.getBytes(), name.getBytes(),sessionTimeOut);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getAttributeNames()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getAttributeNames() {
		return Collections.enumeration(attributeNameSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 */
	@Override
	public String[] getValueNames() {
		return (String[])attributeNameSet.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setAttribute(String name, Object value) {
		if(value==null){
			redisUtil.hdel(sessionId.getBytes(), name.getBytes());
			//同步更新redis中的attrNames属性
			attributeNameSet.remove(name);
			redisUtil.hset(sessionId.getBytes(), SESSION_ATTR_NAMES.getBytes(), 
					SerializeUtils.serializeObject(attributeNameSet),sessionTimeOut);
			return;
		}
		redisUtil.hset(sessionId.getBytes(), name.getBytes(), SerializeUtils.serializeObject(value),sessionTimeOut);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#putValue(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void putValue(String name, Object value) {
		this.setAttribute(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
	 */
	@Override
	public void removeAttribute(String name) {
		redisUtil.hdel(sessionId.getBytes(), name.getBytes());
		//同步更新redis中的attrNames属性
		attributeNameSet.remove(name);
		redisUtil.hset(sessionId.getBytes(), SESSION_ATTR_NAMES.getBytes(), SerializeUtils.serializeObject(attributeNameSet),sessionTimeOut);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
	 */
	@Override
	public void removeValue(String name) {
		this.removeAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#invalidate()
	 */
	@Override
	public void invalidate() {
		redisUtil.del(sessionId.getBytes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#isNew()
	 */
	@Override
	public boolean isNew() {
		return isNew;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xuwei.session.RedisSessionInternal#finishRequest()
	 */
	@Override
	public void finishRequest() {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xuwei.session.RedisSessionInternal#getBaseSessionData()
	 */
	@Override
	public BaseSessionData getBaseSessionData() {
		return baseSessionData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xuwei.session.RedisSessionInternal#getAttributeNameSet()
	 */
	@Override
	public Set<String> getAttributeNameSet() {
		return attributeNameSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xuwei.session.RedisSessionInternal#setAttributeQuiet(java.lang.String
	 * , java.lang.Object)
	 */
	@Override
	public void setAttributeQuiet(String paramString, Object paramObject) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xuwei.session.RedisSessionInternal#invalidateQuiet()
	 */
	@Override
	public void invalidateQuiet() {

	}

	/**    
	 * @param attributeNameSet the attributeNameSet to set    
	 */
	public void setAttributeNameSet(Set<String> attributeNameSet) {
		this.attributeNameSet = attributeNameSet;
	}

	/**    
	 * @param sessionId the sessionId to set    
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**    
	 * @param baseSessionData the baseSessionData to set    
	 */
	public void setBaseSessionData(BaseSessionData baseSessionData) {
		sessionTimeOut = baseSessionData.getMaxInactiveIntervalSeconds();
		this.baseSessionData = baseSessionData;
	}
	
	
	

}
