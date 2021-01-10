package com.atguigu.atcrowdfunding.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dplStart
 * @create 上午 10:28
 * @Description
 */
@Controller
@RequestMapping("/param")
public class ParamController {

    @RequestMapping("/index")
    public String index() {
        return "param/index";
    }

}
