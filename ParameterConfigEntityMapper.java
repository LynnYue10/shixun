package com.aim.questionnaire.dao;

import com.aim.questionnaire.dao.entity.ParameterConfigEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ParameterConfigEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter_config_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter_config_info
     *
     * @mbg.generated
     */
    int insert(ParameterConfigEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter_config_info
     *
     * @mbg.generated
     */
    int insertSelective(ParameterConfigEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter_config_info
     *
     * @mbg.generated
     */
    ParameterConfigEntity selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter_config_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ParameterConfigEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter_config_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ParameterConfigEntity record);

    /**
     * 查询参数列表（模糊搜索）
     * @param map
     * @return
     */
    List<Map<String,Object>> queryParameterList(Map<String, Object> map);

    /**
     * 根据id查询参数信息
     * @param map
     * @return
     */
    int modifyParameterList(Map<String, Object> map);

    /**
     * 查询所有的角色的权限
     * @return
     */
    List<Map<String,String>> queryAllPathAndPermission();

    /**
     * 添加参数信息
     * @param map
     * @return
     */
    int addParameterInfo(Map<String, Object> map);

    /**
     * 查询短信发送密码
     * @param s
     * @return
     */
    String queryMessagePassword(String s);
}