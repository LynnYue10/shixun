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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    	int pagenum = (int)map.get("pageNum");
    	int pageSize = (int) map.get("pageSize");
    	PageHelper.startPage(pagenum, pageSize);
    	List<Map<String,Object>>  resultList = answerEntityMapper.queryQuestionnaireByProjectId(map);
        PageInfo<Map<String, Object>> page = new PageInfo<>(resultList);
        return page;
    }
    
    public  PageInfo  queryAnswerByGroupname(Map<String, Object> map) {
    	int pagenum = (int)map.get("pageNum");
    	int pageSize = (int) map.get("pageSize");
    	PageHelper.startPage(pagenum, pageSize);
    	List<Map<String,Object>>  resultList = answerEntityMapper.queryAnswerByGroupname(map);
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
     * 查询所有答者列表（发送时） 
     * @param map
     * @return
     */
    public PageInfo queryAllAnswerList(Map<String,Object> map) {
    	int pagenum = (int)map.get("pageNum");
    	int pageSize = (int)map.get("pageSize");
    	String answername = (String) map.get("answerName");
    	PageHelper.startPage(pagenum,pageSize); 
		List<Map<String,Object>> resultList = answerEntityMapper.queryAllAnswerList(map);
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
    
//  /**
//  * 创建用户的基本信息
//  * @param map
//  * @return
//  */
// public int addUserInfo(Map<String,Object> map) {
//     if(map.get("username") != null) {
//         int userResult = userEntityMapper.queryExistUser(map);
//         if(userResult != 0) {
//             //用户名已经存在
//             return 3;
//         }
//     }
//
//     String id = UUIDUtil.getOneUUID();
//     map.put("id",id);
//     //创建时间
//     Date date = DateUtil.getCreateTime();
//     map.put("creationDate",date);
//     map.put("lastUpdateDate",date);
//     //创建人
//     String user = "admin";
//     map.put("createdBy",user);
//     map.put("lastUpdatedBy",user);
//     //前台传入的时间戳转换
//     String startTimeStr = map.get("startTime").toString();
//     String endTimeStr = map.get("stopTime").toString();
//     Date startTime = DateUtil.getMyTime(startTimeStr);
//     Date endTime = DateUtil.getMyTime(endTimeStr);
//     map.put("startTime",startTime);
//     map.put("stopTime",endTime);
//
//     int result = userEntityMapper.addUserInfo(map);
//     return result;
// }
//
// /**
//  * 编辑用户的基本信息
//  * @param map
//  * @return
//  */
// public int modifyUserInfo(Map<String, Object> map) {
//
//     return 0;
// }
//
// /**
//  * 修改用户状态
//  * @param map
//  * @return
//  */
// public int modifyUserStatus(Map<String, Object> map) {
//     return 0;
// }
//
// /**
//  * 根据id查询用户信息
//  * @param userEntity
//  * @return
//  */
// public Map<String,Object> selectUserInfoById(UserEntity userEntity) {
//
//     return null;
// }
//
// /**
//  * 删除用户信息
//  * @param userEntity
//  * @return
//  */
// public int deteleUserInfoById(UserEntity userEntity) {
//
//     return 0;
// }
// 
// 
    
    
}
