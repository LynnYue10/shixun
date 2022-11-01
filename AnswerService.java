package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.AnswerEntityMapper;
import com.aim.questionnaire.dao.GroupEntityMapper;
import com.aim.questionnaire.dao.ProjectEntityMapper;
import com.aim.questionnaire.dao.QuestionnaireEntityMapper;
import com.aim.questionnaire.dao.QuestionnaireMapper;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.dao.entity.AnswerEntity;
import com.aim.questionnaire.dao.entity.GroupEntity;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import com.aim.questionnaire.dao.entity.QuestionnaireEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AnswerService {

	@Autowired
    private AnswerEntityMapper answerEntityMapper;
    @Autowired
    private GroupEntityMapper groupEntityMapper;
    @Autowired
    private QuestionnaireEntityMapper questionnaireEntityMapper;
    
    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    
    /**
     * 查询用户列表（模糊搜索）
     * @param map
     * @return
     */
    public PageInfo queryUserList(Map<String,Object> map) {
    	int pagenum = (int)map.get("pageNum");
    	int pageSize = (int)map.get("pageSize");
    	PageHelper.startPage(pagenum,pageSize);
		String name = (String) map.get("userName");
		String groupname=answerEntityMapper.queryUserList(name);
        List<Map<String,Object>> list = questionnaireMapper.queryUserList(groupname);
        PageInfo<Map<String,Object>> page = new PageInfo<>(list);
        return page;
    }
    
    public  PageInfo  queryQuestionnaireByProjectId(Map<String, Object> map) {
    	System.out.println("2");
    	int pagenum = (int)map.get("pageNum");
    	int pageSize = (int) map.get("pageSize");
    	PageHelper.startPage(pagenum, pageSize);
    	List<Map<String,Object>>  resultList = answerEntityMapper.queryQuestionnaireByProjectId(map);
    	System.out.println("！！"+resultList);
        PageInfo<Map<String, Object>> page = new PageInfo<>(resultList);
        return page;
    }
    
    /**
     * 查询群组 
     * @param groupEntity
     * @return
     */
    public  List<Map<String,Object>>  queryGroups() {
        List<Map<String,Object>>   resultList = new ArrayList<Map<String,Object>>();
        resultList = groupEntityMapper.queryGroups();
        return resultList;
    }

    /**
     * 查询答者列表 
     * @param map
     * @return
     */
    public PageInfo queryAnswerList(Map<String,Object> map) {
    	int pagenum = (int)map.get("pageNum");
    	int pageSize = (int)map.get("pageSize");
    	String answername = (String) map.get("answerName");
    	PageHelper.startPage(pagenum,pageSize); 
    	String username = "admin";
		List<Map<String,Object>> resultList = answerEntityMapper.queryAnswerList(username,answername);
		PageInfo<Map<String,Object>> page = new PageInfo<>(resultList);
        return page;   
	}
    

    /**
     * 删除答者
     * @param id
     * @return
     */
    public void deleteAnswerById(String id) {
    	answerEntityMapper.deleteAnswerById(id);
		
	}

	
    /**
     * 添加答者
     * @param answerEntity
     * @return
     */
    @Transactional
    public void addAnswer(AnswerEntity answerEntity) {
    	
    	answerEntity.setId(UUIDUtil.getOneUUID());
    	answerEntity.setGroupname("未分组");
    	answerEntity.setIsdelete("0");
    	answerEntityMapper.addAnswer(answerEntity);

	}

    /**
     * 查询是否有这个答者名 
     * @param answerEntity
     * @return
     */
    public List<Map<String, Object>> ifhasanswer(String answername) {
    	List<Map<String,Object>>   resultList = new ArrayList<Map<String,Object>>();
        resultList = answerEntityMapper.ifhasanswer(answername);
        return resultList;
	}

    /**
     * 根据id找答者 
     * @param id
     * @return
     */
	public int findAnswerById(String id) {
		int ifcan;
		AnswerEntity answer = new AnswerEntity();
		answer = answerEntityMapper.findAnswerById(id);
        if(answer.getGroupname().equals("未分组")) {
        	ifcan = 1;
        }else {
        	ifcan = 0;
        }
        return ifcan;
	}

	/**
     * 修改
     * @param map
     * @return
     */
	public void modifyAnswer(Map<String, Object> map) {
		AnswerEntity answerEntity = new AnswerEntity();
		answerEntity.setId((String) map.get("id"));
		answerEntity.setAnswername((String) map.get("answername"));
		System.out.println("6892658486"+map.get("groupname"));
		answerEntity.setGroupname((String)map.get("groupname"));
		answerEntityMapper.modifyAnswer(answerEntity);		
	}
    
	/**
     * 查询删除的答者列表 
     * @param map
     * @return
     */
    public PageInfo queryDeletedAnswerList(Map<String,Object> map) {
    	int pagenum = (int)map.get("pageNum");
    	int pageSize = (int)map.get("pageSize");
    	String answername = (String) map.get("answerName");
    	PageHelper.startPage(pagenum,pageSize); 
    	String username = "admin";
		List<Map<String,Object>> resultList = answerEntityMapper.queryDeletedAnswerList(username,answername);
		PageInfo<Map<String,Object>> page = new PageInfo<>(resultList);
        return page;   
	}
	
	/**
     * 彻底删除答者
     * @param id
     * @return
     */
    public void finnaldeleteAnswerById(String id) {
    	answerEntityMapper.finnaldeleteAnswerById(id);		
	}
    
    /**
     * 恢复答者
     * @param id
     * @return
     */
    public void findbackAnswerById(String id) {
    	answerEntityMapper.findbackAnswerById(id);		
	}
    
    
    
    
}
