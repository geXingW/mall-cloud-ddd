package com.gexingw.mall.infrastucture.core.util;

import com.gexingw.mall.infrastucture.core.enums.RespCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/9/23 15:07
 */
@SuppressWarnings("unused")
@Getter
public class R<T> implements Serializable {

    private static final long serialVersionUID = -554069322802646072L;

    private final int code;

    private final boolean success;

    private final T data;

    private final String message;

    public R(int code, boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> R<T> ok() {
        return new R<>(RespCode.SUCCESS.getCode(), true, RespCode.SUCCESS.getMessage(), null);
    }

    public static <T> R<T> ok(String message) {
        return new R<>(RespCode.SUCCESS.getCode(), true, message, null);
    }

    public static <T> R<T> ok(RespCode respCode) {
        return new R<>(respCode.getCode(), true, respCode.getMessage(), null);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(RespCode.SUCCESS.getCode(), true, RespCode.SUCCESS.getMessage(), data);
    }

    public static <T> R<T> ok(T data, String message) {
        return new R<>(RespCode.SUCCESS.getCode(), true, message, data);
    }

    public static <T> R<T> fail(String message) {
        return new R<>(RespCode.ERROR.getCode(), true, message, null);
    }

    public static <T> R<T> fail(RespCode respCode){
        return new R<>(respCode.getCode(), false, respCode.getMessage(), null);
    }

    public static <T> R<T> fail(RespCode respCode, String message){
        return new R<>(respCode.getCode(), false, message, null);
    }
    public static <T> R<T> fail(int code, String message) {
        return new R<>(code, false, message, null);
    }

    public static <T> R<T> fail(T data, int code, String message) {
        return new R<>(code, false, message, data);
    }

}
