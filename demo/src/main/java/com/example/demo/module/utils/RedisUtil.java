package com.example.demo.module.utils;


import com.example.demo.applicationcontext.SpringApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 共支持五种数据类型：String,list,hash,set,zset
 */

@Slf4j
public class RedisUtil implements InitializingBean {



    private static RedisTemplate<String,Serializable> redisTemplate=null;

    //@Autowired 不能用于静态变量
    @Autowired
    private ApplicationContext applicationContext;

    /*
       前提需要RedisUtil受spring容器管理才能初始化afterPropertiesSet
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        redisTemplate=(RedisTemplate)SpringApplicationContext.getBean(RedisTemplate.class);
        //获取redis链接工厂(里面包含连接信息和登入账号密码信息)，默认连接第一个数据库，即下标为0，详细进入JedisConnectionFactory类查看
        JedisConnectionFactory connectionFactory=(JedisConnectionFactory)redisTemplate.getConnectionFactory();
        connectionFactory.setDatabase(10);//切换redis数据库，默认16个
       /* redisTemplate=(RedisTemplate)applicationContext.getBean(RedisTemplate.class);*/
    }
/*-------------------------------------String类型测试---------------------------------*/
    /**
     * 通过key删除对应key的缓存键值对
     * @param key
     */
    public static void remove(String key){
        //判断是否存在key
        if(redisTemplate.hasKey(key)){
            redisTemplate.delete(key);
        }
    }

    /**
     * 模糊匹配所有key,并批量删除
     * @param parttarn
     */
    public static void batchRemove(String parttarn){
        Set<String> keys=redisTemplate.keys(parttarn);
        redisTemplate.delete(keys);
    }

    /**
     * 获取对应key的value
     * @param key
     * @return
     */
    public static Object getValue(String key){
        Object value=redisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     * 添加缓存
     * @param key
     * @param value
     */
    public static void putValue(String key,String value){
        log.info("添加缓存键值对{}，{}",key,value);
        redisTemplate.opsForValue().set(key,value);
        //添加缓存并设值过期时间TimeUnit.MINUTES为时间单位
        //redisTemplate.opsForValue().set(key,value,1000,TimeUnit.MINUTES);
        //重新设值key对应的value
        //redisTemplate.opsForValue().setIfAbsent(key,value);
        log.info("添加缓存键值对");
    }

    /**
     * 设值缓存过期时间
     */
    public static void setExpire(String key,long timeOut,TimeUnit timeUnit){
        redisTemplate.expire(key,timeOut,timeUnit);
    }
    public static void setExpireDate(String key,Date date){
        redisTemplate.expireAt(key,date);
    }

    /**
     * 设值缓存持久化，即移除对应key的过期时间，让其永久保存
     * @param key
     */
    public static void setPersisKey(String key){
        redisTemplate.persist(key);
    }

    /**
     * 将redis的key序列化
     * @param key
     */
    public static void seriKey(String key){
        if(redisTemplate.hasKey(key)){
            byte[] bytes=redisTemplate.dump(key);
        }
    }

    /**
     * 重命名key
     * @param oldKey
     * @param newKey
     */
    public static void renameKey(String oldKey,String newKey){
        redisTemplate.rename(oldKey,newKey);
    }

    public static void renameKeyIfExist(String oldKey,String newKey){
        //如果key存在，则重命名
        redisTemplate.renameIfAbsent(oldKey,newKey);
    }

    /**
     * 返回传入key所存储的值的类型,DataType 中包含redis所有支持的数据类型
     * @return
     */
    public static DataType getValueType(String key){
        return redisTemplate.type(key);
    }


    /**
     * 获取个随机的key
     */
    public static void randomKey(){
        String randomKey=redisTemplate.randomKey();
    }

    /**
     * 获取对应key剩余的过期时间
     * @param key
     */
    public static void getExpireKey(String key){
        long residueTime=redisTemplate.getExpire(key);
        //获取对应key剩余的过期时间并指定单位
        long residueTime1=redisTemplate.getExpire(key,TimeUnit.DAYS);
    }

    /**
     * 将当前数据库的key移动到指定redis中数据库当中
     */
    public static void exchangeKeyToDataBase(String key,Integer dbIndex){
        redisTemplate.move(key,dbIndex);
    }

    /**
     * 返回key中字符串的子字符
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static String getKeyPart(String key,long start,long end){
        return redisTemplate.opsForValue().get(key,start,end);
    }

    /**
     * 根据key集合批量获取value
     * @param keys
     */
    public static void batchGetValues(List<String> keys){
        List<Serializable> listValues=redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 追加缓存对应key的长度
     * @param key
     * @param appendStr
     */
    public static void appendStrKey(String key,String appendStr){
        redisTemplate.opsForValue().append(key,appendStr);
    }

    /**
     * 对对应key的value进行加法运算
     * @param key
     */
    public static void incrValue(String key){
        double incrementd=1.0;
        float  incrementf=2.3f;
        redisTemplate.opsForValue().increment(key,incrementd);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     * @param map
     */
    public static void  addKeyFromMap(HashMap<String,String> map){
        redisTemplate.opsForValue().multiSetIfAbsent(map);
        //
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 获取key的长度
     * @param key
     * @return
     */
    public static long getKeyLength(String key){
        return redisTemplate.opsForValue().size(key);
    }

}
