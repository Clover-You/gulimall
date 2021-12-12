package top.ctong.gulimall.product.vo;

import lombok.Data;
import top.ctong.gulimall.product.entity.AttrEntity;

import java.io.Serializable;
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
 * 属性与属性分组vo
 * </p>
 * @author Clover You
 * @create 2021-12-07 10:28
 */
@Data
public class AttrGroupWithAttrsVo implements Serializable {
//"attrGroupId": 1,
//        "attrGroupName": "主体",
//        "sort": 0,
//        "descript": "主体",
//        "icon": "dd",
//        "catelogId": 225,
//        "attrs": [{
//        "attrId": 7,
//                "attrName": "入网型号",
//                "searchType": 1,
//                "valueType": 0,
//                "icon": "xxx",
//                "valueSelect": "aaa;bb",
//                "attrType": 1,
//                "enable": 1,
//                "catelogId": 225,
//                "showDesc": 1,
//                "attrGroupId": null
//    }, {
//        "attrId": 8,
//                "attrName": "上市年份",
//                "searchType": 0,
//                "valueType": 0,
//                "icon": "xxx",
//                "valueSelect": "2018;2019",
//                "attrType": 1,
//                "enable": 1,
//                "catelogId": 225,
//                "showDesc": 0,
//                "attrGroupId": null
//    }]
//},
    private static final long serialVersionUID = 5673554620100923551L;

    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}
