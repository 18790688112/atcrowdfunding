package com.atguigu.atcrowdfunding.potal.service.impl;

import com.atguigu.atcrowdfunding.bean.Project;
import com.atguigu.atcrowdfunding.manager.dao.ProjectMapper;
import com.atguigu.atcrowdfunding.potal.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dplStart
 * @create 下午 08:55
 * @Description
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public void saveProjectInfo(Project project) {
        projectMapper.insertProject(project);
    }
}
