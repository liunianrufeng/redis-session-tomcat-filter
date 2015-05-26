/**
 * <b>项目名：</b>redis-session-filter-tomcat<br/>
 * <b>包名：</b>com.xuwei.util<br/>
 * <b>文件名：</b>RedisUtilInstanceUtil.java<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>版本信息：</b><br/>
 * <b>日期：</b>2015年5月26日-上午10:45:11<br/>
 * <b>Copyright (c)</b> 2015XX公司-版权所有<br/>
 * 
 */
package com.xuwei.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SessionRedisUtil;

import com.xuwei.util.interfaces.RedisUtilInterface;

/**
 * 
 * <b>类名称：</b>RedisUtilInstanceUtil<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年5月26日 上午10:45:11<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class RedisUtilInstanceUtil {
	private static final Logger LOG = Logger
			.getLogger(RedisUtilInstanceUtil.class);
	private static RedisUtilInterface redisUtilInterface = null;
	public synchronized static RedisUtilInterface getRedisUtilInterfaceInstance(){
		if(redisUtilInterface==null){
			initJedis();
		}
		return redisUtilInterface;
	}
	
	private static void initJedis(){
		try {
			String clusters = SessionConfig.getValue("redis_cluster.host");
			if(StringUtils.isEmpty(clusters)){
				LOG.info("没有配置redis集群信息，初始化单点redis");
				String jedisHost=SessionConfig.getValue("redis.pool.host");
				String host = jedisHost.split(":")[0];
				int port = Integer.valueOf(jedisHost.split(":")[1]);
				JedisPoolConfig config = new JedisPoolConfig();
				JedisPool pool = new JedisPool(config, host,port);
				redisUtilInterface = SessionRedisUtil.getInstance(pool);
				return;
			}
			String[] clusterHostAndPorts = clusters.split(",");
			Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
			for(String hostAndPort : clusterHostAndPorts){
				String[] hostConfig = hostAndPort.split(":");
				String host = hostConfig[0];
				String port = hostConfig[1];
				jedisClusterNode.add(new HostAndPort(host, Integer.valueOf(port)));
			}
			JedisCluster jedisCluster = new JedisCluster(jedisClusterNode);
			redisUtilInterface = SessionRedisClusterUtil.getInstance(jedisCluster);
		} catch (Exception e) {
			LOG.error("初始化jedis失败。。。。");
			throw new RuntimeException(e);
		}
	}
}
