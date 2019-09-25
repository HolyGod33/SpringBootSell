package com.imooc.Service;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:39 2019/9/9
 */
@Commit
@Slf4j
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *=============================
     * @author: JXUT CXY
     * @Date: 20:42 2019/9/9
     * @param key productId
     * @param value 当前时间+超时时间  即是该锁的过期时间
     * @return:
     * create with IDEA
     *=============================
     */
    public boolean lock(String key, String value) {
        //原理:将当前productId与过期时间存入redis中,成功存入(返回true)即表示当前时间片没有其他线程来使用该productId进行修改,即是线程安全的
        //不成功(返回false)即表示已经有其他线程使用该productId,即不能修改该productId的信息
        if (redisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }
        String currentValue = redisTemplate.opsForValue().get(key);
        //如果锁过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue)  < System.currentTimeMillis()) {
            //获取上一个锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key,value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }

        return false;
    }
    
    public void unlock(String key,String value) {
        try{
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e) {
            log.error("[redis分布式锁] 解锁异常");
        }
    }
}
