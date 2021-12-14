package top.ctong.gulimall.ware.vo;

import lombok.Data;
import top.ctong.gulimall.common.valid.group.AggregationGroup;

import javax.validation.constraints.NotNull;
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
 * 完成采购单vo
 * </p>
 * @author Clover You
 * @create 2021-12-13 11:09
 */
@Data
public class PurchaseDoneVo implements Serializable {

    private static final long serialVersionUID = -2924315023411348426L;

    /**
     * 采购单id
     */
    @NotNull(message = "采购单id不能为空", groups = AggregationGroup.class)
    private Long id;

    /**
     * 完成/失败的需求详情
     */
    @NotNull(message = "采购单详情不能为空", groups = AggregationGroup.class)
    private List<PurchaseItemDoneVo> items;
}
