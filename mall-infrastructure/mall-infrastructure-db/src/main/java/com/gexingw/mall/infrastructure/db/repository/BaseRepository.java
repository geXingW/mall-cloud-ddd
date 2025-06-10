package com.gexingw.mall.infrastructure.db.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/1 15:36
 */
public class BaseRepository<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

}
