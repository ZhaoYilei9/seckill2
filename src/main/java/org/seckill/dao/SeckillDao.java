package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    /**
     * 减库存操作
     * @param seckillId ；秒杀商品id
     * @param killTime ：秒杀的时间
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 查询单个秒杀商品的信息
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品的集合
     * Caused by: org.apache.ibatis.binding.BindingException:
     * Parameter 'offset' not found. Available parameters are [limit, offse, param1, param2]
     * @param offset：偏移量
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);


}
