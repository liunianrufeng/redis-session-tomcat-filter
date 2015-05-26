package com.xuwei.session.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

import com.xuwei.servlet.RedisServletRequest;
import com.xuwei.session.BaseSessionData;
import com.xuwei.session.RedisSessionInternal;
import com.xuwei.session.RedisSessionInternalImpl;
import com.xuwei.util.RedisUtilInstanceUtil;
import com.xuwei.util.SerializeUtils;
import com.xuwei.util.SessionConfig;
import com.xuwei.util.interfaces.RedisUtilInterface;

/**
 * 
 * 
 * <b>类名称：</b>DefauluSessionManager<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年3月13日 下午2:34:14<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public class DefaultSessionManager implements RedisSessionManager {

	/**
	 * session过期时间
	 */
	private int sessionTimeoutSeconds;
	
	/**
	 * 
	 */
	private String cookieDomain;
	
	/**
	 * 
	 */
	private String cookiePath;
	
	/**
	 * 
	 */
	private int cookieMaxAge;

	
	/**
	 * redis session属性名称集合对应后缀名
	 */
	private final String SESSION_ATTR_NAMES = "ATTR_NAMES";
	/**
	 * redis session基础属性名称对应后缀名
	 */
	private final String SESSION_BASEDATA = "BASESESSIONDATA";
	
	/**
	 * 
	 */
	private HttpSessionListener[] sessionListeners;
	
	/**
	 * 
	 */
	private HttpSessionAttributeListener[] sessionAttributeListeners;
	
	/**
	 * seesion监听管理器
	 */
	private SessionEventManager eventManager;
	
	/**
	 * 
	 */
	private RedisUtilInterface redisUtil = RedisUtilInstanceUtil.getRedisUtilInterfaceInstance();


	/**
	 * 
	 * createNewSession(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param request
	 * @param response
	 * @return 
	 * RedisSessionInternal
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	private RedisSessionInternal createNewSession(HttpServletRequest request,
			HttpServletResponse response) {
		if (!(request instanceof RedisServletRequest)) {
			throw new IllegalStateException("serlvet类型不匹配");
		}
		RedisServletRequest redisServlet = (RedisServletRequest) request;
		BaseSessionData base = new BaseSessionData(System.currentTimeMillis(),
				this.sessionTimeoutSeconds);
		Set<String> attrNames = new HashSet<String>();
		return createNewSession(redisServlet, response, base, attrNames);
	}

	/**
	 * 
	 * createNewSession(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param request
	 * @param response
	 * @param base
	 * @param attrNames
	 * @return 
	 * RedisSessionInternal
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	private RedisSessionInternal createNewSession(RedisServletRequest request,
			HttpServletResponse response, BaseSessionData base,
			Set<String> attrNames) {
		String sessionId = createSessionId();
		RedisSessionInternalImpl session = new RedisSessionInternalImpl(sessionId,request,true,this);
		session.setAttributeNameSet(attrNames);
		session.setBaseSessionData(base);
		Map<byte[],byte[]> data = new HashMap<byte[],byte[]>();
		data.put(SESSION_ATTR_NAMES.getBytes(), SerializeUtils.serializeObject(attrNames));
		data.put(SESSION_BASEDATA.getBytes(), SerializeUtils.serializeObject(base));
		redisUtil.hmset(sessionId.getBytes(), data, sessionTimeoutSeconds);
		//写入cookie
		Cookie cookie = new Cookie("JSESSIONID", sessionId);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
		cookie = new Cookie("JSESSIONID", sessionId);
		cookie.setDomain(cookieDomain);
		cookie.setPath(cookiePath);
		cookie.setMaxAge(cookieMaxAge);
		response.addCookie(cookie);
		//触发session创建事件
		eventManager.sessionCreatedEvent(session);
		return session;
	}


	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.RedisSessionManager#getOrCreateSession(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, boolean)    
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HttpSession getOrCreateSession(String sessionId,
			HttpServletRequest request, HttpServletResponse response,
			boolean create) {
		if (!(request instanceof RedisServletRequest)) {
			throw new IllegalStateException("serlvet类型不匹配");
		}
		RedisServletRequest redisRequest = (RedisServletRequest) request;
		//sessionid为空，初始化session
		if(sessionId==null){
			if(create){
				return createNewSession(request, response);
			}else{
				return null;
			}
		}
		//sessionid不为空，判断redis中是否存在该session的相关信息
		Boolean exist = redisUtil.exists(sessionId.getBytes());
		if(exist){
			List<Object> sessionData = redisUtil.hmget(sessionId.getBytes(), 
					sessionTimeoutSeconds, SESSION_ATTR_NAMES.getBytes(),
					SESSION_BASEDATA.getBytes());
			Set<String> attrNames = (Set<String>)sessionData.get(0);
			BaseSessionData baseSessionData = (BaseSessionData)sessionData.get(1);
			baseSessionData.setLastAccessTimeMillis(System.currentTimeMillis());
			//初始化session
			RedisSessionInternalImpl session = new RedisSessionInternalImpl(sessionId,redisRequest,false,this);
			session.setAttributeNameSet(attrNames);
			session.setSessionId(sessionId);
			session.setBaseSessionData(baseSessionData);
			return session;
		}
		if(create){
			return createNewSession(request, response);
		}else{
			return null;
		}
		
	}

	/**    
	 * sessionTimeoutSeconds    
	 *    
	 * @return  the sessionTimeoutSeconds    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public int getSessionTimeoutSeconds() {
		return sessionTimeoutSeconds;
	}

	/**    
	 * @param sessionTimeoutSeconds the sessionTimeoutSeconds to set    
	 */
	public void setSessionTimeoutSeconds(int sessionTimeoutSeconds) {
		this.sessionTimeoutSeconds = sessionTimeoutSeconds;
	}
	
	/**
	 * 
	 * createSessionId(这里用一句话描述这个方法的作用)<br/>
	 * (sessionid 生成方法)<br/>
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	private String createSessionId(){
		return UUID.randomUUID().toString()+Math.random();
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

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.RedisSessionManager#init()    
	 */
	@Override
	public void init() {
		eventManager = new DefaultSessionEventManager();
		eventManager.setSessionAttributeListeners(sessionAttributeListeners);
		eventManager.setSessionListeners(sessionListeners);
		this.sessionTimeoutSeconds=Integer.valueOf(SessionConfig.getValue("sessiontimeout"));
		this.cookieDomain = SessionConfig.getValue("cookie.domain");
		this.cookiePath = SessionConfig.getValue("cookie.path");
		this.cookieMaxAge = Integer.valueOf(SessionConfig.getValue("cookie.maxAge"));
	}
	
	
	
}
