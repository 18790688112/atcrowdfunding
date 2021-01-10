package com.atguigu.atcrowdfunding.potal.service.impl;

import com.atguigu.atcrowdfunding.bean.Return;
import com.atguigu.atcrowdfunding.manager.dao.ReturnMapper;
import com.atguigu.atcrowdfunding.potal.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dplStart
 * @create 下午 09:46
 * @Description
 */
@Service
public class ReturnServiceImpl implements ReturnService {

    @Autowired
    ReturnMapper returnMapper;

    @Override
    public void saveReturnInfo(Return return1) {
        returnMapper.insert(return1);
    }
}
