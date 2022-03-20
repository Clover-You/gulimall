package top.ctong.gulimall.member.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import top.ctong.gulimall.common.exception.BizCodeEnum;
import top.ctong.gulimall.common.utils.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
 * 熔断、限流、降级配置
 * </p>
 * @author Clover You
 * @email cloveryou02@163.com
 * @create 2022-03-20 9:15 上午
 */
@Configuration
public class SentinelConfig {

    /**
     * 统一降级数据
     * @author Clover You
     * @email cloveryou02@163.com
     * @date 2022/3/20 9:18 上午
     */
    @ConditionalOnMissingBean({BlockExceptionHandler.class, CustomBlockException.class})
    @Configuration
    public static class CustomBlockException implements BlockExceptionHandler {
        @Override
        public void handle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           BlockException e) throws Exception {

            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpServletResponse.getWriter();
            R error = R.error(BizCodeEnum.TO_MANY_REQUEST);
            writer.println(JSON.toJSONString(error));
        }
    }

}
