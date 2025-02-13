package com.fmyd888.fengmao.module.information.controller.admin.car.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author:wu
 * @create: 2024-07-19 14:21
 * @Description: 流程实例发起
 */
@Data
@Valid
public class CarProcessDTO {
    /**
     * 流程定义id
     */
    private String processDefinitionId;
    /**
     * 表单数据
     */
    private JSONObject formValues;
    /**
     * 表单id
     */
    private Long templateId = 25L;
}
