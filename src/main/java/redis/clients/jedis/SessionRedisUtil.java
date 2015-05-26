package redis.clients.jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xuwei.util.SerializeUtils;
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
public class SessionRedisUtil implements RedisUtilInterface{
	
	private JedisPool pool;
	

	private SessionRedisUtil() {

	}

	private static SessionRedisUtil redisUtil=null;
	
	
	public static synchronized SessionRedisUtil getInstance(JedisPool pool) {
		if(redisUtil==null){
			redisUtil = new SessionRedisUtil();
		}
		redisUtil.setPool(pool);
		return redisUtil;
	}
	public static synchronized SessionRedisUtil getInstance() {
		return redisUtil;
	}


	public String set(byte[] key, byte[] value) {
		Jedis jedis = pool.getResource();
		try{
			return jedis.set(key, value);
		}
		finally{
			pool.returnResource(jedis);
		}
	}

	public byte[] get(byte[] key) {
		Jedis jedis = pool.getResource();
		try{
			return jedis.get(key);
		}
		finally{
			pool.returnResource(jedis);
		}
	}

	public boolean del(String key) {
		Jedis jedis = pool.getResource();
		long result = -1;
		try{
			result = jedis.del(key);
		}
		finally{
			pool.returnResource(jedis);
		}
		if(result>=0){
			return true;
		}
		return false;
	}
	
	public boolean del(byte[] key) {
		Jedis jedis = pool.getResource();
		long result = -1;
		try{
			result = jedis.del(key);
		}
		finally{
			pool.returnResource(jedis);
		}
		if(result>=0){
			return true;
		}
		return false;
	}
	
	public Boolean exists(String key) {
		Jedis jedis = pool.getResource();
		try{
			return jedis.exists(key);
		}
		finally{
			pool.returnResource(jedis);
		}

	}
	public Boolean exists(byte[] key) {
		Jedis jedis = pool.getResource();
		try{
			return jedis.exists(key);
		}
		finally{
			pool.returnResource(jedis);
		}
		
	}
	public Boolean hexists(byte[] key,byte[] filed) {
		Jedis jedis = pool.getResource();
		try{
			return jedis.hexists(key, filed);
		}
		finally{
			pool.returnResource(jedis);
		}
		
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
		Jedis jedis = pool.getResource();
		try{
			return jedis.hset(key, field, value);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			return jedis.hset(key, field, value);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			Transaction transaction = jedis.multi();
			transaction.hset(key, field, value);
			transaction.expire(key, expire);
			List<Object> result = transaction.exec();
			return (Long)result.get(0);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			Transaction transaction = jedis.multi();
			transaction.hset(key, field, value);
			transaction.expire(key, expire);
			List<Object> result = transaction.exec();
			return (Long)result.get(0);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			byte[] data = jedis.hget(key, field);
			return SerializeUtils.deserializeObject(data);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			return jedis.hget(key, field);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			Transaction transaction = jedis.multi();
			transaction.hget(key, field);
			transaction.expire(key, expire);
			List<Object> result = transaction.exec();
			return (String)result.get(0);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			Transaction transaction = jedis.multi();
			transaction.hget(key, field);
			transaction.expire(key, expire);
			List<Object> result = transaction.exec();
			return SerializeUtils.deserializeObject((byte[])result.get(0));
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			return jedis.hmset(key, map);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			return jedis.hmset(key, map);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			Transaction transaction = jedis.multi();
			transaction.hmset(key, map);
			transaction.expire(key, expire);
			List<Object> result = transaction.exec();
			return (String) result.get(0);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			Transaction transaction = jedis.multi();
			transaction.hmset(key, map);
			transaction.expire(key, expire);
			List<Object> result = transaction.exec();
			return (String) result.get(0);
		}
		finally{
			pool.returnResource(jedis);
		}
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
		Jedis jedis = pool.getResource();
		try{
			Transaction transaction = jedis.multi();
			transaction.hmget(key, fields);
			transaction.expire(key, expire);
			List<Object> result = transaction.exec();
			List<byte[]> dataResult = (List<byte[]>)result.get(0);
			if(result==null||result.size()<=0){
				return null;
			}
			List<Object> list = new ArrayList<Object>();
			for(byte[] binaryData : dataResult){
				list.add(SerializeUtils.deserializeObject(binaryData));
			}
			return list;
			
		}finally{
			pool.returnResource(jedis);
		}
	}
	
	/**
	 * 
	 * hdel(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param field 
	 * void
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public Long hdel(String key,String ... field){
		Jedis jedis = pool.getResource();
		try{
			return jedis.hdel(key, field);
		}finally{
			pool.returnResource(jedis);
		}
	}
	
	/**
	 * 
	 * hdel(这里用一句话描述这个方法的作用)<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * @param key
	 * @param field
	 * @return 
	 * Long
	 * @author xuwei
	 * @exception 
	 * @since  1.0.0
	 */
	public Long hdel(byte[] key,byte[] ... field){
		Jedis jedis = pool.getResource();
		try{
			return jedis.hdel(key, field);
		}finally{
			pool.returnResource(jedis);
		}
	}
	
	/**    
	 * pool    
	 *    
	 * @return  the pool    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public JedisPool getPool() {
		return pool;
	}
	/**    
	 * @param pool the pool to set    
	 */
	public void setPool(JedisPool pool) {
		this.pool = pool;
	}
	
	
}
