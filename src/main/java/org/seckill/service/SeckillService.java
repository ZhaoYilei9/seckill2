package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatSeckillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

public interface SeckillService {
    /**
     * 查询所有秒杀商品
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getSeckill(long seckillId);

    /**
     * 秒杀开启则暴露秒杀地址
     * 否则返回系统时间 和 秒杀时间
     * @param seckillId
     */
    Exposer ExportSeckillUrl(long seckillId);

    SeckillExcution ExcuteSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,SeckillCloseException,RepeatSeckillException;
}
