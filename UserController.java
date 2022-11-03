package com.aim.questionnaire.controller;

import java.util.List;
import java.util.Map;

import com.aim.questionnaire.dao.entity.AdminEntity;
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
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.dao.entity.UserEntity;
import com.aim.questionnaire.service.UserService;
import com.github.pagehelper.PageInfo;


/**
 * Created by wln on 2018\8\9 0009.
 */
@RestController
@RequestMapping("/admin")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserEntityMapper userEntityMapper;
   
    /**
     * 用户登录
     * @param map
     * @return
     */
    @RequestMapping(value="/userLogin",method= RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity userLogin(@RequestBody UserEntity userEntity) {
//        System.out.println(userEntity.toString());
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {

            List<UserEntity> users = userEntityMapper.selectUserInfo(userEntity);
            List<UserEntity> user = userEntityMapper.selectUserInfoByPh(userEntity);
            System.out.println(users.size());
            boolean no_Users=CollectionUtils.isEmpty(users);
            boolean no_User= CollectionUtils.isEmpty(user);
            if(no_Users==false){
                if(users.get(0).getPassword().equals(userEntity.getPassword())){
                    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                    httpResponseEntity.setData(null);
                    httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
                }

                else {
                    httpResponseEntity.setCode(Constans.EXIST_CODE);
                    httpResponseEntity.setData(users);
                    httpResponseEntity.setMessage(Constans.LOGIN_USERNAME_PASSWORD_MESSAGE);
                }
            }
            if (no_User==false){
                if(user.get(0).getPassword().equals(userEntity.getPassword())){
                    System.out.println(111);
                    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                    httpResponseEntity.setData(null);
                    httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
                }
                else {
                    System.out.println(user.get(0).getPassword());
                    System.out.println(222);
                    httpResponseEntity.setCode(Constans.EXIST_CODE);
                    httpResponseEntity.setData(users);
                    httpResponseEntity.setMessage(Constans.LOGIN_USERNAME_PASSWORD_MESSAGE);
                }
            }
            else if(no_Users==true&&no_User==true){
                System.out.println(11111);
                String code_Set=Constans.EXIST_CODE;
                httpResponseEntity.setCode(code_Set);
                httpResponseEntity.setData(null);
                String message_Set=Constans.LOGIN_USERNAME_PASSWORD_MESSAGE;
                httpResponseEntity.setMessage(message_Set);
            }


        } catch (Exception e) {
            logger.error("ui改版-首页信息查询失败",e);
            logger.info("userLogin 用户登录>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 查询用户列表（模糊搜索）
     * @param map
     * @return
     */
    @RequestMapping(value = "/queryUserList",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryUserList(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            List<Map<String,Object>> users=userEntityMapper.queryUserList(map);

            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            PageInfo pageinfo=userService.queryUserList(map);
            httpResponseEntity.setData(userService.queryUserList(map));
            httpResponseEntity.setData(pageinfo);
            httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);

        }catch(Exception e){
            logger.info("用户查询>>>>>>>>>>>"+e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }
    /**
     * 创建用户的基本信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/addUserInfo",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addUserInfo(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            int if_Exist = userService.addUserInfo(map);
            System.out.println(if_Exist);
            if(if_Exist == 3) {
                httpResponseEntity.setCode(Constans.USER_USERNAME_CODE);
                httpResponseEntity.setMessage(Constans.USER_USERNAME_MESSAGE);
            }else if(if_Exist==4){
                httpResponseEntity.setCode(Constans.USER_USERNAME_CODE);
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage(Constans.USER_PHONENUMBER_MESSAGE);
            }else if(if_Exist==5) {
                httpResponseEntity.setCode(Constans.USER_USERNAME_CODE);
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage(Constans.USER_PHONE_MESSAGE);
            }else {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String user_Info=e.getLocalizedMessage();
            logger.info("addUserInfo 创建用户的基本信息>>>>>>>>>>>" + user_Info);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 编辑用户的基本信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/modifyUserInfo",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyUserInfo(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String id = (String) map.get("id");
        System.out.println(id);
//        int ifcan = userService.selectUserInfoById(id);
        try {
//            if(ifcan==1) {
            int if_Exist = userService.modifyUserInfo(map);
            System.out.println(if_Exist);
            if(if_Exist == 3) {
                httpResponseEntity.setCode(Constans.USER_USERNAME_CODE);
                httpResponseEntity.setMessage(Constans.USER_USERNAME_MESSAGE);
            }
            else if(if_Exist ==4){
                httpResponseEntity.setCode(Constans.USER_PHONE_CODE);
                httpResponseEntity.setMessage(Constans.USER_PHONE_MESSAGE);
            } else{
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
                userService.modifyUserInfo(map);
                httpResponseEntity.setCode("666");
                httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
            }

        } catch (Exception e) {
            String user_Info=e.getLocalizedMessage();
            logger.info("addUserInfo 创建用户的基本信息>>>>>>>>>>>" + user_Info);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }


    /**
     *  根据用户id查询用户基本信息
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/selectUserInfoById",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity selectUserInfoById(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }



    /**
     * 修改用户状态
     * @param map
     * @return
     */
    @RequestMapping(value = "/modifyUserStatus",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyUserStatus(@RequestBody Map<String,Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        
        return httpResponseEntity;
    }
    /**
     *  删除用户信息
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/deleteUserInfoById",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteUserInfoById(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        System.out.println("Controller准备删除");
        System.out.println(userEntity.toString());
        int if_Exist= userService.deleteUserInfoById(userEntity);
        System.out.println("controller删除");
        if(if_Exist==3){
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.PROJECT_EXIST_MESSAGE);
        }else {
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);

            httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
        }
        return httpResponseEntity;
    }


    /**
     * 用户没有权限
     * @return
     */
    @RequestMapping(value = "/error")
    public HttpResponseEntity logout() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        
        return httpResponseEntity;
    }
}
