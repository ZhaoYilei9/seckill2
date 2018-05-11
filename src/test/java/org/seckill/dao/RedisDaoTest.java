package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private long id =1003;
    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;



    @Test
    public void redisDao() throws Exception {
        Seckill seckill = redisDao.getSeckill(id);
        if (seckill == null){
            seckill = seckillDao.queryById(id);
            if (seckill != null){
                String result = redisDao.putSeckill(seckill);

                seckill = redisDao.getSeckill(id);
                System.out.println(seckill + ":" + result);

            }
        }else {
            System.out.println(seckill);
        }
    }
}