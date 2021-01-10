package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.manager.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dplStart
 * @create 上午 09:33
 * @Description
 */
@Controller
@RequestMapping("/message")
public class MessageController {



    @RequestMapping("/index")
    public String index() {
        return "message/index";
    }


}
