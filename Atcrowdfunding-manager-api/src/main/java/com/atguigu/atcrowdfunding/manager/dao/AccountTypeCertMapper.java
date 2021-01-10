package com.atguigu.atcrowdfunding.manager.dao;/*
@author Mr.Ding
@date 2020/3/5 - 18:03
*/

import com.atguigu.atcrowdfunding.bean.AccountTypeCert;
import java.util.List;
import java.util.Map;

public interface AccountTypeCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountTypeCert record);

    AccountTypeCert selectByPrimaryKey(Integer id);

    List<AccountTypeCert> selectAll();

    int updateByPrimaryKey(AccountTypeCert record);

    List<Map<String, Object>> queryCertAccttype();

    int deleteAcctTypeCert(Map<String, Object> paramMap);

    int insertAcctTypeCert(Map<String, Object> paramMap);
}