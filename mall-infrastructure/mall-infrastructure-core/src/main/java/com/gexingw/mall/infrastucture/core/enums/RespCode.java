package com.gexingw.mall.infrastucture.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/12 18:36
 */
@Getter
@AllArgsConstructor
public enum RespCode {

    /**
     * 成功的响应
     */
    SUCCESS(200000, "成功！"),
    CREATED(200000, "已保存！"),
    UPDATED(200000, "已更新！"),
    DELETED(200000, "已删除！"),

    PARAM_MISS(400001, "缺少必要的请求参数！"),
    PARAM_TYPE_ERROR(400002, "请求参数格式错误！"),
    PARAM_BIND_ERROR(400003, "参数绑定错误！"),
    PARAM_VALID_ERROR(400004, "参数校验失败!"),
    MSG_NOT_READABLE(400005, "消息不能读取！"),
    RECORD_HAS_EXISTED(400006, "数据重复！"),

    NOT_FOUND(404000, "未找到资源！"),
    SOURCE_NOT_FOUND(404001, "记录不存在！"),

    METHOD_NOT_SUPPORTED(405000, "不支持的请求方式！"),

    MEDIA_TYPE_NOT_SUPPORTED(415000, "不支持的媒体类型！"),

    /**
     * 失败的响应
     */
    ERROR(500000, "请求失败！"),
    QUERY_ERROR(500010, "查询失败，稍后再试试吧！"),
    CREATE_ERROR(500011, "创建失败，稍后再试试吧！"),
    UPDATE_ERROR(500012, "更新失败，稍后再试试吧！"),
    CANCEL_ERROR(500013, "取消失败，稍后再试试吧！"),
    DELETE_ERROR(500014, "删除失败，稍后再试试吧！"),

    ;
    private final int code;

    private final String message;


}

