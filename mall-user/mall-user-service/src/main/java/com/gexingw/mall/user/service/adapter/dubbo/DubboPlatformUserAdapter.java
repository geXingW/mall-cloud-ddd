package com.gexingw.mall.user.service.adapter.dubbo;

import com.gexingw.mall.infrastucture.core.enums.RespCode;
import com.gexingw.mall.infrastucture.core.exception.BaseBizException;
import com.gexingw.mall.user.client.dubbo.DubboPlatformUser;
import com.gexingw.mall.user.service.application.service.PlatformUserService;
import com.gexingw.mall.user.service.infra.dao.PUserPO;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;

@DubboService
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class DubboPlatformUserAdapter implements DubboPlatformUser {

    private final PlatformUserService platformUserService;


    @Override
    public @NotNull String getById(Long id) {
        PUserPO pUserPO = platformUserService.getById(id);
        if (pUserPO == null) {
            throw new BaseBizException(RespCode.NOT_FOUND, "用户不存在！");
        }

        return pUserPO.getNickname();
    }

    @Override
    public int number(int num) {
        return 0;
    }

}
