package top.ctong.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
 * 2级分类vo
 * </p>
 * @author Clover You
 * @create 2021-12-26 14:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalog2Vo {

    /**
     * 一级分类id
     */
    private String catalog1Id;

    /**
     * 三级子分类
     */
    private List<Catalog3Vo> catalog3List;

    /**
     * 分类id
     */
    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 三级分类
     * @author Clover You
     * @date 2021/12/26 14:44
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Catalog3Vo {

        /**
         * 二级分类id
         */
        private String catalog2Id;

        /**
         * 分类id
         */
        private String id;

        /**
         * 分类名
         */
        private String name;

    }
}
