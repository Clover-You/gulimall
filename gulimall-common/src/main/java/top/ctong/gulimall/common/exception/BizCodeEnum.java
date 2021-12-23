package top.ctong.gulimall.common.exception;

import lombok.Getter;

/**
 * █████▒█      ██  ▄████▄   ██ ▄█▀     ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒      ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░      ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄      ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄     ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒     ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 * Copyright 2021 Clover You.
 * <p>
 * 通用错误代码
 * - 10: 通用
 *   - 001：参数校验错误
 * - 11：商品
 * - 12：订单
 * - 13：购物车
 * - 14：物流
 * </p>
 * @author Clover You
 * @create 2021-11-25 08:47
 */
@Getter
public enum BizCodeEnum {
    /**
     * 参数校验错误
     */
    VALID_EXCEPTION(10001, "参数校验错误"),
    /**
     * 非法操作
     */
    ILLEGAL_OPERATION(10002, "非法操作数据"),
    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION(10000, "未知异常"),

    /**
     * 商品上架错误
     */
    PRODUCT_UP_EXCEPTION(11000, "商品上架错误");

    private final Integer code;

    private final String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
