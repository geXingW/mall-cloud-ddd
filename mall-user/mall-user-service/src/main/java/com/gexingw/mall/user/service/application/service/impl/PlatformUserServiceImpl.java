package com.gexingw.mall.user.service.application.service.impl;

import com.gexingw.mall.infrastructure.db.BaseServiceImpl;
import com.gexingw.mall.user.service.application.service.PlatformUserService;
import com.gexingw.mall.user.service.infra.dao.PUserMapper;
import com.gexingw.mall.user.service.infra.dao.PUserPO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @time 2023/11/13 10:31
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class PlatformUserServiceImpl extends BaseServiceImpl<PUserMapper, PUserPO> implements PlatformUserService {


}
