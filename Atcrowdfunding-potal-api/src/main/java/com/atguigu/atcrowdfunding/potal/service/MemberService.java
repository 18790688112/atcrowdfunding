package com.atguigu.atcrowdfunding.potal.service;/*
@author Mr.Ding
@date 2020/3/5 - 17:57
*/

import com.atguigu.atcrowdfunding.bean.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {

    Member queryMebmerlogin(Map<String, Object> paramMap);

    void updateAcctType(Member loginMember);

    void updateBasicinfo(Member loginMember);

    void updateEmail(Member loginMember);

    void updateAuthstatus(Member loginMember);

    Member getMemberById(Integer memberid);

    List<Map<String, Object>> queryCertByMemberid(Integer memberid);

    int insertMember(Member member);

    int regMember(Member member);
    List<Member> queryAllMember();
}
