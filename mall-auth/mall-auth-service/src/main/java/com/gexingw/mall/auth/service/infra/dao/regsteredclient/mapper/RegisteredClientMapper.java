package com.gexingw.mall.auth.service.infra.dao.regsteredclient.mapper;

import com.gexingw.mall.auth.service.infra.dao.regsteredclient.po.RegisteredClientPO;
import com.gexingw.mall.infrastructure.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * mall-user-service
 *
 * @author GeXingW
 * @date 2024/2/17 13:02
 */
@Mapper
public interface RegisteredClientMapper extends BaseMapper<RegisteredClientPO> {

}
