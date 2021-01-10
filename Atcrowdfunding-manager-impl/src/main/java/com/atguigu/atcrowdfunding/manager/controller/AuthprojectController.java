package com.atguigu.atcrowdfunding.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dplStart
 * @create 上午 10:40
 * @Description
 */
@Controller
@RequestMapping("/authproject")
public class AuthprojectController {
    @RequestMapping("/index")
    public String index() {
        return "authproject/index";
    }


}
