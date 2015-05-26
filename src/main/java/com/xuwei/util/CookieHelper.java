
package com.xuwei.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * <b>类名称：</b>CookieHelper<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年5月22日 下午4:27:30<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public class CookieHelper {
	private static final String DISTRIBUTED_SESSION_ID = "JSESSIONID";
	protected static Logger log = Logger.getLogger(CookieHelper.class);

	/**
	 * 
	 * writeSessionIdToNewCookie(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param id
	 * @param response
	 * @param expiry
	 * @return 
	 * Cookie
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public static Cookie writeSessionIdToNewCookie(String id,
			HttpServletResponse response, int expiry) {
		Cookie cookie = new Cookie(DISTRIBUTED_SESSION_ID, id);
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
		return cookie;
	}

	/**
	 * 
	 * writeSessionIdToCookie(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param id
	 * @param request
	 * @param response
	 * @param expiry
	 * @return 
	 * Cookie
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public static Cookie writeSessionIdToCookie(String id,
			HttpServletRequest request, HttpServletResponse response, int expiry) {
		Cookie cookie = findCookie(DISTRIBUTED_SESSION_ID, request);
		if (cookie == null) {
			return writeSessionIdToNewCookie(id, response, expiry);
		} else {
			cookie.setValue(id);
			cookie.setMaxAge(expiry);
			response.addCookie(cookie);
		}
		return cookie;
	}

	/**
	 * 
	 * findCookieValue(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param name
	 * @param request
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public static String findCookieValue(String name, HttpServletRequest request) {
		Cookie cookie = findCookie(name, request);
		if (cookie != null) {
			return cookie.getValue();
		}
		return null;
	}

	/**
	 * 
	 * findCookie(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param name
	 * @param request
	 * @return 
	 * Cookie
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public static Cookie findCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0, n = cookies.length; i < n; i++) {
			if (cookies[i].getName().equalsIgnoreCase(name)) {
				return cookies[i];
			}
		}
		return null;
	}

	/**
	 * 
	 * findSessionId(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param request
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public static String findSessionId(HttpServletRequest request) {
		return findCookieValue(DISTRIBUTED_SESSION_ID, request);
	}
}
