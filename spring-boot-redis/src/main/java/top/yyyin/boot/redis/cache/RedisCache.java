package top.yyyin.boot.redis.cache;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存工具类
 * 封装Redis常用操作，简化Redis使用，提供统一的缓存操作接口
 *
 * <p>主要功能：</p>
 * <ul>
 *   <li>String类型：基础的键值对存储操作</li>
 *   <li>Hash类型：哈希表操作，适用于对象属性存储</li>
 *   <li>List类型：列表操作，支持队列和栈的功能</li>
 *   <li>过期时间：统一的过期时间管理</li>
 * </ul>
 *
 * @author mqxu
 * @since 2025/9/24
 */
@Component
public class RedisCache {

    // ====================== 常量定义 ======================

    /** 默认过期时长：24小时（单位：秒） */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24L;

    /** 1小时过期时长（单位：秒） */
    public final static long HOUR_ONE_EXPIRE = 60 * 60L;

    /** 6小时过期时长（单位：秒） */
    public final static long HOUR_SIX_EXPIRE = 60 * 60 * 6L;

    /** 永不过期标识 */
    public final static long NOT_EXPIRE = -1L;

    /** Redis操作模板 */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // ====================== String类型操作 ======================

    /**
     * 存储键值对并设置过期时间
     *
     * @param key    键，不能为空
     * @param value  值，支持任意对象类型
     * @param expire 过期时间（秒），-1表示永不过期
     */
    public void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 存储键值对，使用默认过期时间（24小时）
     *
     * @param key   键，不能为空
     * @param value 值，支持任意对象类型
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 获取键对应的值，并重新设置过期时间
     * 适用于需要延长缓存生存时间的场景
     *
     * @param key    键，不能为空
     * @param expire 新的过期时间（秒），-1表示不修改过期时间
     * @return 键对应的值，不存在则返回null
     */
    public Object get(String key, long expire) {
        Object value = redisTemplate.opsForValue().get(key);
        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
        return value;
    }

    /**
     * 获取键对应的值
     *
     * @param key 键，不能为空
     * @return 键对应的值，不存在则返回null
     */
    public Object get(String key) {
        return get(key, NOT_EXPIRE);
    }

    /**
     * 将键的值递增1
     * 如果键不存在，则先设置为0再递增
     *
     * @param key 键，不能为空
     * @return 递增后的值
     */
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 判断键是否存在
     *
     * @param key 键，不能为空
     * @return true表示存在，false表示不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除单个键
     *
     * @param key 要删除的键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除键
     *
     * @param keys 要删除的键集合
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    // ====================== Hash类型操作 ======================

    /**
     * 获取Hash中指定字段的值
     *
     * @param key   Hash键，不能为空
     * @param field 字段名，不能为空
     * @return 字段对应的值，不存在则返回null
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取Hash中所有字段和值
     * 适用于获取完整对象信息的场景
     *
     * @param key Hash键，不能为空
     * @return 包含所有字段和值的Map，Hash不存在则返回空Map
     */
    public Map<String, Object> hGetAll(String key) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }

    /**
     * 批量设置Hash中的字段和值，使用默认过期时间
     *
     * @param key Hash键，不能为空
     * @param map 字段和值的映射，不能为空
     */
    public void hMSet(String key, Map<String, Object> map) {
        hMSet(key, map, DEFAULT_EXPIRE);
    }

    /**
     * 批量设置Hash中的字段和值，并设置过期时间
     * 适用于缓存完整对象信息的场景
     *
     * @param key    Hash键，不能为空
     * @param map    字段和值的映射，不能为空
     * @param expire 过期时间（秒），-1表示永不过期
     */
    public void hMSet(String key, Map<String, Object> map, long expire) {
        redisTemplate.opsForHash().putAll(key, map);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 设置Hash中单个字段的值，使用默认过期时间
     *
     * @param key   Hash键，不能为空
     * @param field 字段名，不能为空
     * @param value 字段值，支持任意对象类型
     */
    public void hSet(String key, String field, Object value) {
        hSet(key, field, value, DEFAULT_EXPIRE);
    }

    /**
     * 设置Hash中单个字段的值，并设置过期时间
     *
     * @param key    Hash键，不能为空
     * @param field  字段名，不能为空
     * @param value  字段值，支持任意对象类型
     * @param expire 过期时间（秒），-1表示永不过期
     */
    public void hSet(String key, String field, Object value, long expire) {
        redisTemplate.opsForHash().put(key, field, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 删除Hash中的指定字段
     *
     * @param key    Hash键，不能为空
     * @param fields 要删除的字段名，可以是多个
     */
    public void hDel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    // ====================== List类型操作 ======================

    /**
     * 从列表左侧插入元素，使用默认过期时间
     * 适用于队列（FIFO）数据结构
     *
     * @param key   列表键，不能为空
     * @param value 要插入的值，支持任意对象类型
     */
    public void leftPush(String key, Object value) {
        leftPush(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 从列表左侧插入元素，并设置过期时间
     *
     * @param key    列表键，不能为空
     * @param value  要插入的值，支持任意对象类型
     * @param expire 过期时间（秒），-1表示永不过期
     */
    public void leftPush(String key, Object value, long expire) {
        redisTemplate.opsForList().leftPush(key, value);

        if (expire != NOT_EXPIRE) {
            expire(key, expire);
        }
    }

    /**
     * 从列表右侧弹出元素
     * 与leftPush配合使用可实现队列（FIFO）功能
     *
     * @param key 列表键，不能为空
     * @return 弹出的元素，列表为空则返回null
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    // ====================== 过期时间管理 ======================

    /**
     * 设置键的过期时间
     *
     * @param key    键，不能为空
     * @param expire 过期时间（秒），必须大于0
     */
    public void expire(String key, long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }
}