package top.ctong.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.ctong.gulimall.common.utils.PageUtils;
import top.ctong.gulimall.ware.entity.PurchaseEntity;
import top.ctong.gulimall.ware.exception.HandlerExceptionReJSON;
import top.ctong.gulimall.ware.vo.MergeVo;

import java.util.List;
import java.util.Map;


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
 * 采购信息
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2021-11-16 16:12:37
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /** 
     * 查询未领取的采购单
     * @param params 分页数据
     * @return PageUtils
     * @author Clover You
     * @date 2021/12/12 20:39
     */
    PageUtils queryPageUnreceive(Map<String, Object> params);

    /**
     * 合并采购单
     * @param merge 采购单信息
     * @author Clover You
     * @date 2021/12/13 09:06
     */
    void mergePurchase(MergeVo merge) throws HandlerExceptionReJSON;

    /**
     * 领取采购单
     * @param purchaseIds 采购单id
     * @author Clover You
     * @date 2021/12/13 10:00
     */
    void received(List<Long> purchaseIds);
}

