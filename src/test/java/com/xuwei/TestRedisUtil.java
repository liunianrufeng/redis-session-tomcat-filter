/**
 * <b>项目名：</b>redis-session-tomcat-filter<br/>
 * <b>包名：</b>com.xuwei<br/>
 * <b>文件名：</b>TestRedisUtil.java<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>版本信息：</b><br/>
 * <b>日期：</b>2015年5月25日-下午4:27:32<br/>
 * <b>Copyright (c)</b> 2015XX公司-版权所有<br/>
 * 
 */
package com.xuwei;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.xuwei.util.SessionRedisUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 
 * <b>类名称：</b>TestRedisUtil<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年5月25日 下午4:27:32<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public class TestRedisUtil {
	
	SessionRedisUtil redisUtil;

	@Before
	public void pre(){
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		jedisClusterNode.add(new HostAndPort("172.16.228.216", 7000));
		jedisClusterNode.add(new HostAndPort("172.16.228.216", 7001));
		jedisClusterNode.add(new HostAndPort("172.16.228.217", 7000));
		jedisClusterNode.add(new HostAndPort("172.16.228.217", 7001));
		jedisClusterNode.add(new HostAndPort("172.16.228.11", 7000));
		jedisClusterNode.add(new HostAndPort("172.16.228.11", 7001));
		JedisCluster jedisCluster = new JedisCluster(jedisClusterNode);
		SessionRedisUtil.getInstance(jedisCluster);
		redisUtil = SessionRedisUtil.getInstance();
	}
	
	@Test
	public void testExist(){
		Map<byte[],byte[]> map = new HashMap<byte[],byte[]>();
		map.put("qwe".getBytes(), "1234".getBytes());
		redisUtil.hmset("123".getBytes(), map, 5);
		boolean b = redisUtil.exists("123".getBytes());
		System.out.println(b);
	}
	@Test
	public void testExist1(){
		boolean b = redisUtil.exists("123".getBytes());
		System.out.println(b);
	}
	
	
	
}
