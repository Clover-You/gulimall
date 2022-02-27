package top.ctong.gulimall.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ctong.gulimall.common.utils.R;

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
 * 会员模块远程调用
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-25 2:06 下午
 */
@FeignClient("gulimall-member")
public interface MemberFeignService {
    /**
     * 根据会员id远程查询会员所有地址
     * @param memberId 会员id
     * @return R
     * @author Clover You
     * @date 2022/2/25 2:31 下午
     */
    @GetMapping("/member/memberreceiveaddress/{memberId}/addresses")
    R getMemberReceiveAddress(@PathVariable("memberId") Long memberId);

    /**
     * 根据收货地址id查询地址信息
     * @param id 收货地址id
     * @return R
     * @author Clover You
     * @date 2022/2/27 10:26 上午
     */
    @RequestMapping("/member/memberreceiveaddress/info/{id}")
    R getAddressById(@PathVariable("id") Long id);
}
