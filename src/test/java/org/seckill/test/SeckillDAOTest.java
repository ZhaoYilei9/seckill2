package org.seckill.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 配置spring和junit整合junit 启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
/**
 * 告诉junit spring配置文件的位置
 */
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class SeckillDAOTest {

//    private static final Logger logger = LoggerFactory.getLogger(SeckillDAOTest.class);

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() {
        int num = seckillDao.reduceNumber(1001,new Date());
        System.out.println(new Date());
        System.out.println(num);
    }

    @Test
    public void queryById() {

        Seckill seckill = seckillDao.queryById(1000l);
        System.out.println(seckill);
//        logger.debug("seckill={}",seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> list = seckillDao.queryAll(2,3);
        for (Seckill s: list) {
            System.out.println(s);
        }

    }
}