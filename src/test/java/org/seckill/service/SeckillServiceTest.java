package org.seckill.service;

import org.apache.taglibs.standard.tag.rt.core.ForEachTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.swing.text.StyledEditorKit;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class SeckillServiceTest {

    public static final Logger logger = LoggerFactory.getLogger(SeckillServiceTest.class);
    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckills = seckillService.getSeckillList();
//        for(Seckill seckill : seckills){
//            System.out.println(seckill);
//        }
        logger.info("seckills={}",seckills);

    }

    @Test
    public void getSeckill() {
        Seckill seckill =seckillService.getSeckill(1001);
        System.out.println(seckill.getStartTime().getTime());
//        logger.info("seckill={}" ,seckill);
        System.out.println(new Date().getTime());

    }

    @Test
    public void exportSeckillUrl() {
        Exposer exposer = seckillService.ExportSeckillUrl(1004);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void excuteSeckill() {
        String md5 = "d774d0c3ffb816aa1fd71bcdb8810ba1";
        SeckillExcution excution = seckillService.ExcuteSeckill(1004,17763238629l,md5);
        logger.info("excution={}",excution);

    }
}