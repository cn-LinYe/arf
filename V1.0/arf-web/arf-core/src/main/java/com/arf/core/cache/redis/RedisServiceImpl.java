package com.arf.core.cache.redis;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 封装redis 缓存服务器服务接口
 * 
 * @author caolibao
 * 
 *         2016-05-16 下午7:25:42
 */
@Service(value = "redisService")
public class RedisServiceImpl implements RedisService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
    private static String redisCode = "utf-8";
    
    public static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;
    
    @Resource(name = "redisTemplate")
    private RedisTemplate<String,Serializable> redisTemplate;

    @Override
	public long del(final String... keys) {
		if(keys == null || keys.length == 0){
			return 0;
		}
        try {
			return redisTemplate.execute(new RedisCallback<Long>() {
			    @Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
			        long result = 0;
			        byte[][] keyBytes = new byte[keys.length][];
			        for (int i = 0; i < keyBytes.length; i++) {
			        	keyBytes[i] = keys[i].getBytes();
			        }
			        result = connection.del(keyBytes);
			        return result;
			    }
			});
		} catch (Exception e) {
			return 0l;
		}
    }

	@Override
	public long set(final String key,final Object obj,final long liveTime){
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
			    @Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
			    	byte bytes[] = null;
			    	if(obj != null){
			    		if(obj instanceof String){
				    		bytes = ((String)obj).getBytes();
				    	}else if(obj instanceof Serializable){
				    		bytes = JSON.toJSONString(obj).getBytes();
				    	}else{
				    		bytes = obj.toString().getBytes();
				    	}
			    	}
			       connection.set(key.getBytes(),bytes,Expiration.from(liveTime,DEFAULT_TIMEUNIT),null);
			       return 1L;
			    }
			});
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}
	
	@Override
	public long set(final String key,final Object obj){
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
			    @Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
			    	byte bytes[] = null;
			    	if(obj != null){
			    		if(obj instanceof String){
				    		bytes = ((String)obj).getBytes();
				    	}else if(obj instanceof Serializable){
				    		bytes = JSON.toJSONString(obj).getBytes();
				    	}else{
				    		bytes = obj.toString().getBytes();
				    	}
			    	}
			       connection.set(key.getBytes(), bytes);
			       return 1L;
			    }
			});
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}
	
	
	
    @SuppressWarnings("unchecked")
	@Override
    public <T> T get(String key,Class<T> clazz){
    	try {
    		String val = this.get(key);
    		if(val == null){
    			return null;
    		}
    		if(String.class.equals(clazz)){
    			return (T) val;
    		}else{
    			if(StringUtils.isNotBlank(val)){
    				return JSON.parseObject(val, clazz);
    			}else{
    				return null;
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    @Override
	public String get(final String key) {
        try{
        	return redisTemplate.execute(new RedisCallback<String>() {
                @Override
				public String doInRedis(RedisConnection connection) throws DataAccessException {
                	try {
                		byte[] val = connection.get(key.getBytes());
                		String valStr = "";
                		if(val != null){
                			valStr = new String(val, redisCode);
                		}
                		return valStr;
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						return "";
					}
                }
            });
        }catch(Exception e){
        	e.printStackTrace();
        	return "";
        }
    }

    @Override
	public Set<String> getKeys(String pattern) {
        try {
			return redisTemplate.keys(pattern);
		} catch (Exception e) {
			return null;
		}
    }

    @Override
	public boolean exists(final String key) {
        try {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
			    @Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
			        return connection.exists(key.getBytes());
			    }
			});
		} catch (Exception e) {
			return false;
		}
    }
    /**获取redis中键值所有LIST
     * @param key
     * @return
     */
    @Override
    public List<String> lrange(final String key){
    	return lrange(key, 0, -1);
    }
    
    @Override
	public List<String> lrange(final String key, long begin, long end) {
    	try{
        	return redisTemplate.execute(new RedisCallback<List<String>>() {
                @Override
				public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                	try {      
                		List<byte[]> list=connection.lRange(key.getBytes(), 0, -1);
                		if(list!=null&&list.size()>0){
                			List<String> outList=new ArrayList<String>();
                			for (byte[] bs : list) {
                				outList.add(new String(bs, redisCode));
							}
                			return outList;
                		}
                		return null;
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
                }
            });
        }catch(Exception e){
        	e.printStackTrace();
        	return null;
        }
	}
    /**存储LIST集合
     * @param key redis中键值
     * @param obj List中数据
     * @return
     */
    @Override
	public boolean lpush(final String key,final Object obj){
		try {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
			    @Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
			    	byte bytes[] = null;
			    	if(obj != null){
			    		if(obj instanceof String){
				    		bytes = ((String)obj).getBytes();
				    	}else if(obj instanceof Serializable){
				    		bytes = JSON.toJSONString(obj).getBytes();
				    	}else{
				    		bytes = obj.toString().getBytes();
				    	}
			    	}
			       connection.lPush(key.getBytes(),bytes);
			       return true;
			    }
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
    /**获取redis中hashmap
     * @param key redis存储键值
     * @return
     */
    @Override
   	public Map<String,String> hgetAll(final String key){
   		try {
   			return redisTemplate.execute(new RedisCallback<Map<String,String>>() {
   			    @Override
				public Map<String,String> doInRedis(RedisConnection connection) throws DataAccessException {   			    	
   			    	Map<byte[],byte[]> resMap = connection.hGetAll(key.getBytes());
   			    	Map<String,String> res = new HashMap<String,String>();
   			    	try {
	   			    	for (byte[] keyByte:resMap.keySet()) {
	   			    		String key = null;
	   			    		String value = null;
							if(keyByte != null){
								key = new String(keyByte,redisCode);
							}
							if(resMap.get(keyByte) != null){
								value = new String(resMap.get(keyByte),redisCode);
							}
							res.put(key, value);
						}
   			    	} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
   			    	return res;
   			    }
   			});
   		} catch (Exception e) {
   			e.printStackTrace();
   			return null;
   		}
   	}  
   	/**获取HashMap中值
   	 * @param key redis中键值
   	 * @param field hashmap 中键值
   	 * @return
   	 */
   	@Override
   	public String hGet(final String key,final String field){
   		try {
   			return redisTemplate.execute(new RedisCallback<String>() {
   			    @Override
				public String doInRedis(RedisConnection connection) throws DataAccessException {
            		try {
            			byte[] val = connection.hGet(key.getBytes(), field.getBytes());
                		String valStr = null;
                		if(val != null){
							valStr = new String(val, redisCode);
                		}
                		return valStr; 
					} catch (UnsupportedEncodingException e) {							
						e.printStackTrace();
						return null;	
					}            		  			    
   			    }
   			});
   		} catch (Exception e) {
   			e.printStackTrace();
   			return null;
   		}
   	}
   	

	@Override
	public <T> T hGet(final String key, final String field, final Class<T> clazz) {
		try {
   			return redisTemplate.execute(new RedisCallback<T>() {
   			    @SuppressWarnings("unchecked")
				@Override
				public T doInRedis(RedisConnection connection) throws DataAccessException {
            		try {
            			byte[] val = connection.hGet(key.getBytes(), field.getBytes());
                		String valStr = null;
                		if(val != null){
							valStr = new String(val, redisCode);
                		}
                		
                		if(valStr == null){
                			return null;
                		}
                		if(String.class.equals(clazz)){
                			return (T) val;
                		}else{
                			if(StringUtils.isNotBlank(valStr)){
                				return JSON.parseObject(valStr, clazz);
                			}else{
                				return null;
                			}
                		}
                		
					} catch (UnsupportedEncodingException e) {							
						e.printStackTrace();
						return null;	
					}            		  			    
   			    }
   			});
   		} catch (Exception e) {
   			e.printStackTrace();
   			return null;
   		}
	}
   	
   	/**删除HashMap中值
   	 * @param key redis中键值
   	 * @param field hashmap 中键值
   	 * @return
   	 */
   	@Override
   	public boolean hdel(final String key,final String field){
   		try {
   			return redisTemplate.execute(new RedisCallback<Boolean>() {
   			    @Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            		try {
            			connection.hDel(key.getBytes(), field.getBytes());                		
                		return true; 
					} catch (Exception e) {							
						e.printStackTrace();
						return false;	
					}            		  			    
   			    }
   			});
   		} catch (Exception e) {
   			e.printStackTrace();
   			return false;
   		}
   	}   	
    /**判断hashmap中是否存在map中键值field
     * @param key 键值存储hashmap键值
     * @param field hashmap键值
     * @return
     */
    @Override
   	public boolean hExists(final String key,final String field){
   		try {
   			return redisTemplate.execute(new RedisCallback<Boolean>() {
   			    @Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException { 
   			    	return connection.hExists(key.getBytes(), field.getBytes());   			    	  			    	
   			    }
   			});
   		} catch (Exception e) {
   			e.printStackTrace();
   			return false;
   		}
   	}    
   
    @Override
   	public boolean hsetnx(final String key,final String field,final String value){
   		try {
   			return redisTemplate.execute(new RedisCallback<Boolean>() {
   			    @Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException { 
   			    	connection.hSetNX(key.getBytes(), field.getBytes(),value==null?null:value.getBytes());
   			    	 return true;
   			    }
   			});
   		} catch (Exception e) {
   			e.printStackTrace();
   			return false;
   		}
   	}
    
    @Override
	public boolean hset(final String key, final String field, final String value) {
    	try {
   			return redisTemplate.execute(new RedisCallback<Boolean>() {
   			    @Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException { 
   			    	connection.hSet(key.getBytes(), field.getBytes(),value==null?null:value.getBytes());
   			    	 return true;
   			    }
   			});
   		} catch (Exception e) {
   			e.printStackTrace();
   			return false;
   		}
	}
    
    /**存储HashMap
     * @param key 键值
     * @param hashes hashmap字节
     * @return
     */
    @Override
   	public boolean hMSet(final String key,final Map<byte[], byte[]> hashes){
   		try {
   			return redisTemplate.execute(new RedisCallback<Boolean>() {
   			    @Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException { 
   			    	 connection.hMSet(key.getBytes(), hashes);
   			    	 return true;
   			    }
   			});
   		} catch (Exception e) {
   			e.printStackTrace();
   			return false;
   		}
   	}
    /**选择数据库
     * @param dbIndex 数据index
     * @return
     */
    @Override
	public boolean slectDB(final int dbIndex){
    	try {
    		return redisTemplate.execute(new RedisCallback<Boolean>() {
    		    @Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
    		        connection.select(dbIndex);
    		        return true;
    		    }
    		});
		} catch (Exception e) {
			return false;
		}
    }
    @Override
	public boolean flushDB() {
        try {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
			    @Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
			        connection.flushDb();
			        return true;
			    }
			});
		} catch (Exception e) {
			return false;
		}
    }

    @Override
	public long dbSize() {
        try {
			return redisTemplate.execute(new RedisCallback<Long>() {
			    @Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
			        return connection.dbSize();
			    }
			});
		} catch (Exception e) {
			return 0;
		}
    }

    @Override
	public String ping() {
        try {
			return redisTemplate.execute(new RedisCallback<String>() {
			    @Override
				public String doInRedis(RedisConnection connection) throws DataAccessException {
			        return connection.ping();
			    }
			});
		} catch (Exception e) {
			return null;
		}
    }
    
	@Override
	public long setExpiration(final String key, final long expiration) {
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					connection.expire(key.getBytes(), expiration);
					return 1L;
				}
			});
		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public BoundZSetOperations<String, Serializable> boundZSetOps(String key){
		return redisTemplate.boundZSetOps(key);
	}
	
	@Override
	public boolean zAdd(final String key, final Object obj,final double score){
		if(StringUtils.isBlank(key)){
			return false;
		}
		try {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					byte bytes[] = null;
			    	if(obj != null){
			    		if(obj instanceof String){
				    		bytes = ((String)obj).getBytes();
				    	}else if(obj instanceof Serializable){
				    		bytes = JSON.toJSONString(obj).getBytes();
				    	}else{
				    		bytes = obj.toString().getBytes();
				    	}
			    	}
					connection.zAdd(key.getBytes(), score, bytes);
					return true;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public <T> Set<T> zRange(final String key, final long begin, final long end, final Class<T> clazz, final boolean DESC){
		if(StringUtils.isBlank(key)){
			return Collections.emptySet();
		}
		try {
			return redisTemplate.execute(new RedisCallback<Set<T>>() {
				@Override
				public Set<T> doInRedis(RedisConnection connection) throws DataAccessException {
					Set<T> zortedSet = new TreeSet<T>();
					Set<byte[]> resultSet = null;
					if(DESC){
						resultSet = connection.zRevRange(key.getBytes(), begin, end);
					}else{
						resultSet = connection.zRange(key.getBytes(), begin, begin);
					}
					if(CollectionUtils.isNotEmpty(resultSet)){
						zortedSet = new TreeSet<T>(Collections2.transform(resultSet, new Function<byte[],T>(){

							@Override
							public T apply(byte[] input) {
								try {
									if(input == null){
										return null;
									}else{
										return JSON.parseObject(new String(input,redisCode), clazz);
									}
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
									return null;
								}
							}
							
						}));
					}
					return zortedSet;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptySet();
		}
	}

	@Override
	public boolean hsetnx(String key, String field, Serializable serial) {
		if(serial != null){
			return this.hsetnx(key, field, JSON.toJSONString(serial));
		}else{
			return this.hsetnx(key, field, (String)null);
		}
	}

	@Override
	public boolean hset(String key, String field,Serializable serial) {
		if(serial != null){
			return this.hset(key, field, JSON.toJSONString(serial));
		}else{
			return this.hset(key, field, (String)null);
		}
	}

	@Override
	public RedisConnection psubscribe(MessageListener listener,byte[]... patterns) {
		if(patterns == null || patterns.length <= 0){
			logger.error("订阅redis事件必须设置匹配值");
			return null;
		}
		
		RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
		connection.pSubscribe(listener, patterns);
		return connection;
	}

	@Override
	public Long hIncrBy(final String key, final String field, final long delta) {
		if(StringUtils.isBlank(key) || StringUtils.isBlank(field)){
			return null;
		}
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection)throws DataAccessException {
					return connection.hIncrBy(key.getBytes(), field.getBytes(), delta);
				}
			});
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Double hIncrBy(final String key, final String field, final double delta) {
		if(StringUtils.isBlank(key) || StringUtils.isBlank(field)){
			return null;
		}
		try {
			return redisTemplate.execute(new RedisCallback<Double>() {
				@Override
				public Double doInRedis(RedisConnection connection)throws DataAccessException {
					return connection.hIncrBy(key.getBytes(), field.getBytes(), delta);
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Long incrBy(final String key,final long delta) {
		if(StringUtils.isBlank(key)){
			return null;
		}
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection)throws DataAccessException {
					return connection.incrBy(key.getBytes(), delta);
				}
			});
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public RedisConnection getRedisConnection() {
		try {
			return redisTemplate.execute(new RedisCallback<RedisConnection>() {
				@Override
				public RedisConnection doInRedis(RedisConnection connection)throws DataAccessException {
					return connection;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
	

	@Override
	public Long ttl(final String key,final int indexDb) {
		if(StringUtils.isBlank(key)){
			return -1l;
		}
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection)throws DataAccessException {
					try {
						if(indexDb>=0)
							connection.select(indexDb);		
						return connection.ttl(key.getBytes());
					} finally {
						if(indexDb != System_DB_Index){
							connection.select(System_DB_Index);
						}
					}
				}
			});
		} catch (Exception e) {
			return -1l;
		}
	}

	@Override
	public Long ttl(String key) {		
		return ttl(key, -1);
	}

	@Override
	public List<String> hMGet(final String key, final String... field) {
		try {
			return redisTemplate.execute(new RedisCallback<List<String>>(){

				@Override
				public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
					try {
						byte[][] fieldBytes = new byte[field.length][];
				        for (int i = 0; i < fieldBytes.length; i++) {
				        	fieldBytes[i] = field[i].getBytes();
				        }
						List<byte[]> valList = connection.hMGet(key.getBytes(), fieldBytes);
						List<String> valStrList = Lists.newArrayList();
	            		if(CollectionUtils.isNotEmpty(valList)){
	            			for(byte[] bs:valList){
	            				if(bs!=null){
	            					String valStr = new String(bs, redisCode);
		            				valStrList.add(valStr);
	            				}
	            			}
	            		}
	            		return valStrList; 
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean hMdel(final String key, final String... field) {
		try {
   			return redisTemplate.execute(new RedisCallback<Boolean>() {
   			    @Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            		try {
            			byte[][] fieldBytes = new byte[field.length][];
				        for (int i = 0; i < fieldBytes.length; i++) {
				        	fieldBytes[i] = field[i].getBytes();
				        }
            			connection.hDel(key.getBytes(), fieldBytes);                		
                		return true; 
					} catch (Exception e) {							
						e.printStackTrace();
						return false;	
					}            		  			    
   			    }
   			});
   		} catch (Exception e) {
   			e.printStackTrace();
   			return false;
   		}
	}

	@Override
	public long mSet(final Map<String, Object> keyValues) {
		try {
			return redisTemplate.execute(new RedisCallback<Long>() {
			    @Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
			    	Map<byte[], byte[]> tuples = Maps.newHashMap();
			    	for(String key : keyValues.keySet()){
			    		byte bytes[] = null;
			    		Object obj = keyValues.get(key);
				    	if(obj != null){
				    		if(obj instanceof String){
					    		bytes = ((String)obj).getBytes();
					    	}else if(obj instanceof Serializable){
					    		bytes = JSON.toJSONString(obj).getBytes();
					    	}else{
					    		bytes = obj.toString().getBytes();
					    	}
				    	}
				    	tuples.put(key.getBytes(), bytes);
			    	}
			    	if(!tuples.isEmpty())
			       connection.mSet(tuples);
			       return 1L;
			    }
			});
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public List<String> getAll(final String... keys) {
		try{
        	return redisTemplate.execute(new RedisCallback<List<String>>() {
                @Override
				public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                	try {      
                		if(keys!=null&&keys.length>0){
                			byte[][] keysBytes = new byte[keys.length][];
                    		for (int i = 0; i < keysBytes.length; i++) {
                    			keysBytes[i] = keys[i].getBytes();
    				        }
                    		List<byte[]> list=connection.mGet(keysBytes);
                    		if(list!=null&&list.size()>0){
                    			List<String> outList=new ArrayList<String>();
                    			for (byte[] bs : list) {
                    				if(bs!=null&&bs.length>0){
                    					outList.add(new String(bs, redisCode));
                    				}
    							}
                    			return outList;
                    		}
                		}
                		return null;
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
                }
            });
        }catch(Exception e){
        	e.printStackTrace();
        	return null;
        }
	}
}
