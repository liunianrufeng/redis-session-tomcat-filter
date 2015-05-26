package com.xuwei.session;

import java.util.Set;

import javax.servlet.http.HttpSession;

/**
 * 
 * 
 * <b>类名称：</b>BaseRedisSession<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年3月13日 下午2:30:29<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public interface BaseRedisSession extends HttpSession{

	public boolean isValid();

	public void setTransientAttribute(String paramString,
			Object paramObject);

	public void removeTransientAttribute(String paramString);

	public Object getTransientAttribute(String paramString);

	public Set<String> getTransientAttributeNames();
}
