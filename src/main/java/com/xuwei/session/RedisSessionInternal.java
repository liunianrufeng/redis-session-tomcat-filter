package com.xuwei.session;

import java.util.Set;

/**
 * 
 * 
 * <b>类名称：</b>RedisSessionInternal<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年3月13日 下午2:31:13<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public interface RedisSessionInternal extends BaseRedisSession {
	public void finishRequest();

	public BaseSessionData getBaseSessionData();

	public Set<String> getAttributeNameSet();

	public void setAttributeQuiet(String paramString,
			Object paramObject);

	public abstract void invalidateQuiet();

}
