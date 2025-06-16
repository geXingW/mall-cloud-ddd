package com.gexingw.mall.user.client.dubbo;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/13 10:31
 */
public interface DubboPlatformUser {

    String getById(Long id);

    int number(int num);

}
