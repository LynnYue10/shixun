package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import com.aim.questionnaire.service.ProjectService;

import com.aim.questionnaire.service.QuestionnaireService;
import com.aim.questionnaire.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wln on 2018\8\6 0006.
 */
@RestController
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;
    @Autowired
    private QuestionnaireService questionnaireService;
    private UserService userService;


    /**
     * 查询全部项目的信息
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/queryProjectList",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryProjectList(@RequestBody(required = false) ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        List<Map<String, Object>> maps = projectService.queryProjectListById(projectEntity);
        httpResponseEntity.setData(maps);
        httpResponseEntity.setCode("666");
        return httpResponseEntity;
    }

    /**
     * 根据id删除项目
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/deleteProjectById",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteProjectById(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
      //判断当前下面下是否有开启的题库
        int count = questionnaireService.selectByProjectId(projectEntity.getId());
        if(count==0){
             httpResponseEntity.setCode("333");
                httpResponseEntity.setCode(Constans.QUESTIONNAIRE_SEND_CODE);
                httpResponseEntity.setMessage(Constans.QUESTION_EXIST_NODELET);
         }else {
             //删除项目
                projectService.deleteProjectById(projectEntity);
                httpResponseEntity.setCode("666");
                httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
         }
        return httpResponseEntity;
    }

    /**
     * 添加项目 
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/addProjectInfo",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        List<Map<String, Object>> maps = projectService.queryProjectList(projectEntity);
        if(!CollectionUtils.isEmpty(maps)){
            httpResponseEntity.setCode("111");
        }else {
            /**
             * 新增项目到mysql
             * 
             */
        	String user = projectEntity.getCreatedBy();;
            projectService.addProjectInfo(projectEntity,user);
            httpResponseEntity.setCode("666");
        }
        return httpResponseEntity;
    }

    /**
     * 根据项目id修改项目（如何修改）
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/modifyProjectInfo",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        //判断当前下面下是否有开启的题库
        
         int count = questionnaireService.selectupByProjectId(projectEntity.getId());
            if(count==0){
                httpResponseEntity.setCode("333");
                httpResponseEntity.setCode(Constans.QUESTIONNAIRE_SEND_CODE);
                httpResponseEntity.setMessage(Constans.QUESTION_EXIST_MESSAGE);
            }else {
                //修改项目
            	List<Map<String, Object>> maps = projectService.queryProjectList(projectEntity);
                if(!CollectionUtils.isEmpty(maps)){
                    httpResponseEntity.setCode("111");
                    httpResponseEntity.setMessage(Constans.PROJECT_HAS_MESSAGE);
                }else {
                projectService.modifyProjectInfo(projectEntity,null);
                httpResponseEntity.setCode("666");
            }
        }
        
        return httpResponseEntity;
    }


    /**
     * 查询全部项目的名字接口
     * @return
     */
    @RequestMapping(value = "/queryAllProjectName",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryAllProjectName() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
          
        return httpResponseEntity;
    }
}
