package com.gexingw.mall.auth.service.infra.dao.authuser.mapper;


import com.gexingw.mall.auth.service.infra.dao.authuser.po.AuthUserPO;
import com.gexingw.mall.infrastructure.db.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * mall-cloud
 *
 * @author GeXingW
 * @date 2024/5/18 17:57
 */
@Mapper
public interface AuthUserMapper extends BaseMapper<AuthUserPO> {

}
