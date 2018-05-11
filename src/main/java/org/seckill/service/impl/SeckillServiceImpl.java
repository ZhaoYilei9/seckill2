package org.seckill.service.impl;

import org.seckill.dao.RedisDao;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatSeckillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    private static final Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    private final String salt = "sjjdajdadjiwdiwidjf";

    public List<Seckill> getSeckillList() {

        return seckillDao.queryAll(0, 5);
    }

    public Seckill getSeckill(long seckillId) {
        return seckillDao.queryById(seckillId);
    }
    //暴露秒杀地址
    public Exposer ExportSeckillUrl(long seckillId) {
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null){
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            }else {
                redisDao.putSeckill(seckill);
            }
        }


        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        System.out.println(startTime.getTime());
        System.out.println(nowTime.getTime());
        System.out.println(endTime.getTime());
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMd5(long seckillId) {

        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());

        return md5;
    }
    @Transactional
    public SeckillExcution ExcuteSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatSeckillException {
        if (md5 == null || !md5.equals(this.getMd5(seckillId))){
            throw new SeckillException(SeckillStateEnum.DATA_REWRITE.getStateInfo());
        }
        try {
            Date killTime = new Date();
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0){
                throw new RepeatSeckillException(SeckillStateEnum.REPEATE_KILL.getStateInfo());
            }else {
                SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                int updateCount = seckillDao.reduceNumber(seckillId, killTime);
                if (updateCount <= 0){
                    throw new SeckillCloseException(SeckillStateEnum.END.getStateInfo());
                }else {
                    return new SeckillExcution(SeckillStateEnum.SUCCESS,seckillId,successKilled);
                }
            }
//
//            if (updateCount <= 0) {
//
//            } else {
//
//                if (insertCount <= 0) {
//                    throw new RepeatSeckillException(SeckillStateEnum.REPEATE_KILL.getStateInfo());
//                } else {
//                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
//                    return new SeckillExcution(SeckillStateEnum.SUCCESS,seckillId,successKilled);
//                }
//
//            }
        }catch (RepeatSeckillException e){
            throw e;

        }catch (SeckillCloseException e){
            throw e;
        }
        catch (Exception e){


            throw new SeckillException(SeckillStateEnum.INNER_ERROR.getStateInfo());
        }




    }

}
