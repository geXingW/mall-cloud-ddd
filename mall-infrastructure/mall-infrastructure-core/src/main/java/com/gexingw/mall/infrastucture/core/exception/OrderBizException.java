package com.gexingw.mall.infrastucture.core.exception;

import com.gexingw.mall.infrastucture.core.enums.RespCode;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/12 18:32
 */
@SuppressWarnings("unused")
public class OrderBizException extends BaseBizException {

    private static final long serialVersionUID = -2907363500429212777L;

    public OrderBizException(String message) {
        super(message);
    }

    public OrderBizException(RespCode respCode) {
        super(respCode);
    }

    public OrderBizException(RespCode respCode, String message) {
        super(respCode, message);
    }

}
