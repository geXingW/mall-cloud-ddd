package com.gexingw.shop.infrastucture.core.exception;

import com.gexingw.shop.infrastucture.core.enums.RespCode;
import lombok.Getter;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/12 18:33
 */
@Getter
@SuppressWarnings("unused")
public class BaseBizException extends RuntimeException{

    private static final long serialVersionUID = -8782484287836146746L;

    protected RespCode respCode;

    public BaseBizException(String message) {
        super(message);
    }

    public BaseBizException(RespCode respCode) {
        super();
        this.respCode = respCode;
    }

    public BaseBizException(RespCode respCode, String message) {
        super(message);
        this.respCode = respCode;
    }

}
