package com.atguigu.atcrowdfunding.potal.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atguigu.atcrowdfunding.bean.*;
import com.atguigu.atcrowdfunding.potal.service.ProjectService;
import com.atguigu.atcrowdfunding.potal.service.ReturnService;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.potal.listener.PassListener;
import com.atguigu.atcrowdfunding.potal.listener.RefuseListener;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.vo.Data;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CertService certService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @RequestMapping("/accttype")
    public String accttype() {
        return "member/accttype";
    }

    @RequestMapping("/basicinfo")
    public String basicinfo() {
        return "member/basicinfo";
    }

    @RequestMapping("/uploadCert")
    public String uploadCert() {
        return "member/uploadCert";
    }

    @RequestMapping("/checkemail")
    public String checkemail() {
        return "member/checkemail";
    }

    @RequestMapping("/checkauthcode")
    public String checkauthcode() {
        return "member/checkauthcode";
    }


    @RequestMapping("/apply")
    public String apply(HttpSession session) {

        Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);

        Ticket ticket = ticketService.getTicketByMemberId(member.getId());

        if (ticket == null) {
            ticket = new Ticket(); //封装数据
            ticket.setMemberid(member.getId());
            ticket.setPstep("apply");
            ticket.setStatus("0");

            ticketService.saveTicket(ticket);

        } else {
            String pstep = ticket.getPstep();

            if ("accttype".equals(pstep)) {

                return "redirect:/member/basicinfo.htm";
            } else if ("basicinfo".equals(pstep)) {

                //根据当前用户查询账户类型,然后根据账户类型查找需要上传的资质
                List<Cert> queryCertByAccttype = certService.queryCertByAccttype(member.getAccttype());
                session.setAttribute("queryCertByAccttype", queryCertByAccttype);


                return "redirect:/member/uploadCert.htm";
            } else if ("uploadcert".equals(pstep)) {

                return "redirect:/member/checkemail.htm";
            } else if ("checkemail".equals(pstep)) {

                return "redirect:/member/checkauthcode.htm";
            }


        }

        return "member/accttype";
    }


    @ResponseBody
    @RequestMapping("/finishApply")
    public Object finishApply(HttpSession session, String authcode) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);


            //让当前系统用户完成:验证码审核任务.
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
//			if(ticket.getAuthcode().equals(authcode)){
            if (ticket.getPstep().equals("checkemail")) {
                //完成审核验证码任务
//				Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid()).taskAssignee(loginMember.getLoginacct()).singleResult();
//				taskService.complete(task.getId());

                //更新用户申请状态
                loginMember.setAuthstatus("1");
                memberService.updateAuthstatus(loginMember);


                //记录流程步骤:
                ticket.setPstep("finishapply");
                ticketService.updatePstep(ticket);
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("验证码不正确,请重新输入!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;

    }


    @ResponseBody
    @RequestMapping("/startProcess")
    public Object startProcess(HttpSession session, String email) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);

            // 如果用户输入新的邮箱,将旧的邮箱地址替换
            if (!loginMember.getEmail().equals(email)) {
                loginMember.setEmail(email);
                memberService.updateEmail(loginMember);
            }

            StringBuilder authcode = new StringBuilder();
            for (int i = 1; i <= 4; i++) {
                authcode.append(new Random().nextInt(10));
            }

            Map<String, Object> variables = new HashMap<String, Object>();

            variables.put("authcode", authcode);

            //记录流程步骤:
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("checkemail");
            ticket.setPiid("2");
            variables.get("authcode");
            ticket.setAuthcode("1234");

            ticketService.updatePiidAndPstep(ticket);

            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;

    }


    @ResponseBody
    @RequestMapping("/doUploadCert")
    public Object doUploadCert(HttpSession session, Data ds) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);

            String realPath = session.getServletContext().getRealPath("/pics");

            List<MemberCert> certimgs = ds.getCertimgs();
            for (MemberCert memberCert : certimgs) {

                MultipartFile fileImg = memberCert.getFileImg();
                String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
                String tmpName = UUID.randomUUID().toString() + extName;
                String filename = realPath + "/cert" + "/" + tmpName;
                System.out.println(filename);

                fileImg.transferTo(new File(filename));    //资质文件上传.

                //准备数据
                memberCert.setIconpath(tmpName); //封装数据,保存数据库
                memberCert.setMemberid(loginMember.getId());
            }

            // 保存会员与资质关系数据.
            certService.saveMemberCert(certimgs);

            //记录流程步骤:
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("uploadcert");
            ticketService.updatePstep(ticket);

            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;

    }

    @ResponseBody
    @RequestMapping("/updateBasicinfo")
    public Object updateBasicinfo(HttpSession session, Member member) {
        AjaxResult result = new AjaxResult();
        try {
            // 获取登录会员信息
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            loginMember.setRealname(member.getRealname());
            loginMember.setCardnum(member.getCardnum());
            loginMember.setTel(member.getTel());
            // 更新账户类型
            memberService.updateBasicinfo(loginMember);
            //记录流程步骤:
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("basicinfo");
            ticketService.updatePstep(ticket);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 更新账户类型
     */
    @ResponseBody
    @RequestMapping("/updateAcctType")
    public Object updateAcctType(HttpSession session, String accttype) {
        AjaxResult result = new AjaxResult();

        try {

            // 获取登录会员信息
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            loginMember.setAccttype(accttype);
            // 更新账户类型
            memberService.updateAcctType(loginMember);


            //记录流程步骤:
            Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
            ticket.setPstep("accttype");
            ticketService.updatePstep(ticket);


            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;

    }


    @Autowired
    ProjectService projectService;


    @RequestMapping("/minecrowdfunding")
    public String minecrowdfunding(HttpSession session) {
        return "member/minecrowdfunding";
    }

    @RequestMapping("/start")
    public String start(HttpSession session) {
        return "member/start";
    }

    @RequestMapping("/start-step-1")
    public String startstep1(HttpSession session) {
        return "member/start-step-1";
    }

    @RequestMapping("/start-step-2")
    public String startstep2(HttpSession session) {
        return "member/start-step-2";
    }

    @RequestMapping("/start-step-3")
    public String startstep3(HttpSession session) {
        return "member/start-step-3";
    }

    @RequestMapping("/start-step-4")
    public String startstep4(HttpSession session) {
        return "member/start-step-4";
    }

    @ResponseBody
    @RequestMapping("/saveProject")
    public Object saveProject(String name, String remark, Long money,Integer day ){
        AjaxResult result = new AjaxResult();

        try {
            projectService.saveProjectInfo(new Project(name,remark,money,day));
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }

    @Autowired
    ReturnService returnService;

    @ResponseBody
    @RequestMapping("/saveReturn")
    public Object saveReturn(Return return1){
        AjaxResult result = new AjaxResult();

        try {
            returnService.saveReturnInfo(return1);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }

        return result;
    }



}
