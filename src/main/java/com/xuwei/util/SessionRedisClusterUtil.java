package com.xuwei.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.JedisCluster;

import com.xuwei.util.interfaces.RedisUtilInterface;

/**
 * 
 * 
 * <b>类名称：</b>SessionRedisUtil<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年3月13日 下午2:32:57<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *
 */
public class SessionRedisClusterUtil implements RedisUtilInterface{
	
	
	private JedisCluster cluster;

	private SessionRedisClusterUtil() {

	}

	private static SessionRedisClusterUtil redisUtil=null;
	
	
	public static synchronized SessionRedisClusterUtil getInstance(JedisCluster cluster) {
		if(redisUtil==null){
			redisUtil = new SessionRedisClusterUtil();
		}
		redisUtil.setCluster(cluster);
		return redisUtil;
	}
	public static synchronized SessionRedisClusterUtil getInstance() {
		return redisUtil;
	}


	public String set(byte[] key, byte[] value) {
		return cluster.set(key, value);
	}

	public byte[] get(byte[] key) {
		return cluster.get(key);
	}

	public boolean del(String key) {
		long result = cluster.del(key);
		if(result>=0){
			return true;
		}
		return false;
	}
	
	public boolean del(byte[] key) {
		long result = cluster.del(key);
		if(result>=0){
			return true;
		}
		return false;
	}
	
	public Boolean exists(String key) {
		return cluster.exists(key);

	}
	public Boolean exists(byte[] key) {
		return cluster.exists(key);
		
	}
	public Boolean hexists(byte[] key,byte[] filed) {
		return cluster.hexists(key,filed);
		
	}
	
	/**
	 * 
	 * hset(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param field
	 * @param value
	 * @return 
	 * Long
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public Long hset(String key,String field,String value){
		return cluster.hset(key, field, value);
	}
	
	/**
	 * 
	 * hset(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param field
	 * @param value
	 * @return 
	 * Long
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public Long hset(byte[] key,byte[] field,byte[] value){
		return cluster.hset(key, field, value);
	}
	/**
	 * 
	 * hset(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param field
	 * @param value
	 * @return 
	 * Long
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public Long hset(String key,String field,String value,int expire){
		return cluster.hset(key, field, value ,expire);
	}
	
	/**
	 * 
	 * hset(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param field
	 * @param value
	 * @return 
	 * Long
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public Long hset(byte[] key,byte[] field,byte[] value,int expire){
		return cluster.hset(key, field, value,expire);
	}

	/**
	 * 
	 * hget(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param field
	 * @param value 
	 * void
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public Object hget(byte[] key,byte[] field){
		byte[] value = cluster.hget(key, field);
		return SerializeUtils.deserializeObject(value);
	}
	
	/**
	 * 
	 * hget(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param field
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public String hget(String key,String field){
		return cluster.hget(key, field);
	}
	
	/**
	 * 
	 * hget(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param field
	 * @param expire
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public String hget(String key,String field,int expire){
		return cluster.hget(key, field,expire);
	}
	/**
	 * 
	 * hget(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param field
	 * @param value 
	 * @param expire
	 * void
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public Object hget(byte[] key,byte[] field,int expire){
		byte[] value = cluster.hget(key, field,expire);
		return SerializeUtils.deserializeObject(value);
	}
	
	
	
	
	
	/**
	 * 
	 * hmset(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param map
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public String hmset(String key,Map<String,String> map){
		return cluster.hmset(key, map);
	}
	
	/**
	 * 
	 * hmset(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param map
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public String hmset(byte[] key,Map<byte[],byte[]> map){
		return cluster.hmset(key, map);
	}
	/**
	 * 
	 * hmset(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param map
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public String hmset(String key,Map<String,String> map,int expire){
		return cluster.hmset(key, map,expire);
	}
	
	/**
	 * 
	 * hmset(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param map
	 * @return 
	 * String
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public String hmset(byte[] key,Map<byte[],byte[]> map,int expire){
		return cluster.hmset(key, map,expire);
	}
	
	/**
	 * 
	 * hmget(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param expire
	 * @param fields
	 * @return 
	 * List<Object>
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public List<Object> hmget(byte[] key, int expire , byte[]... fields){
		List<byte[]> result = cluster.hmget(key,expire, fields);
		if(result==null||result.size()<=0){
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		for(byte[] binaryData : result){
			list.add(SerializeUtils.deserializeObject(binaryData));
		}
		return list;
	}
	
	
	/**    
	 * @param cluster the cluster to set    
	 */
	public void setCluster(JedisCluster cluster) {
		this.cluster = cluster;
	}
	/* (non-Javadoc)    
	 * @see com.xuwei.util.interfaces.RedisUtilInterface#hdel(java.lang.String, java.lang.String[])    
	 */
	@Override
	public Long hdel(String key, String... field) {
		return cluster.hdel(key, field);
	}
	/* (non-Javadoc)    
	 * @see com.xuwei.util.interfaces.RedisUtilInterface#hdel(byte[], byte[][])    
	 */
	@Override
	public Long hdel(byte[] key, byte[]... field) {
		return cluster.hdel(key, field);
	}
	
	
}
