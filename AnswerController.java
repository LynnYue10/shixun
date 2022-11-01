package com.aim.questionnaire.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.dao.AnswerEntityMapper;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.dao.entity.AnswerEntity;
import com.aim.questionnaire.dao.entity.GroupEntity;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import com.aim.questionnaire.dao.entity.UserEntity;
import com.aim.questionnaire.service.AnswerService;
import com.aim.questionnaire.service.UserService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/answer")
public class AnswerController {
	
	private final Logger logger = LoggerFactory.getLogger(AnswerController.class);
    
    @Autowired
    private AnswerService answerService;

    //@Autowired
    //private AnswerEntityMapper answerEntityMapper;
    
    /**
	 * 查询用户列表（模糊搜索）
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryUserList", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity queryUserList(@RequestBody Map<String, Object> map) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		PageInfo pageinfo = answerService.queryUserList(map);
		httpResponseEntity.setData(pageinfo);
		httpResponseEntity.setCode(Constans.SUCCESS_CODE);
		return httpResponseEntity;

	}
	
    /**
     * 查看项目下问卷 
     * @param GroupEntity
     * @return
     */
    @RequestMapping(value = "/queryAnswerByGroupname",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryQuestionnaireByProjectId(@RequestBody Map<String, Object> map) {
    	System.out.println(map);
    	System.out.println("1");
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        PageInfo list = answerService.queryQuestionnaireByProjectId(map);
        System.out.println(list);
        httpResponseEntity.setData(list);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        return httpResponseEntity;
    }
    
    
    /**
     * 查询全部群组名称
     * @param GroupEntity
     * @return
     */
    @RequestMapping(value = "/queryGroups",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryGroups() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        List<Map<String, Object>> maps = answerService.queryGroups();
        System.out.println("4132413"+maps);
        httpResponseEntity.setData(maps);
        httpResponseEntity.setCode("666");
        return httpResponseEntity;
    }
    
    /**
     * 查询全部答者的信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryAnswerList",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryAnswerList(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        PageInfo pageinfo = answerService.queryAnswerList(map);
        httpResponseEntity.setData(pageinfo); 
        
        httpResponseEntity.setCode("666");
        return httpResponseEntity;
    }
    
    
    
    
    
    /**
     * 根据id删除答者
     * @param answerEntity
     * @return
     */
    @RequestMapping(value = "/deleteAnswerById",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteAnswerById(@RequestBody Map<String,Object> map) {
    	String id = (String) map.get("id");
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();     
        int ifcan = answerService.findAnswerById(id);
        if(ifcan==1) {
        	answerService.deleteAnswerById(id);
        	httpResponseEntity.setCode("666");
            httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
        }else {
            httpResponseEntity.setCode(Constans.GROUP_EXIST__CODE);
            httpResponseEntity.setMessage(Constans.GROUP_EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
    
    
    /**
     * 添加答者
     * @param answerEntity
     * @return
     */
    @RequestMapping(value = "/addAnswer",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addAnswer(@RequestBody AnswerEntity answerEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String answername = answerEntity.getAnswername();
        System.out.println("###");
        System.out.println(answername);
        List<Map<String, Object>> maps = answerService.ifhasanswer(answername);
        System.out.println("%%%");
        System.out.println(maps);
        if(!CollectionUtils.isEmpty(maps)){
            httpResponseEntity.setCode("111");
        }else {
            /**
             * 新增答者到mysql
             * 
             */        	
        	answerService.addAnswer(answerEntity);
        	httpResponseEntity.setCode("666");
        }
        return httpResponseEntity;
    }
	
    /**
     * 根据项目id修改答者（如何修改）
     * @param answerEntity
     * @return
     */
    @RequestMapping(value = "/modifyAnswer",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyAnswer(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        //判断
        String id = (String) map.get("id");    
        int ifcan = answerService.findAnswerById(id);
        if(ifcan==1) {
        	answerService.modifyAnswer(map);
        	httpResponseEntity.setCode("666");
            httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
        }else {
            httpResponseEntity.setCode(Constans.GROUP_EXIST__CODE);
            httpResponseEntity.setMessage(Constans.GROUP_EXIST_CANOTUP_MESSAGE);
        }
        return httpResponseEntity;
    }
    
    /**
     * 查询全部答者的信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryDeletedAnswerList",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryDeletedAnswerList(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        PageInfo pageinfo = answerService.queryDeletedAnswerList(map);
        httpResponseEntity.setData(pageinfo); 
        
        httpResponseEntity.setCode("666");
        return httpResponseEntity;
    }
    
    /**
     * 根据id彻底删除答者
     * @param answerEntity
     * @return
     */
    @RequestMapping(value = "/finnaldeleteAnswerById",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity finnaldeleteAnswerById(@RequestBody Map<String,Object> map) {
    	String id = (String) map.get("id");
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();     
        answerService.finnaldeleteAnswerById(id);
        httpResponseEntity.setCode("666");
        httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
        return httpResponseEntity;
    }
    
    
    /**
     * 根据id恢复答者
     * @param answerEntity
     * @return
     */
    @RequestMapping(value = "/findbackAnswerById",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity findbackAnswerById(@RequestBody Map<String,Object> map) {
    	String id = (String) map.get("id");
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();     
        answerService.findbackAnswerById(id);
        httpResponseEntity.setCode("666");
        httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
        return httpResponseEntity;
    }
    
    /* 
	*//**
		 * 用户登录
		 * 
		 * @param userEntity
		 * @return
		 *//*
			 * @RequestMapping(value="/userLogin",method= RequestMethod.POST, headers =
			 * "Accept=application/json") public HttpResponseEntity userLogin(@RequestBody
			 * UserEntity userEntity) { HttpResponseEntity httpResponseEntity = new
			 * HttpResponseEntity(); try {
			 * 
			 * List<UserEntity> hasUser = userEntityMapper.selectUserInfo(userEntity);
			 * if(CollectionUtils.isEmpty(hasUser) ) {
			 * httpResponseEntity.setCode(Constans.EXIST_CODE);
			 * httpResponseEntity.setData(null);
			 * httpResponseEntity.setMessage(Constans.LOGIN_USERNAME_PASSWORD_MESSAGE);
			 * }else { httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			 * httpResponseEntity.setData(userEntity);
			 * httpResponseEntity.setMessage(Constans.LOGIN_USERNAME_PASSWORD_MESSAGE); } }
			 * catch (Exception e) { logger.info("userLogin 用户登录>>>>>>>>>>>" +
			 * e.getLocalizedMessage()); httpResponseEntity.setCode(Constans.EXIST_CODE);
			 * httpResponseEntity.setMessage(Constans.EXIST_MESSAGE); } return
			 * httpResponseEntity; }
			 */
//	/**
//	 * 创建用户的基本信息
//	 * 
//	 * @param map
//	 * @return
//	 */
//	@RequestMapping(value = "/addUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
//	public HttpResponseEntity addUserInfo(@RequestBody Map<String, Object> map) {
//		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//		try {
//			int result = userService.addUserInfo(map);
//			if (result == 3) {
//				httpResponseEntity.setCode(Constans.USER_USERNAME_CODE);
//				httpResponseEntity.setMessage(Constans.USER_USERNAME_MESSAGE);
//			} else {
//				httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//				httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
//			}
//		} catch (Exception e) {
//			logger.info("addUserInfo 创建用户的基本信息>>>>>>>>>>>" + e.getLocalizedMessage());
//			httpResponseEntity.setCode(Constans.EXIST_CODE);
//			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
//		}
//		return httpResponseEntity;
//	}
//
//	/**
//	 * 编辑用户的基本信息
//	 * 
//	 * @param map
//	 * @return
//	 */
//	@RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
//	public HttpResponseEntity modifyUserInfo(@RequestBody Map<String, Object> map) {
//		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//
//		return httpResponseEntity;
//	}
//
//	/**
//	 * 根据用户id查询用户基本信息
//	 * 
//	 * @param userEntity
//	 * @return
//	 */
//	@RequestMapping(value = "/selectUserInfoById", method = RequestMethod.POST, headers = "Accept=application/json")
//	public HttpResponseEntity selectUserInfoById(@RequestBody UserEntity userEntity) {
//		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//
//		return httpResponseEntity;
//	}
//
//	/**
//	 * 修改用户状态
//	 * 
//	 * @param map
//	 * @return
//	 */
//	@RequestMapping(value = "/modifyUserStatus", method = RequestMethod.POST, headers = "Accept=application/json")
//	public HttpResponseEntity modifyUserStatus(@RequestBody Map<String, Object> map) {
//		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//
//		return httpResponseEntity;
//	}
//
//	/**
//	 * 删除用户信息
//	 * 
//	 * @param userEntity
//	 * @return
//	 */
//	@RequestMapping(value = "/deleteUserInfoById", method = RequestMethod.POST, headers = "Accept=application/json")
//	public HttpResponseEntity deteleUserInfoById(@RequestBody UserEntity userEntity) {
//		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//
//		return httpResponseEntity;
//	}
//
//	/**
//	 * 用户没有权限
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/error")
//	public HttpResponseEntity logout() {
//		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//
//		return httpResponseEntity;
//	}
    
    
    
    
}
