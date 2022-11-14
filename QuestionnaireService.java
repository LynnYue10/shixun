package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.QuestionnaireMapper;
import com.aim.questionnaire.dao.entity.Questionnaire;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.protobuf.TextFormat.ParseException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireService {

	@Autowired
	private QuestionnaireMapper questionnaireMapper;
	@Autowired
	private QuestionnaireService questionnaireService;

	/*
	 * public int selectByProjectId(String id) { return
	 * questionnaireMapper.selectByProjectId(id); }
	 */

	public int selectByProjectId(String id) {
		List<Map<String, Object>> l = questionnaireService.queryAllQuestionnaireInfo();
		for (Map<String, Object> m : l) { // 已发送
			if (m.get("projectId") != null) {
				if (m.get("projectId").equals(id)) {
					if (m.get("status").equals("2") || m.get("status").equals("1")) {
						return 0;
					}
				}
			}
		}

		for (Map<String, Object> m : l) {
			if (m.get("projectId") != null) {
				if (m.get("projectId").equals(id)) {
					String id1 = (String) m.get("id");
					questionnaireMapper.deleteQuestionnaireById(id1);
				}
			}

		}
		return 1;
	}

	public int addQuestionnaire(Questionnaire questionnaire) {
		questionnaire.setId(UUIDUtil.getOneUUID());
		if (questionnaire.getStartTime() != null && questionnaire.getEndTime() != null) {
			// 设置开始或结束时间
			Long startTimeL = questionnaire.getStartTime().getTime();
			Long endTimeStL = questionnaire.getEndTime().getTime();
			String startTimeStr = String.valueOf(startTimeL);
			String endTimeStr = String.valueOf(endTimeStL);
			Date startTime = DateUtil.getMyTime(startTimeStr);
			Date endTime = DateUtil.getMyTime(endTimeStr);
			questionnaire.setStartTime(startTime);
			questionnaire.setEndTime(endTime);
			// 设置群组以及未被删除
			questionnaire.setIsdelete("0");
			questionnaire.setGroupname("未发送");
			// 设施status
			Date date = new Date();
			long now = date.getTime();
			if (now < startTimeL) {
				questionnaire.setStatus("0");
			} else {
				questionnaire.setStatus("1");
			}
			// 获取模板的题目和选型并赋予给这个新的问卷
			if (questionnaire.getProjectId() != null) {
				Map<String, Object> target = questionnaireService.queryQuestionnaireById(questionnaire.getProjectId());
				String question = (String) target.get("question");
				String questionTitle = (String) target.get("questionTitle");
				
				questionnaire.setQuestion(question);
				questionnaire.setQuestionTitle(questionTitle);
			
			}

			questionnaireMapper.addQuestionnaire(questionnaire);
		} else {
			questionnaire.setStatus("0");
			// 获取模板的题目和选型并赋予给这个新的问卷
			if (questionnaire.getProjectId() != null) {
				Map<String, Object> target = questionnaireService.queryQuestionnaireById(questionnaire.getProjectId());
				String question = (String) target.get("question");
				String questionTitle = (String) target.get("questionTitle");
				 questionnaire.setQuestion(question);
				 questionnaire.setQuestionTitle(questionTitle);
				 
			}
			questionnaireMapper.addQuestionnaire(questionnaire);
		}
		return 0;
	}

	public PageInfo queryQuestionnaireByProjectId(Map<String, Object> map) {
		int pagenum = (int) map.get("pageNum");
		int pageSize = (int) map.get("pageSize");
		PageHelper.startPage(pagenum, pageSize);
		List<Map<String, Object>> resultList = questionnaireMapper.queryQuestionnaireByProjectId(map);
		PageInfo<Map<String, Object>> page = new PageInfo<>(resultList);
		return page;
	}

	/**
	 * 查询全部问卷
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryAllQuestionnaireInfo() {
		return questionnaireMapper.selectAllQuestionnaire();
	}

	/**
	 * 查询全部问卷
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryQuestionnaireInfo(Questionnaire questionnaire) {
		return questionnaireMapper.queryQuestionnaireInfo(questionnaire);
	}

	/**
	 * 根据问卷id查询问卷信息
	 * 
	 * @param questionnaireId
	 * @return
	 */
	public Map<String, Object> queryQuestionnaireById(String questionnaireId) {
		return questionnaireMapper.selectQuestionnaireById(questionnaireId);
	}

	/**
	 * 根据问卷id查询删除的问卷信息
	 * 
	 * @param questionnaireId
	 * @return
	 */
	public Map<String, Object> queryDeletedQuestionnaireById(String questionnaireId) {
		return questionnaireMapper.selectDeletedQuestionnaireById(questionnaireId);
	}

	/**
	 * 展示全部问卷
	 * 
	 * @return
	 */
	public PageInfo showAllQuestionnaireInfo(Map<String, Object> map) {
		int pagenum = (int) map.get("pageNum");
		int pageSize = (int) map.get("pageSize");
		String questionnaireName = null;
		if (map.get("questionnaireName") == null) {
			questionnaireName = "";
		} else if (map.get("questionnaireName") != null) {
			questionnaireName = (String) map.get("questionnaireName");
		}
		PageHelper.startPage(pagenum, pageSize);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (questionnaireName.equals("")) {
			resultList = questionnaireMapper.queryQuestionnaireList(map);
		} else {
			resultList = questionnaireMapper.queryQuestionnaireByName(map);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(resultList);
		return page;
	}

	/**
	 * 展示已删除全部问卷
	 * 
	 * @return
	 */
	public PageInfo showAllDeletedQuestionnaireInfo(Map<String, Object> map) {
		int pagenum = (int) map.get("pageNum");
		int pageSize = (int) map.get("pageSize");
		String questionnaireName = null;
		if (map.get("questionnaireName") == null) {
			questionnaireName = "";
		} else if (map.get("questionnaireName") != null) {
			questionnaireName = (String) map.get("questionnaireName");
		}
		PageHelper.startPage(pagenum, pageSize);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (questionnaireName.equals("")) {
			resultList = questionnaireMapper.queryDeletedQuestionnaireList(map);
		} else {
			resultList = questionnaireMapper.queryDeletedQuestionnaireByName(map);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(resultList);
		return page;
	}

	/**
	 * 修改问卷信息
	 * 
	 * @param questionnaire
	 * @return
	 */
	public int modifyQuestionnaireInfo(Questionnaire questionnaire) {
		// 设置修改时间

		int result = questionnaireMapper.modifyQuestionnaireInfo(questionnaire);
		// return questionnaireMapper.updateByPrimaryKeySelective(questionnaire);
		return result;
	}
	
	/**
	 * 修改已发送的问卷为未发送
	 * 
	 * @param questionnaire
	 * @return
	 */
	public int modifySendQuestionnaireInfo(Questionnaire questionnaire) {
		// 设置修改时间

		int result = questionnaireMapper.modifySendQuestionnaireInfo(questionnaire);
		// return questionnaireMapper.updateByPrimaryKeySelective(questionnaire);
		return result;
	}

	/**
	 * 判断是否可以发送并修改信息修改问卷信息
	 * 
	 * @param questionnaire
	 * @return
	 */
	public int modifySendQuestionnaire(Questionnaire questionnaire) {
		int result = 0;
		Map<String, Object> target = questionnaireMapper.selectQuestionnaireById(questionnaire.getId());
		if (target.get("groupname").equals("未发送")) {
			// 设置修改时间
			if (questionnaire.getReleaseTime() == null) {
				Date date = DateUtil.getCurrentDate();
				// questionnaireEntity.setLastUpdateDate(date);
				questionnaire.setReleaseTime(date);
			}

			result = questionnaireMapper.modifyQuestionnaireInfo(questionnaire);
			// return questionnaireMapper.updateByPrimaryKeySelective(questionnaire);
			return result;
		} else {
			result = 0;
		}

		return result;
	}

	public int deleteQuestionnaireById(String id) throws ParseException {
		Map<String, Object> m = questionnaireService.queryQuestionnaireById(id);
		if (!m.get("groupname").equals("未发送")) {
			return 1;
		}

		questionnaireMapper.deleteQuestionnaireById(id);
		return 0;
	}

	public int deleteQuestionnaireByIdCompletely(String id) throws ParseException {
		Map<String, Object> m = questionnaireService.queryQuestionnaireById(id);
		questionnaireMapper.deleteQuestionnaireById(id);
		return 0;
	}

	public int modifyQuestionnaire(Questionnaire questionnaire) {

		// 设置修改时间
		return questionnaireMapper.updateByPrimaryKeySelective(questionnaire);
	}

	public int selectupByProjectId(String id) {
		List<Map<String, Object>> l = questionnaireService.queryAllQuestionnaireInfo();
		for (Map<String, Object> m : l) {
			if (m.get("status").equals("2") && m.get("projectId").equals(id)) {
				return 0;
			}
		}
		return 1;
	}

	public int deleteModel(Questionnaire questionnaire) {
		return questionnaireMapper.deleteModel(questionnaire);

	}
}
