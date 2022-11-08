package com.aim.questionnaire.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.aim.questionnaire.dao.entity.AnswerEntity;
import com.aim.questionnaire.dao.entity.ProjectEntity;

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
	
    List<Map<String,Object>> queryAnswerByGroupname( Map<String, Object> map);
    
    List<Map<String,Object>> queryAllAnswerList( Map<String, Object> map);
    
    
	/**
     * 查询答者列表
     * @param String
     * @return 
     */
	@Mapper
	List<Map<String, Object>> queryAnswerList(String username,String answername);
	
	/**
     * 根据答者id删除
     * @param id
     * @return
     */
	@Mapper
    void deleteAnswerById(String id);

    /**
     * 添加答者
     * @param answerEntity
     * @return
     */
	@Mapper
    void addAnswer(AnswerEntity answerEntity);

	
	/**
     * 查询答者
     * @param answerEntity
     * @return
     */
	@Mapper
	List<Map<String, Object>> ifhasanswer(String answername);

	
	/**
     * 查询答者
     * @param id
     * @return
     */
	@Mapper
	AnswerEntity findAnswerById(String id);

	/**
     * 修改答者
     * @param id
     * @return
     */
	@Mapper
	void modifyAnswer(AnswerEntity answerEntity);
	
	/**
     * 查询delete答者列表
     * @param String
     * @return 
     */
	@Mapper
	List<Map<String, Object>> queryDeletedAnswerList(String username,String answername);
	
	/**
     * 根据答者id彻底删除
     * @param id
     * @return
     */
	@Mapper
    void finnaldeleteAnswerById(String id);
	
	/**
     * 根据答者id恢复
     * @param id
     * @return
     */
	@Mapper
    void findbackAnswerById(String id);

}
