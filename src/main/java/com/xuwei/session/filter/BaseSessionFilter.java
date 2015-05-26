/**
 * <b>项目名：</b>redis-session-tomcat-filter<br/>
 * <b>包名：</b>com.xuwei.session.filter<br/>
 * <b>文件名：</b>BaseSessionFilter.java<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>版本信息：</b><br/>
 * <b>日期：</b>2015年4月11日-下午2:04:22<br/>
 * <b>Copyright (c)</b> 2015XX公司-版权所有<br/>
 * 
 */
package com.xuwei.session.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.xuwei.servlet.RedisServletRequest;
import com.xuwei.session.manager.DefaultSessionManager;
import com.xuwei.session.manager.RedisSessionManager;
import com.xuwei.util.SessionConfig;
import com.xuwei.util.SessionRedisClusterUtil;

/**
 * 
 * <b>类名称：</b>BaseSessionFilter<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年4月11日 下午2:04:22<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public abstract class BaseSessionFilter implements Filter{
	
	private static final Logger LOG = Logger
			.getLogger(BaseSessionFilter.class);
	
	/**
	 * 
	 */
	private RedisSessionManager sessionManager;

	/* (non-Javadoc)    
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)    
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		/*JedisCluster jedisCluster = initJedisCluster();
		SessionRedisClusterUtil.getInstance(jedisCluster);*/
		sessionManager = new DefaultSessionManager();
		//ServletContext servletContext = filterConfig.getServletContext();
		setSessionListener(filterConfig, sessionManager);
		sessionManager.init();
		
		
	}

	/* (non-Javadoc)    
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)    
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		RedisServletRequest redisServletRequest = new RedisServletRequest((HttpServletRequest)request, sessionManager, (HttpServletResponse)response);
		chain.doFilter(redisServletRequest, response);
	}

	/* (non-Javadoc)    
	 * @see javax.servlet.Filter#destroy()    
	 */
	@Override
	public void destroy() {
		
	}

	/**
	 * 
	 * setSessionListener(初始化session监听器)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param filterConfig
	 * @param sessionManager 
	 * void
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	protected abstract void setSessionListener(FilterConfig filterConfig,RedisSessionManager sessionManager);

	/**
	 * 
	 * initJedisCluster(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @return 
	 * JedisCluster
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private JedisCluster initJedisCluster(){
		try {
			String clusters = SessionConfig.getValue("redis_cluster.host");
			String[] clusterHostAndPorts = clusters.split(",");
			Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
			for(String hostAndPort : clusterHostAndPorts){
				String[] hostConfig = hostAndPort.split(":");
				String host = hostConfig[0];
				String port = hostConfig[1];
				jedisClusterNode.add(new HostAndPort(host, Integer.valueOf(port)));
			}
			JedisCluster jedisCluster = new JedisCluster(jedisClusterNode);
			return jedisCluster;
		} catch (Exception e) {
			LOG.error("初始化jediscluster失败。。。。");
			throw new RuntimeException(e);
		}
	}
}
