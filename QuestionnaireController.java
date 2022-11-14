package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.entity.ProjectEntity;
import com.aim.questionnaire.dao.entity.Questionnaire;
import com.aim.questionnaire.service.ProjectService;

import com.aim.questionnaire.service.QuestionnaireService;
import com.aim.questionnaire.service.UserService;
import com.github.pagehelper.PageInfo;
import com.google.protobuf.TextFormat.ParseException;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wln on 2018\8\6 0006.
 */
@RestController
public class QuestionnaireController {

	private final Logger logger = LoggerFactory.getLogger(QuestionnaireController.class);

	@Autowired
	private QuestionnaireService questionnaireService;

	/**
	 * 添加问卷
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/addQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity addQuestionnaire(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		int result = questionnaireService.addQuestionnaire(questionnaire);
		httpResponseEntity.setCode(Constans.SUCCESS_CODE);
		httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
		return httpResponseEntity;
	}

	/**
	 * 查看项目下问卷
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/queryQuestionnaireByProjectId", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity queryQuestionnaireByProjectId(@RequestBody Map<String, Object> map) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		PageInfo list = questionnaireService.queryQuestionnaireByProjectId(map);
		httpResponseEntity.setData(list);
		httpResponseEntity.setCode(Constans.SUCCESS_CODE);
		return httpResponseEntity;
	}

	/**
	 * 查询全部问卷
	 * 
	 * @param questionnaireEntity
	 * @return
	 */
	@RequestMapping(value = "/queryAllQuestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity queryAllQuestionnaireInfo(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		try {
			List<Map<String, Object>> list = questionnaireService.queryAllQuestionnaireInfo();
			List<Map<String, Object>> history = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> m : list) {
				if (m.get("projectId") != null) {
					history.add(m);
				}
			}
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
			httpResponseEntity.setData(list);
		} catch (Exception e) {
			logger.error("delete questionnaire error", e);
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
		}
		return httpResponseEntity;
	}

	/**
	 * 查询相同类型的历史问卷
	 * 
	 * @param questionnaireEntity
	 * @return
	 */
	@RequestMapping(value = "/queryHistoryQuestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity queryHistoryQuestionnaire(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		try {
			List<Map<String, Object>> list = questionnaireService.queryQuestionnaireInfo(questionnaire);
			List<Map<String, Object>> history = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> m : list) {
				if (!m.get("groupname").equals("模板")) {
					if (m.get("dataId").equals(questionnaire.getDataId())) {
						history.add(m);
					}
				}
			}
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
			httpResponseEntity.setData(history);
		} catch (Exception e) {
			logger.error("delete questionnaire error", e);
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
		}
		return httpResponseEntity;
	}

	/**
	 * 查询全部问卷
	 * 
	 * @param questionnaireEntity
	 * @return
	 */
	@RequestMapping(value = "/showAllQuestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity showAllQuestionnaireInfo(@RequestBody Map<String, Object> map) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		try {
			PageInfo list = questionnaireService.showAllQuestionnaireInfo(map);
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
			httpResponseEntity.setData(list);
		} catch (Exception e) {
			logger.error("delete questionnaire error", e);
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
		}
		return httpResponseEntity;
	}

	/**
	 * 查询全部已删除问卷
	 * 
	 * @param questionnaireEntity
	 * @return
	 */
	@RequestMapping(value = "/showAllDeletedQuestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity showAllDeletedQuestionnaireInfo(@RequestBody Map<String, Object> map) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		try {
			PageInfo list = questionnaireService.showAllDeletedQuestionnaireInfo(map);
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
			httpResponseEntity.setData(list);
		} catch (Exception e) {
			logger.error("delete questionnaire error", e);
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
		}
		return httpResponseEntity;
	}

	/**
	 * 设计问卷相关
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/modifyQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity modifyQuestionnaire(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		try {
			questionnaireService.modifyQuestionnaire(questionnaire);
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setData(questionnaire.getId());
			httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
		} catch (Exception e) {
			logger.error("modify questionnaire error", e);
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setData(questionnaire.getId());
			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
		}
		return httpResponseEntity;
	}

	/**
	 * 根据id查找问卷
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/queryQuestionnaireById", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity queryQuestionnaireById(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		try {
			Map<String, Object> map = questionnaireService.queryQuestionnaireById(questionnaire.getId());
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
			httpResponseEntity.setData(map);
		} catch (Exception e) {
			logger.error("query questionnaire error", e);
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
		}
		return httpResponseEntity;
	}

	/**
	 * 根据id判断问卷是否为模板或已发送
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/judgeQuestionnaireById", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity judgeQuestionnaireById(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		try {
			Map<String, Object> map = questionnaireService.queryQuestionnaireById(questionnaire.getId());
			if (map.get("groupname").equals("模板")) {
				httpResponseEntity.setCode(Constans.EXIST_CODE);
				httpResponseEntity.setMessage(Constans.MODEL_FAILend_MESSAGE);
			} else {
				httpResponseEntity.setCode(Constans.SUCCESS_CODE);
				httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
				httpResponseEntity.setData(map);
			}

		} catch (Exception e) {
			logger.error("query questionnaire error", e);
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
		}
		return httpResponseEntity;
	}

	/**
	 * 根据id查找删除的问卷
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/queryDeletedQuestionnaireById", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity queryDeletedQuestionnaireById(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		try {
			Map<String, Object> map = questionnaireService.queryDeletedQuestionnaireById(questionnaire.getId());
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
			httpResponseEntity.setData(map);
		} catch (Exception e) {
			logger.error("query questionnaire error", e);
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
		}
		return httpResponseEntity;
	}

	/**
	 * 修改问卷信息
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/modifyQuestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity modifyQuestionnaireInfo(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		if (questionnaire.getStatus().equals("1")) {
			questionnaire.setStatus("3");
			questionnaireService.modifyQuestionnaireInfo(questionnaire);
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.STOP_EXIT_UPDATE_MESSAGE);
		} else if (questionnaire.getStatus().equals("2")) {
			Date releasetime = new Date();
			questionnaire.setReleaseTime(releasetime);
			questionnaireService.modifyQuestionnaireInfo(questionnaire);
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
		} else {
			questionnaireService.modifyQuestionnaireInfo(questionnaire);
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
		}

		return httpResponseEntity;
	}
	
	
	/**
	 * 修改问卷发送状态
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/modifySendQuestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity modifySendQuestionnaireInfo(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
			
			questionnaireService.modifySendQuestionnaireInfo(questionnaire);
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.UPDATE_MESSAGE);
		
		return httpResponseEntity;
	}

	/**
	 * 修改问卷信息
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/ModifyQuestionnaireIsDelete", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity ModifyQuestionnaireIsDelete(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		if (questionnaire.getGroupname().equals("未发送") || questionnaire.getGroupname().equals("模板")) {
			questionnaireService.modifyQuestionnaireInfo(questionnaire);
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
		} else {
			httpResponseEntity.setCode(Constans.DELETE_FAILend_CODE);
			httpResponseEntity.setMessage(Constans.DELETE_FAILend);
		}

		return httpResponseEntity;
	}

	/**
	 * 发送前保存问卷信息
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/addSendQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity addSendQuestionnaire(@RequestBody Questionnaire questionnaire) {

		/*
		 * String host = "https://dfsns.market.alicloudapi.com"; String path =
		 * "/data/send_sms"; String method = "POST"; String appcode =
		 * "99d7211d9fd3454e9422738d0070b4e3"; Map<String, String> headers = new
		 * HashMap<String, String>(); //最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		 * 83359fd73fe94948385f570e3c139105 headers.put("Authorization", "APPCODE " +
		 * appcode); //根据API的要求，定义相对应的Content-Type headers.put("Content-Type",
		 * "application/x-www-form-urlencoded; charset=UTF-8"); Map<String, String>
		 * querys = new HashMap<String, String>(); Map<String, String> bodys = new
		 * HashMap<String, String>(); bodys.put("content", "code:2562");
		 * bodys.put("phone_number", "13889723113"); bodys.put("template_id",
		 * "TPL_0000");
		 * 
		 * 
		 * try {
		 *//**
			 * 重要提示如下: HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 *//*
				 * HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys,
				 * bodys); System.out.println(response.toString()); } catch (Exception e) {
				 * e.printStackTrace(); }
				 */
		int result;
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		result = questionnaireService.modifySendQuestionnaire(questionnaire);
		if (result == 0) {
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setMessage(Constans.DETAIL_FAILend_MESSAGE);
		} else {
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
		}

		return httpResponseEntity;
	}

	/**
	 * 修改问卷信息
	 * 
	 * @param questionnaire
	 * @return
	 */
	@RequestMapping(value = "/RecoverQuestionnaire", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity RecoverQuestionnaire(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		if (questionnaire.getGroupname().equals("未发送") || questionnaire.getGroupname().equals("模板")) {
			questionnaireService.modifyQuestionnaireInfo(questionnaire);
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.RECOVER_MESSAGE);
		} else {
			httpResponseEntity.setCode(Constans.DELETE_FAILend_CODE);
			httpResponseEntity.setMessage(Constans.RECOVER_FAILend);
		}

		return httpResponseEntity;
	}

	@RequestMapping(value = "/deleteQuestionnaireInfo", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity deleteProjectById(@RequestBody Questionnaire questionnaire) throws ParseException {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		int result = questionnaireService.deleteQuestionnaireById(questionnaire.getId());
		if (result == 1) {
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setData(questionnaire.getId());
			httpResponseEntity.setMessage(Constans.DELETE_FAILend);
		} else {
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setData(questionnaire.getId());
			httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
		}

		return httpResponseEntity;
	}

	@RequestMapping(value = "/deleteQuestionnaireInfoCompletely", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity deleteProjectByIdCompletely(@RequestBody Questionnaire questionnaire)
			throws ParseException {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		int result = questionnaireService.deleteQuestionnaireByIdCompletely(questionnaire.getId());
		if (result == 0) {
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setData(questionnaire.getId());
			httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
		} else {
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setData(questionnaire.getId());
			httpResponseEntity.setMessage(Constans.DELETE_COM_FAILend);
		}

		return httpResponseEntity;
	}

	/**
	 * 查询模板
	 * 
	 * @param questionnaireEntity
	 * @return
	 */
	@RequestMapping(value = "/queryQuestionnaireMould", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity queryQuestionnaireMould(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		try {
			List<Map<String, Object>> list = questionnaireService.queryQuestionnaireInfo(questionnaire);
			List<Map<String, Object>> model = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> m : list) {
				if (m.get("groupname").equals("模板")) {
					model.add(m);
				}
			}
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
			httpResponseEntity.setData(model);
		} catch (Exception e) {
			logger.error("delete questionnaire error", e);
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
		}
		return httpResponseEntity;
	}

	/**
	 * 删除模板
	 * 
	 * @param questionnaireEntity
	 * @return
	 */
	@RequestMapping(value = "/deleteModel", method = RequestMethod.POST, headers = "Accept=application/json")
	public HttpResponseEntity deleteModel(@RequestBody Questionnaire questionnaire) {
		HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
		int result = questionnaireService.deleteModel(questionnaire);
		try {
			httpResponseEntity.setCode(Constans.SUCCESS_CODE);
			httpResponseEntity.setMessage(Constans.DELETE_MESSAGE);
		} catch (Exception e) {
			logger.error("delete questionnaire error", e);
			httpResponseEntity.setCode(Constans.EXIST_CODE);
			httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
		}
		return httpResponseEntity;
	}

}
