package com.gexingw.mall.user.service.infra.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gexingw.mall.infrastructure.db.BasePO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("p_user")
@Accessors(chain = true)
public class PUserPO extends BasePO {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 登录用户Id
     */
    private Long authUserId;

}
