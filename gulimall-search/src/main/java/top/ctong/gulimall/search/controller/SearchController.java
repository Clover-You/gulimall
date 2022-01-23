package top.ctong.gulimall.search.controller;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import top.ctong.gulimall.search.service.MallSearchService;
import top.ctong.gulimall.search.vo.SearchParam;
import top.ctong.gulimall.search.vo.SearchResult;

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
 * 搜索页前端控制器
 * </p>
 *
 * @author Clover You
 * @email 2621869236@qq.com
 * @create 2022-01-16 22:08
 */
@Controller
@Slf4j
public class SearchController {

    @Autowired
    private MallSearchService mallSearchService;

    /**
     * 检索页
     * @param param 检索参数
     * @author Clover You
     * @date 2022/1/17 00:56
     * @return String 检索页面
     */
    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {
        log.debug("come list page..");
        log.info("查询条件,{}",request.getQueryString());
        param.set_queryString(request.getQueryString());
        SearchResult result = mallSearchService.search(param);
        model.addAttribute("searchResult", result);
        return "list";
    }

}
