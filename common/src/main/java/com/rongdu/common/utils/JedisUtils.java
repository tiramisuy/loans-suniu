/**
 *Copyright 2014-2017 www.suniushuke.com All rights reserved.
 */
package com.rongdu.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rongdu.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

/**
 * Jedis Cache 工具类
 * 
 * @author sunda
 * @version 2014-6-29
 */
public class JedisUtils {

	private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);

	private static JedisPool jedisPool = SpringContextHolder.getBean(JedisPool.class);

	public static final String KEY_PREFIX = Global.getConfig("redis.keyPrefix");

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.get(key);
				value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
				logger.debug("get {} ", key);
			}
		} catch (Exception e) {
			logger.warn("get {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Object getObject(String key) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = toObject(jedis.get(getBytesKey(key)));
				logger.debug("getObject {} ", key);
			}
		} catch (Exception e) {
			logger.warn("getObject {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String set(String key, String value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.set(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("set {} ", key);
		} catch (Exception e) {
			logger.warn("set {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	public static Long setNxAndEx(String key, String value, int seconds) {
		Jedis jedis = null;
		Long res = null;
		Transaction multi = null;
		try {
			jedis = getResource();
			multi = jedis.multi();
			multi.setnx(key, value);
			multi.expire(key, seconds);
			List<Object> responseList = multi.exec();
			res = (Long) responseList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
		return res;
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String setObject(String key, Object value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.set(getBytesKey(key), toBytes(value));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObject {} ", key);
		} catch (Exception e) {
			logger.warn("setObject {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取List缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static List<String> getList(String key) {
		List<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.lrange(key, 0, -1);
				logger.debug("getList {} ", key);
			}
		} catch (Exception e) {
			logger.warn("getList {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 获取List缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static List<?> getObjectList(String key) {
		List<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
				value = Lists.newArrayList();
				for (byte[] bs : list) {
					value.add(toObject(bs));
				}
				logger.debug("getObjectList {} ", key);
			}
		} catch (Exception e) {
			logger.warn("getObjectList {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 设置List缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static long setList(String key, List<String> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			String[] strings = new String[value.size()];
			value.toArray(strings);
			result = jedis.rpush(key, strings);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setList {} ", key);
		} catch (Exception e) {
			logger.warn("setList {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 设置List缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static long setObjectList(String key, List<?> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			List<byte[]> list = Lists.newArrayList();
			for (Object o : value) {
				list.add(toBytes(o));
			}
			byte[][] bytes = new byte[value.size()][];
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = toBytes(value.get(i));
			}
			result = jedis.rpush(getBytesKey(key), bytes);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObjectList {} ", key);
		} catch (Exception e) {
			logger.warn("setObjectList {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向List缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long listAdd(String key, String... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.rpush(key, value);
			logger.debug("listAdd {} ", key);
		} catch (Exception e) {
			logger.warn("listAdd {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向List缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long listObjectAdd(String key, Object... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			List<byte[]> list = Lists.newArrayList();
			for (Object o : value) {
				list.add(toBytes(o));
			}
			result = jedis.rpush(getBytesKey(key), (byte[][]) list.toArray());
			logger.debug("listObjectAdd {} ", key);
		} catch (Exception e) {
			logger.warn("listObjectAdd {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Set<String> getSet(String key) {
		Set<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.smembers(key);
				logger.debug("getSet {} ", key);
			}
		} catch (Exception e) {
			logger.warn("getSet {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Set<Object> getObjectSet(String key) {
		Set<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = Sets.newHashSet();
				Set<byte[]> set = jedis.smembers(getBytesKey(key));
				for (byte[] bs : set) {
					value.add(toObject(bs));
				}
				logger.debug("getObjectSet {} ", key);
			}
		} catch (Exception e) {
			logger.warn("getObjectSet {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 设置Set缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static long setSet(String key, Set<String> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.sadd(key, (String[]) value.toArray());
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setSet {} ", key);
		} catch (Exception e) {
			logger.warn("setSet {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 设置Set缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			Set<byte[]> set = Sets.newHashSet();
			for (Object o : value) {
				set.add(toBytes(o));
			}
			result = jedis.sadd(getBytesKey(key), (byte[][]) set.toArray());
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObjectSet {} ", key);
		} catch (Exception e) {
			logger.warn("setObjectSet {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向Set缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long setSetAdd(String key, String... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.sadd(key, value);
			logger.debug("setSetAdd {} ", key);
		} catch (Exception e) {
			logger.warn("setSetAdd {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向Set缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static long setSetObjectAdd(String key, Object... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			Set<byte[]> set = Sets.newHashSet();
			for (Object o : value) {
				set.add(toBytes(o));
			}
			result = jedis.rpush(getBytesKey(key), (byte[][]) set.toArray());
			logger.debug("setSetObjectAdd {} ", key);
		} catch (Exception e) {
			logger.warn("setSetObjectAdd {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取Map缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Map<String, String> getMap(String key) {
		Map<String, String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.hgetAll(key);
				logger.debug("getMap {} ", key);
			}
		} catch (Exception e) {
			logger.warn("getMap {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 获取Map缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public static Map<String, Object> getObjectMap(String key) {
		Map<String, Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = Maps.newHashMap();
				Map<byte[], byte[]> map = jedis.hgetAll(getBytesKey(key));
				for (Map.Entry<byte[], byte[]> e : map.entrySet()) {
					value.put(StringUtils.toString(e.getKey()), toObject(e.getValue()));
				}
				logger.debug("getObjectMap {} ", key);
			}
		} catch (Exception e) {
			logger.warn("getObjectMap {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String setMap(String key, Map<String, String> value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.hmset(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setMap {} ", key);
		} catch (Exception e) {
			logger.warn("setMap {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public static String setObjectMap(String key, Map<String, ?> value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			Map<byte[], byte[]> map = Maps.newHashMap();
			for (Map.Entry<String, ?> e : value.entrySet()) {
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObjectMap {}", key);
		} catch (Exception e) {
			logger.warn("setObjectMap {} ", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向Map缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static String mapPut(String key, Map<String, String> value) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hmset(key, value);
			logger.debug("mapPut {} ", key);
		} catch (Exception e) {
			logger.warn("mapPut {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向Map缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static String mapObjectPut(String key, Map<String, Object> value) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			Map<byte[], byte[]> map = Maps.newHashMap();
			for (Map.Entry<String, Object> e : value.entrySet()) {
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>) map);
			logger.debug("mapObjectPut {} ", key);
		} catch (Exception e) {
			logger.warn("mapObjectPut {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向Map缓存中添加值
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public static Long mapObjectPut(String key, String field,Object value, int cacheSeconds) {
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();			
			result = jedis.hset(getBytesKey(key),getBytesKey(field), toBytes(value));
			
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			
			logger.debug("mapObjectPut {} ", key);
		} catch (Exception e) {
			logger.warn("mapObjectPut {} , {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 移除Map缓存中的值
	 * 
	 * @param key
	 *            键
	 * @param mapKey
	 *            值
	 * @return
	 */
	public static long mapRemove(String key, String mapKey) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hdel(key, mapKey);
			logger.debug("mapRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapRemove {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 移除Map缓存中的值
	 * 
	 * @param key
	 *            键
	 * @param mapKey
	 *            值
	 * @return
	 */
	public static long mapObjectRemove(String key, String mapKey) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hdel(getBytesKey(key), getBytesKey(mapKey));
			logger.debug("mapObjectRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapObjectRemove {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 判断Map缓存中的Key是否存在
	 * 
	 * @param key
	 *            键
	 * @param mapKey
	 *            值
	 * @return
	 */
	public static boolean mapExists(String key, String mapKey) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hexists(key, mapKey);
			logger.debug("mapExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapExists {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 判断Map缓存中的Key是否存在
	 * 
	 * @param key
	 *            键
	 * @param mapKey
	 *            值
	 * @return
	 */
	public static boolean mapObjectExists(String key, String mapKey) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hexists(getBytesKey(key), getBytesKey(mapKey));
			logger.debug("mapObjectExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.warn("mapObjectExists {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static long del(String key) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				result = jedis.del(key);
				logger.debug("del {}", key);
			} else {
				logger.debug("del {} not exists", key);
			}
		} catch (Exception e) {
			logger.warn("del {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static long delObject(String key) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				result = jedis.del(getBytesKey(key));
				logger.debug("delObject {}", key);
			} else {
				logger.debug("delObject {} not exists", key);
			}
		} catch (Exception e) {
			logger.warn("delObject {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * [*]模糊匹配删除
	 * 
	 * @param key
	 * @param type
	 *            （1-前匹配， 2-后匹配， 3-前后匹配）
	 */
	public static List<String> batchDel(String key, Integer type) {
		if (StringUtils.isNotBlank(key)) {
			List<String> rzList = new LinkedList<String>();
			Set<String> set = null;
			Jedis jedis = getResource();
			try {
				switch (type) {
				case 1:
					set = jedis.keys("**" + key);
					break;
				case 2:
					set = jedis.keys(key + "**");
					break;
				case 3:
					set = jedis.keys("**" + key + "**");
					break;
				default:
					logger.error("type is error");
				}
				if (null != set) {
					Iterator<String> it = set.iterator();
					while (it.hasNext()) {
						String keyStr = it.next();
						long rz = del(keyStr);
						if (rz != 0) {
							rzList.add(keyStr);
						}
					}
				} else {
					long rz = del(key);
					if (rz != 0) {
						rzList.add(key);
					}
				}
			} catch (Exception e) {
				logger.warn("batchDel {}", key, e);
			} finally {
				returnResource(jedis);
			}
			return rzList;
		}
		return null;
	}

	/**
	 * [*]模糊匹配key
	 * 
	 * @param key
	 * @param type
	 *            （1-前匹配， 2-后匹配， 3-前后匹配）
	 */
	public static Set<String> getKeys(String key, Integer type) {
		if (StringUtils.isNotBlank(key)) {
			Set<String> set = null;
			Jedis jedis = getResource();
			try {
				switch (type) {
				case 1:
					set = jedis.keys("**" + key);
					break;
				case 2:
					set = jedis.keys(key + "**");
					break;
				case 3:
					set = jedis.keys("**" + key + "**");
					break;
				default:
					logger.error("type is error");
				}
			} catch (Exception e) {
				logger.warn("getKeys {}", key, e);
			} finally {
				returnResource(jedis);
			}
			return set;
		}
		return null;
	}

	/**
	 * 缓存是否存在
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static boolean exists(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.exists(key);
			logger.debug("exists {}", key);
		} catch (Exception e) {
			logger.warn("exists {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 缓存是否存在
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public static boolean existsObject(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.exists(getBytesKey(key));
			logger.debug("existsObject {}", key);
		} catch (Exception e) {
			logger.warn("existsObject {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取资源
	 * 
	 * @return
	 * @throws JedisException
	 */
	public static Jedis getResource() throws JedisException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// logger.debug("getResource.", jedis);
		} catch (JedisException e) {
			logger.warn("getResource.", e);
			returnBrokenResource(jedis);
			throw e;
		}
		return jedis;
	}

	/**
	 * 归还资源
	 * 
	 * @param jedis
	 */
	public static void returnBrokenResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnBrokenResource(jedis);
		}
	}

	/**
	 * 释放资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 获取byte[]类型Key
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] getBytesKey(Object object) {
		if (object instanceof String) {
			return StringUtils.getBytes((String) object);
		} else {
			return ObjectUtils.serialize(object);
		}
	}

	/**
	 * 获取byte[]类型Key
	 * 
	 * @param key
	 * @return
	 */
	public static Object getObjectKey(byte[] key) {
		try {
			return StringUtils.toString(key);
		} catch (UnsupportedOperationException uoe) {
			try {
				return JedisUtils.toObject(key);
			} catch (UnsupportedOperationException uoe2) {
				uoe2.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Object转换byte[]类型
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] toBytes(Object object) {
		return ObjectUtils.serialize(object);
	}

	/**
	 * byte[]型转换Object
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object toObject(byte[] bytes) {
		return ObjectUtils.unserialize(bytes);
	}

	/**
	 * <p>
	 * 原子递增,用于统计
	 * </p>
	 * <p>
	 * 方法调用一次加一
	 * </p>
	 * 
	 * @param key
	 *            递增的值的模块代号
	 * @param field
	 *            递增的值
	 */
	public static void increaseByKey(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			// 原子递增
			Long count = jedis.hincrBy(key, field, 1);
			jedis.hset(key, field, String.valueOf(count));
		} catch (Exception e) {
			logger.warn("increaseByKey {} , {}, {}", key, field, e);
		} finally {
			returnResource(jedis);
		}
	}

	public static Long addByKey(String key) {
		Jedis jedis = null;
		Long count = null;
		try {
			jedis = getResource();
			// 原子递增
			count = jedis.incr(key);
		} catch (Exception e) {
			logger.warn("addByKey {}-{}", key,count, e);
		} finally {
			returnResource(jedis);
		}
		return count;
	}

	public static Long subValueByKey(String key, Long value) {
		Jedis jedis = null;
		Long count = null;
		try {
			jedis = getResource();
			// 原子性减去value
			count = jedis.decrBy(key, value);
		} catch (Exception e) {
			logger.warn("addByKey {}-{}", key, count, e);
		} finally {
			returnResource(jedis);
		}
		return count;
	}

	/**
	 * <p>
	 * 统计减少,用于清零
	 * </p>
	 * 
	 * @param key
	 *            递增的值的模块代号
	 * @param field
	 *            递增的值
	 * @param value
	 *            要减少的值
	 */
	public static void delIncreaseByKey(String key, String field, Long value) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			Long count = jedis.hincrBy(key, field, value > 0 ? 0L - value : value);
			jedis.hset(key, field, String.valueOf(count));
		} catch (Exception e) {
			logger.warn("increaseByKey {} , {}, {}, {}", key, field, value, e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * <p>
	 * 得到统计数据
	 * </p>
	 * 
	 * @param key
	 *            递增的值的模块代号
	 * @param field
	 *            递增的值
	 */
	public static Long getIncreaseByKey(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.hexists(key, field)) {
				return Long.valueOf(jedis.hget(key, field));
			}
		} catch (Exception e) {
			logger.warn("increaseByKey {} , {}, {}", key, field, e);
		} finally {
			returnResource(jedis);
		}
		return 0L;
	}

	/**
	 * code y0621
	 * 
	 * @Title: setLock
	 * @Description: 设置缓存锁与过期时间（原子性）
	 * @param @param key
	 * @param @param value
	 * @param @param seconds 过期时间（秒）
	 * @return boolean 返回类型
	 */
	public static boolean setLock(String key, String value, int seconds) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getResource();
			result = jedis.set(key, value, "NX", "EX", seconds);
			if ("OK".equals(result)) {
				logger.debug("lock {} success", key);
				return true;
			}
		} catch (Exception e) {
			logger.warn("lock {} fail", key, e);
		} finally {
			returnResource(jedis);
		}
		return false;
	}

	/**
	 * code y0913
	 * 
	 * @Title: setObjectEX
	 * @Description: 设置带过期时间缓存 （原子性）
	 * @param @param key
	 * @param @param value
	 * @param @param seconds 过期时间（秒）
	 */
	public static boolean setObjectEX(String key, Object value, int seconds) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = getResource();
			result = jedis.set(getBytesKey(key), toBytes(value), "NX".getBytes(), "EX".getBytes(), seconds);
			if ("OK".equals(result)) {
				logger.debug("setObjectEX {} success", key);
				return true;
			}
		} catch (Exception e) {
			logger.warn("lock {} fail", key, e);
		} finally {
			returnResource(jedis);
		}
		return false;
	}

	/**
	 * code y0621
	 * 
	 * @Title: releaseLock
	 * @Description: 对比请求标识后释放锁 确保原子性
	 * @param @param key 锁
	 * @param @param requestId 请求标识
	 * @return boolean 是否释放成功
	 */
	public static boolean releaseLock(String key, String requestId) {
		Jedis jedis = null;
		Object result = "0";
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
				result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(requestId));
			} else {
				logger.debug("del {} not exists", key);
			}
			if ("1".equals(result.toString())) {
				logger.debug("del {} success", key);
				return true;
			}
		} catch (Exception e) {
			logger.warn("del {} fail", key, e);
		} finally {
			returnResource(jedis);
		}
		return false;

	}
}
