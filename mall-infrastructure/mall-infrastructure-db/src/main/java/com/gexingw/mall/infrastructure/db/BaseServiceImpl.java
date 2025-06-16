package com.gexingw.mall.infrastructure.db;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class BaseServiceImpl<M extends BaseMapper<P>, P extends BasePO> extends ServiceImpl<BaseMapper<P>, P> implements BaseService<P> {

}
