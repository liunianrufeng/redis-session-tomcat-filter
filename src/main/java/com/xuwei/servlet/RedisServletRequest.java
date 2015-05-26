package com.xuwei.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xuwei.session.manager.RedisSessionManager;
/**
 * 
 * 
 * <b>类名称：</b>RedisServletRequest<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年3月13日 下午2:31:53<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public class RedisServletRequest extends HttpServletRequestWrapper{

	/**
	 * 
	 */
	private HttpSession httpSession;
	
	/**
	 * session管理器
	 */
	private RedisSessionManager sessionManager;
	
	/**
	 * 
	 */
	private final HttpServletResponse response;
	
	/**
	 * 
	 */
	private String sessionId;
	
	/**
	 * session的cookie名称
	 */
	private String sessionCookieName = "JSESSIONID";
	

	/**
	 * 创建一个新的实例 RedisServletRequest.
	 *
	 * @param request
	 * @param sessionManager
	 * @param response
	 * @param idGenerator
	 */
	public RedisServletRequest(HttpServletRequest request,
			RedisSessionManager sessionManager, HttpServletResponse response) {
		super(request);
		this.sessionManager = sessionManager;
		this.response = response;
		this.sessionId = findSessionFormCookie(request);
	}



	/* (non-Javadoc)    
	 * @see javax.servlet.http.HttpServletRequestWrapper#getSession(boolean)    
	 */
	@Override
	public HttpSession getSession(boolean create) {
		if(httpSession!=null){
			return httpSession;
		}
		httpSession = sessionManager.getOrCreateSession(sessionId, this, response, create);
		return httpSession;
	}

	/* (non-Javadoc)    
	 * @see javax.servlet.http.HttpServletRequestWrapper#getSession()    
	 */
	@Override
	public HttpSession getSession() {
		return getSession(true);
	}
	
	
	/**
	 * 
	 * findSessionFormCookie(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param request
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	private String findSessionFormCookie(HttpServletRequest request){
		String rv = null;

	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	      for (Cookie cookie : cookies) {
	        if (this.sessionCookieName.equals(cookie.getName())) {
	          rv = cookie.getValue();
	        }
	      }
	    }
	    return rv;
	}
	
	/**
	 * 
	 * findSessionFormCookie(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param request
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	@SuppressWarnings("unused")
	private String findSessionFormURL(HttpServletRequest request){
		if(request.isRequestedSessionIdFromURL()){
			return request.getRequestedSessionId();
		}
		String rv = null;
		
		return rv;
	}

}
