package top.ctong.gulimall.product.exception;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.ctong.gulimall.common.exception.BizCodeEnum;
import top.ctong.gulimall.common.utils.R;

import java.util.HashMap;
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
 * 全局异常处理
 * </p>
 * @author Clover You
 * @create 2021-11-25 08:14
 */
@RestControllerAdvice(basePackages = "top.ctong.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {

    /**
     * 处理@Valid验证异常
     * @author Clover You
     * @date 2021/11/25 08:59
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public R handleBindingResultException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        Map<String, Map<String, Object>> map = new HashMap<>(result.getFieldErrorCount());
        result.getFieldErrors().forEach(f -> {
            String field = f.getField();
            Object value = f.getRejectedValue();

            Map<String, Object> childMap = new HashMap<>(2);
            childMap.put("value", value);
            childMap.put("msg", f.getDefaultMessage());

            map.put(field, childMap);
        });
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg())
                .put("data", map);
    }

    /**
     * 处理未处理的异常
     * @author Clover You
     * @date 2021/11/25 08:59
     */
    @ExceptionHandler(Throwable.class)
    public R handleUnhandledException(Throwable e) {
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}
