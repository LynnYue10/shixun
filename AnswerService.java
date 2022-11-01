package com.aim.questionnaire.service;
import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.AnswerEntityMapper;
import com.aim.questionnaire.dao.QuestionnaireEntityMapper;
import com.aim.questionnaire.dao.QuestionnaireMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class AnswerService {

    @Autowired
    private AnswerEntityMapper answerEntityMapper;

    //@Autowired
    //private SysUserService sysUserService;

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
//    /**
//     * 创建用户的基本信息
//     * @param map
//     * @return
//     */
//    public int addUserInfo(Map<String,Object> map) {
//        if(map.get("username") != null) {
//            int userResult = userEntityMapper.queryExistUser(map);
//            if(userResult != 0) {
//                //用户名已经存在
//                return 3;
//            }
//        }
//
//        String id = UUIDUtil.getOneUUID();
//        map.put("id",id);
//        //创建时间
//        Date date = DateUtil.getCreateTime();
//        map.put("creationDate",date);
//        map.put("lastUpdateDate",date);
//        //创建人
//        String user = "admin";
//        map.put("createdBy",user);
//        map.put("lastUpdatedBy",user);
//        //前台传入的时间戳转换
//        String startTimeStr = map.get("startTime").toString();
//        String endTimeStr = map.get("stopTime").toString();
//        Date startTime = DateUtil.getMyTime(startTimeStr);
//        Date endTime = DateUtil.getMyTime(endTimeStr);
//        map.put("startTime",startTime);
//        map.put("stopTime",endTime);
//
//        int result = userEntityMapper.addUserInfo(map);
//        return result;
//    }
//
//    /**
//     * 编辑用户的基本信息
//     * @param map
//     * @return
//     */
//    public int modifyUserInfo(Map<String, Object> map) {
//
//        return 0;
//    }
//
//    /**
//     * 修改用户状态
//     * @param map
//     * @return
//     */
//    public int modifyUserStatus(Map<String, Object> map) {
//        return 0;
//    }
//
//    /**
//     * 根据id查询用户信息
//     * @param userEntity
//     * @return
//     */
//    public Map<String,Object> selectUserInfoById(UserEntity userEntity) {
//  
//        return null;
//    }
//
//    /**
//     * 删除用户信息
//     * @param userEntity
//     * @return
//     */
//    public int deteleUserInfoById(UserEntity userEntity) {
//
//        return 0;
//    }
//    
//    
    
    
}
