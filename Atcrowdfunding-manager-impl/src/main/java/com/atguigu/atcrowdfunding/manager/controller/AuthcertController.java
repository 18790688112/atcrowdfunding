package com.atguigu.atcrowdfunding.manager.controller;/*
@author Mr.Ding
@date 2020/3/5 - 18:00
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.jfree.util.StackableRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

@Controller
@RequestMapping("/authcert")
public class AuthcertController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index() {
        return "authcert/index";
    }


    @ResponseBody
    @RequestMapping("/pass")
    public Object pass(Integer memberid) {
        AjaxResult result = new AjaxResult();

        try {
            Member member = new Member();
            member.setId(memberid);
            member.setAuthstatus("2");
            memberService.updateAuthstatus(member);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return "result";
    }

    @ResponseBody
    @RequestMapping("/refuse")
    public Object refuse(String taskid, Integer memberid) {
        AjaxResult result = new AjaxResult();

        try {
            Member member = new Member();
            member.setId(memberid);
            member.setAuthstatus("0");
            memberService.updateAuthstatus(member);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }


    @RequestMapping("/show")
    public String show(HttpServletRequest request, Integer memberid, Map<String, Object> map) {
        Member memberById = memberService.getMemberById(memberid);

        List<Map<String, Object>> list = memberService.queryCertByMemberid(memberid);
//        System.out.println(list);


        map.put("member", memberById);
        map.put("certimgs", list);

        return "authcert/show";
    }


    @ResponseBody
    @RequestMapping("/pageQuery")
    public Object pageQuery() {

        List<Member> members = memberService.queryAllMember();

        return members;
    }
}
