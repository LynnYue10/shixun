package com.aim.questionnaire.dao;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.aim.questionnaire.dao.entity.Questionnaire;

@Repository
public interface QuestionnaireMapper {
     int selectByProjectId(@Param("id") String id) ;
     
     int addQuestionnaire(Questionnaire questionnaire);
     /**
      * 查询问卷列表
      * @param map
      * @return
      */
     List<Map<String,Object>> queryQuestionnaireByProjectId( Map<String, Object> map);
     
     List<Map<String, Object>> selectAllQuestionnaire();
     
     List<Map<String, Object>> queryQuestionnaireInfo(Questionnaire questionnaire);
     
     List<Map<String,Object>> queryQuestionnaireList(Map<String, Object> map);
     
     List<Map<String,Object>> queryDeletedQuestionnaireList(Map<String, Object> map);
     
     List<Map<String,Object>> queryQuestionnaireByName(Map<String, Object> map);
     
     List<Map<String,Object>> queryDeletedQuestionnaireByName(Map<String, Object> map);
     
     Map<String, Object> selectQuestionnaireById(String questionnaireId);
     
     Map<String, Object> selectDeletedQuestionnaireById(String questionnaireId);
     
     /**
      * This method was generated by MyBatis Generator.
      * This method corresponds to the database table questionnaire_info
      *
      * @mbg.generated
      */
     int modifyQuestionnaireInfo(Questionnaire result);
     
     int modifySendQuestionnaireInfo(Questionnaire result);
     
     /**
      * 根据问卷id删除问卷
      * @param id
      * @return
      */
     void deleteQuestionnaireById(String id);
     
     List<Map<String, Object>> queryUserList(String groupName);
     
     int updateByPrimaryKeySelective(Questionnaire questionnaire);
     
     int deleteModel(Questionnaire questionnaire);
}
