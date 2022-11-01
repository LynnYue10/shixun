package com.aim.questionnaire.dao;

import com.aim.questionnaire.dao.entity.AnswerEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AnswerEntityMapper {
	
    /**
     * 查询用户列表（模糊搜索）
     * @param name
     * @return
     */
    String queryUserList(String name);
    
    /**
     * 查询问卷列表
     * @param map
     * @return
     */
    List<Map<String,Object>> queryQuestionnaireByProjectId( Map<String, Object> map);

}
