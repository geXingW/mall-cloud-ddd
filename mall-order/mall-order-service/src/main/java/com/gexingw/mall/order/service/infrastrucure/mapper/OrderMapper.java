package com.gexingw.mall.order.service.infrastrucure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gexingw.mall.order.service.infrastrucure.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 14:08
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
}
