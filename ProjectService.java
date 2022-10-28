package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.ProjectEntityMapper;
import com.aim.questionnaire.dao.QuestionnaireEntityMapper;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import com.aim.questionnaire.dao.entity.QuestionnaireEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by wln on 2018\8\6 0006.
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectEntityMapper projectEntityMapper;
    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;
    @Autowired
    private UserEntityMapper userEntityMapper;


    /**
     * 添加项目
     * @param projectEntity
     * @return
     */
    @Transactional
    public int addProjectInfo(ProjectEntity projectEntity,String user) {
        //新增
    	String userid = userEntityMapper.selectIdByName(user);
    	projectEntity.setUserId(userid);
        projectEntity.setId(UUIDUtil.getOneUUID());
        projectEntity.setCreationDate(new Date());
        projectEntity.setLastUpdateDate(new Date());
        projectEntityMapper.insertSelective(projectEntity);
        return 0;
    }

    /**
     * 修改项目
     * @param projectEntity
     * @return
     */
    @Transactional
    public int modifyProjectInfo(ProjectEntity projectEntity,String user) {
        projectEntityMapper.updateByPrimaryKeySelective(projectEntity);
        return 0;//重点啊 对项目的修改（modify  修改）
    }

    /**
     * 删除项目
     * @param projectEntity
     * @return
     */
    public int deleteProjectById(ProjectEntity projectEntity) {
    	projectEntityMapper.deleteProjectById(projectEntity);
    	//QuestionnaireEntity questionnaireEntity=new QuestionnaireEntity();
    	//projectEntityMapper.deleteProjectById(projectEntity.getId());
        return 0;
    }

    /**
     * 查询项目列表 add
     * @param projectEntity
     * @return
     */
    public  List<Map<String,Object>>  queryProjectList(ProjectEntity projectEntity) {
        List<Map<String,Object>>   resultList = new ArrayList<Map<String,Object>>();
        resultList = projectEntityMapper.queryProjectList(projectEntity);
        return resultList;
    }
    
    /**
     * 查询项目列表 add
     * @param projectEntity
     * @return
     */
    public  List<Map<String,Object>>  queryProjectListById(ProjectEntity projectEntity) {
        List<Map<String,Object>>   resultList = new ArrayList<Map<String,Object>>();
        resultList = projectEntityMapper.queryProjectListById(projectEntity);
        return resultList;
    }

    /**
     * 查询全部项目的名字接口
     * @return
     */
    public List<Map<String,Object>> queryAllProjectName() {
        return null;
    }
}
