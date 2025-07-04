package com.gexingw.mall.auth.service.infra.dao.authuser.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gexingw.mall.infrastructure.db.BasePO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * mall-cloud
 *
 * @author GeXingW
 * @date 2024/5/18 17:51
 */
@Data
@Accessors(chain = true)
@TableName("auth_user")
public class AuthUserPO extends BasePO {

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email = "";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 密码重置时间
     */
    private LocalDateTime passwdResetTime;

}
