package top.ctong.gulimall.order.enume;

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
 * Copyright 2022 Clover You.
 * <p>
 * 订单状态
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-27 10:14 上午
 */
public enum OrderStatusEnum {
    /**
     * 待付款
     */
    CREATE_NEW(0, "待付款"),

    /**
     * 已付款
     */
    PAYED(1, "已付款"),

    /**
     * 已发货
     */
    SENSED(2, "已发货"),

    /**
     * 已完成
     */
    RECEIVED(3, "已完成"),

    /**
     * 已取消
     */
    CANCELED(4, "已取消"),

    /**
     * 售后中
     */
    SERVICING(5, "售后中"),

    /**
     * 售后完成
     */
    SERVICED(6, "售后完成");

    /**
     * 状态吗
     */
    private final Integer code;

    /**
     * 信息
     */
    private final String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
