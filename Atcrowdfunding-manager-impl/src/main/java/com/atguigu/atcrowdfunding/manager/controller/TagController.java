package com.atguigu.atcrowdfunding.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dplStart
 * @create 上午 10:18
 * @Description
 */
@Controller
@RequestMapping("/tag")
public class TagController {

    @RequestMapping("/index")
    public String index() {
        return "tag/index";
    }

}
