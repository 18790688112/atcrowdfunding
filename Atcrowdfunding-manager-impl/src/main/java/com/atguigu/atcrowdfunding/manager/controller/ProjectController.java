package com.atguigu.atcrowdfunding.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dplStart
 * @create 上午 10:03
 * @Description
 */
@Controller
@RequestMapping("/projectType")
public class ProjectController {

    @RequestMapping("/index")
    public String index() {
        return "projectType/index";
    }


}
