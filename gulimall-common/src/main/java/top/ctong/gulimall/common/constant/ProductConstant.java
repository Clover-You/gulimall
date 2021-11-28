package top.ctong.gulimall.common.constant;

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
 * 商品系统常量
 * </p>
 * @author Clover You
 * @create 2021-11-28 11:32
 */
public class ProductConstant {

    /**
     * 属性枚举
     * @author Clover You
     * @date 2021/11/28 11:36
     */
    @Getter
    public enum AttrEnum {
        BASE(1, "基本属性"),
        SALE(0, "销售属性");

        /**
         * code
         */
        private final int code;

        /**
         * 注释
         */
        private final String msg;

        /**
         * s
         */
        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
