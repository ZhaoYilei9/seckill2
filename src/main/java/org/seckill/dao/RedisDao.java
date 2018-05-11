package org.seckill.dao;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
    private final JedisPool jedisPool;
    private static final Logger logger = LoggerFactory.getLogger(RedisDao.class);
    public RedisDao(String ip,int port) {
        this.jedisPool = new JedisPool(ip,port);
    }
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    //反序列化过程
    public Seckill getSeckill(Long seckillId){
        try {
            Jedis jedis = this.jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                byte[] bytes =jedis.get(key.getBytes());
                if (bytes != null){
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    return seckill;
                }
            }finally {
                jedis.close();
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
//序列化过程
public String putSeckill(Seckill seckill){
        try{
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,schema,LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timetout = 60 * 60;
                String result = jedis.setex(key.getBytes(),timetout,bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
}

}
