package top.ctong.gulimall.common.constant;

import lombok.Data;

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
 * 库存枚举
 * </p>
 * @author Clover You
 * @create 2021-12-13 09:11
 */
public class WareConstant {
    public enum PurchaseStatusEnum {

        CREATED(0,"新建"),

        ASSIGNED(1,"已分配"),

        receive(2,"已领取"),

        FINISH(3,"已完成"),

        HASERROR(4,"有异常");

        private final int status;

        private final String msg;


        PurchaseStatusEnum(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }
    }

    public enum PurchaseDeyailStatusEnum {

        CREATED(0,"新建"),

        ASSIGNED(1,"已分配"),

        BUYING(2,"正在采购"),

        FINISH(3,"已完成"),

        HASERROR(4,"采购失败");

        private final int status;

        private final String msg;


        PurchaseDeyailStatusEnum(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }
    }
}
