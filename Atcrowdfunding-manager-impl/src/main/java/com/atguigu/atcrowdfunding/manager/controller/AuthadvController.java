package com.atguigu.atcrowdfunding.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dplStart
 * @create 上午 10:42
 * @Description
 */
@Controller
@RequestMapping("/authadv")
public class AuthadvController {

    @RequestMapping("/index")
    public String index() {
        return "authadv/index";
    }


}
