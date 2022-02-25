package top.ctong.gulimall.order.components.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.ctong.gulimall.common.constant.SessionKeyConstant;
import top.ctong.gulimall.common.vo.MemberRespVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * 登录拦截器
 * </p>
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-02-25 10:20 AM
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 但前用户信息
     */
    public final static ThreadLocal<MemberRespVo> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * Interception point before the execution of a handler. Called after
     * HandlerMapping determined an appropriate handler object, but before
     * HandlerAdapter invokes the handler.
     * <p>DispatcherServlet processes a handler in an execution chain, consisting
     * of any number of interceptors, with the handler itself at the end.
     * With this method, each interceptor can decide to abort the execution chain,
     * typically sending an HTTP error or writing a custom response.
     * <p><strong>Note:</strong> special considerations apply for asynchronous
     * request processing. For more details see
     * {@link AsyncHandlerInterceptor}.
     * <p>The default implementation returns {@code true}.
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return {@code true} if the execution chain should proceed with the
     * next interceptor or the handler itself. Else, DispatcherServlet assumes
     * that this interceptor has already dealt with the response itself.
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        log.warn("订单服务---登录拦截器触发");
        HttpSession session = request.getSession();
        MemberRespVo attribute = (MemberRespVo) session.getAttribute(SessionKeyConstant.LOGIN_USER);
        if (attribute == null) {
            // 去登录
            response.sendRedirect("http://auth.gulimall.com/login.html");
            log.warn("订单服务---登录拦截器拦截未登录用户");
            return false;
        }

        THREAD_LOCAL.set(attribute);
        log.warn("订单服务---已登录");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * Interception point after successful execution of a handler.
     * Called after HandlerAdapter actually invoked the handler, but before the
     * DispatcherServlet renders the view. Can expose additional model objects
     * to the view via the given ModelAndView.
     * <p>DispatcherServlet processes a handler in an execution chain, consisting
     * of any number of interceptors, with the handler itself at the end.
     * With this method, each interceptor can post-process an execution,
     * getting applied in inverse order of the execution chain.
     * <p><strong>Note:</strong> special considerations apply for asynchronous
     * request processing. For more details see
     * {@link AsyncHandlerInterceptor}.
     * <p>The default implementation is empty.
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler the handler (or {@link HandlerMethod}) that started asynchronous
     * execution, for type and/or instance examination
     * @param modelAndView the {@code ModelAndView} that the handler returned
     * (can also be {@code null})
     * @throws Exception in case of errors
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MemberRespVo respVo = THREAD_LOCAL.get();
        if (respVo != null) {
            // 移除ThreadLocal数据，避免内存泄露
            THREAD_LOCAL.remove();
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
