package com.gexingw.shop.order.service.infrastrucure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gexingw.shop.order.service.infrastrucure.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 14:08
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
}
