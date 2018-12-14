package com.arf.core.cache.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundZSetOperations;

/**
 * redis 的操作开放接口
 * 
 * @author caolibao
 * 
 *         2016-05-16 下午7:25:42
 */
public interface RedisService {
	
	/**
	 * 本系统应用的缓存db index
	 */
	public final int System_DB_Index = 1;
	/**
	 * socket sever的缓存db index
	 */
	public final int Socket_Server_DB_Index = 0;
	
	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public abstract long del(String... keys);

	/**
	 * 缓存key对应的对象 并设置超时时间
	 * @param key
	 * @param obj
	 * @param liveTime 单位:秒
	 * @return
	 */
	public long set(String key,Object obj,long liveTime);
	
	/**
	 * 缓存key对应的对象
	 * @param key
	 * @param obj
	 * @return
	 */
	public long set(String key,Object obj);
	
	/**
	 * 将map(key-valve)中的键值对缓存为多个key对应的对象
	 * @param key
	 * @param obj
	 * @return
	 */
	public long mSet(Map<String, Object> keyValues);

	/**
	 * 获取redis value (String)
	 * 
	 * @param key
	 * @return
	 */
	public abstract String get(String key);
	
	/**
	 * 获取key对应的对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T get(String key,Class<T> clazz);

	/**
     * 通过正则匹配keys
     * 
     * @param pattern
     * @return
     */
    public abstract Set<String> getKeys(String pattern);

	/**
	 * 检查key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	public abstract boolean exists(String key);
	/**获取redis中键值所有LIST
     * @param key
     * @return
     */
    public List<String> lrange(final String key);
    
    /**
     * 获取List集合中从begin到end的子集,包含begin,end所在位置的元素
     * @param key
     * @param begin
     * @param end
     * @return
     */
    public List<String> lrange(final String key, long begin,long end);
    /**存储LIST集合
     * @param key redis中键值
     * @param obj List中数据
     * @return
     */
	public boolean lpush(final String key,final Object obj);
    /**获取redis中hashmap
     * @param key redis存储键值
     * @return
     */
   	public Map<String, String> hgetAll(final String key);
   	/**获取HashMap中值
   	 * @param key redis中键值
   	 * @param field hashmap 中键值
   	 * @return
   	 */
   	public String hGet(final String key,final String field);
   	
   	/**获取HashMap中值
   	 * @param key redis中键值
   	 * @param field hashmap 中键值
   	 * @return
   	 */
   	public <T> T hGet(final String key,final String field,Class<T> clazz);
   	
   	/**删除HashMap中值
   	 * @param key redis中键值
   	 * @param field hashmap 中键值
   	 * @return
   	 */
   	public boolean hdel(final String key,final String field);
   	
   	/**	
   	 * 【注意:该方法一般情况用不到】
   	 * Redis Hsetnx 命令用于为哈希表中不存在的的字段赋值 。
   	 *	如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
   	 *	如果字段已经存在于哈希表中，操作无效。
   	 *	如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。 
     * @param key redis 键值
     * @param field hashmap 键值
     * @param value hashmap 值
     * @return
     */
   	public boolean hsetnx(final String key,final String field,final String value);
   	
	/**	
	 * 【注意:该方法一般情况用不到】
	 * Redis Hsetnx 命令用于为哈希表中不存在的的字段赋值 。
   	 *	如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
   	 *	如果字段已经存在于哈希表中，操作无效。
   	 *	如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。 
     * @param key redis 键值
     * @param field hashmap 键值
     * @param value hashmap 值
     * @return
     */
   	public boolean hsetnx(final String key,final String field,final Serializable serial);
   	
   	/** Redis Hset 命令用于为哈希表中的字段赋值 。如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
   	 * 	如果字段已经存在于哈希表中，旧值将被覆盖。 
     * @param key redis 键值
     * @param field hashmap 键值
     * @param value hashmap 值
     * @return
     */
   	public boolean hset(final String key,final String field,final String value);
   	
   	
   	/** Redis Hset 命令用于为哈希表中的字段赋值 。如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
   	 * 	如果字段已经存在于哈希表中，旧值将被覆盖。 
     * @param key redis 键值
     * @param field hashmap 键值
     * @param value hashmap 值
     * @return
     */
   	public boolean hset(final String key,final String field,final Serializable serial);
   	
   	/**判断hashmap中是否存在map中键值field
     * @param key 键值存储hashmap键值
     * @param field hashmap键值
     * @return
     */
   	public boolean hExists(final String key,final String field);
   	
    /**存储HashMap
     * @param key 键值
     * @param hashes hashmap字节
     * @return
     */   
   	public boolean hMSet(final String key,final Map<byte[], byte[]> hashes);
   	/**选择数据库
     * @param dbIndex 数据index
     * @return
     */
    public boolean slectDB(final int dbIndex);
	/**
	 * 清空redis 所有数据
	 * 
	 * @return
	 */
	public abstract boolean flushDB();

	/**
	 * 查看redis里有多少数据
	 */
	public abstract long dbSize();

	/**
	 * 检查是否连接成功
	 * 
	 * @return
	 */
	public abstract String ping();
	
	/**
	 * 设置存活时间
	 * @param key
	 * @param expiration 存活时间 单位秒
	 */
	public abstract long setExpiration(String key,long expiration);
	
	/**
	 * 获取操作sorted sets的Operations
	 * Returns the operations performed on zset values (also known as sorted sets) bound to the given key.
	 * @param key
	 * @return
	 */
	public BoundZSetOperations<String, Serializable> boundZSetOps(String key);
	
	/**
	 * 向有序集合增加一个元素, 通过score来做排序,如果要保持缓存和数据库中数据存储顺序一致,可以使用主键id来充当score
	 * @param key
	 * @param obj
	 * @param score
	 * @return
	 */
	public boolean zAdd(String key, Object obj, double score);
	
	/**
	 * 从有序集合取出从begin到end的子集元素
	 * @param key
	 * @param begin
	 * @param end
	 * @param clazz 希望的集合元素类型
	 * @param DESC true:倒序取出, false:正序取出
	 * @return
	 */
	public <T> Set<T> zRange(final String key, final long begin, final long end, final Class<T> clazz, final boolean DESC);
	
	/**
	 * 订阅redis的相关事件,eg. key del事件
	 * @param listener
	 * @param patterns
	 * @return 返回监听时间的连接,一旦订阅事件成功,该连接即进入监听模式,任何其它非订阅命令将不被接受,除非解除订阅
	 */
	public RedisConnection psubscribe(MessageListener listener,byte[]... patterns);
	
	/**
	 * 用于增加存储在字段中存储由增量键哈希的数量。如果键不存在，新的key被哈希创建。如果字段不存在，值被设置为0之前进行操作。
	 * 增量也可以为负数，相当于对给定域进行减法操作。
	 * @param key
	 * @param field
	 * @param delta
	 * @return
	 */
	Long hIncrBy(final String key, final String field, final long delta);
	
	/**
	 * 用于增加存储在字段中存储由增量键哈希的数量。如果键不存在，新的key被哈希创建。如果字段不存在，值被设置为0之前进行操作。
	 * 增量也可以为负数，相当于对给定域进行减法操作。
	 * @param key
	 * @param field
	 * @param delta
	 * @return
	 */
	Double hIncrBy(final String key, final String field,final double delta);
	
	/**
	 * 增加数值
	 * @param key
	 * @param delta
	 * @return
	 */
	Long incrBy(final String key,final long delta);
	
	/**
	 * 获得redis 连接
	 * @return
	 */
	RedisConnection getRedisConnection();
	
	/**获取redis 数据库KEY 超时时间
	 * 
    TTL以毫秒为单位。
    -1, 如果key没有到期超时。
    -2, 如果键不存在。
	 * @param key
	 * @param indexDb
	 * @return
	 */
	Long ttl(String key,final int indexDb);
	
	/**获取redis 数据库KEY 超时时间
	 * 
    TTL以毫秒为单位。
    -1, 如果key没有到期超时。
    -2, 如果键不存在。
	 * @param key
	 * @return
	 */
	Long ttl(String key);
	
	/**获取HashMap中值
   	 * @param key redis中键值
   	 * @param field hashmap 中键值
   	 * @return
   	 */
   	public List<String> hMGet(final String key,final String... field);
   	
   	/**删除HashMap中值
   	 * @param key redis中键值
   	 * @param field hashmap 中键值
   	 * @return
   	 */
   	public boolean hMdel(final String key,final String... field);
   	
   	/**获取redis中键值集合的对应的值
     * @param key
     * @return
     */
    public List<String> getAll(final String... keys);
}