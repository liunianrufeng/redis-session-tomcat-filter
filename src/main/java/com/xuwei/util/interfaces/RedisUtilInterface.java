/**
 * <b>项目名：</b>redis-session-filter-tomcat<br/>
 * <b>包名：</b>com.xuwei.util.interfaces<br/>
 * <b>文件名：</b>RedisUtilInterface.java<br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>版本信息：</b><br/>
 * <b>日期：</b>2015年5月26日-上午10:37:37<br/>
 * <b>Copyright (c)</b> 2015XX公司-版权所有<br/>
 * 
 */
package com.xuwei.util.interfaces;

import java.util.List;
import java.util.Map;

/**
 * 
 * <b>类名称：</b>RedisUtilInterface<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>xuwei<br/>
 * <b>修改人：</b>xuwei<br/>
 * <b>修改时间：</b>2015年5月26日 上午10:37:37<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 * 
 */
public interface RedisUtilInterface {

	public String set(byte[] key, byte[] value);

	public byte[] get(byte[] key) ;

	public boolean del(String key) ;
	
	public boolean del(byte[] key) ;
	
	public Boolean exists(String key) ;
	
	public Boolean exists(byte[] key) ;
	
	public Boolean hexists(byte[] key,byte[] filed) ;
	
	public Long hset(byte[] key,byte[] field,byte[] value);
	
	public Long hset(byte[] key,byte[] field,byte[] value,int expire);
	
	public Object hget(byte[] key,byte[] field);
	
	public Object hget(byte[] key,byte[] field,int expire);
	
	public String hmset(byte[] key,Map<byte[],byte[]> map);
	
	public String hmset(byte[] key,Map<byte[],byte[]> map,int expire);
	
	public List<Object> hmget(byte[] key, int expire , byte[]... fields);
	
	public Long hdel(byte[] key,byte[] ... field);
	
	public Long hset(String key,String field,String value);
	
	public Long hset(String key,String field,String value,int expire);
	
	public String hget(String key,String field);
	
	public String hget(String key,String field,int expire);
	
	public String hmset(String key,Map<String,String> map);
	
	public String hmset(String key,Map<String,String> map,int expire);
	
	public Long hdel(String key,String ... field);
	

}
