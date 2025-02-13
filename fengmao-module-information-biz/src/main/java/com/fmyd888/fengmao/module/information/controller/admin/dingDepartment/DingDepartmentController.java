package com.fmyd888.fengmao.module.information.controller.admin.dingDepartment;

import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.module.information.controller.admin.dingDepartment.dto.DeptNodeDto;
import com.fmyd888.fengmao.module.information.service.dingDepartment.DingDepartmentService;
import com.taobao.api.ApiException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Title: DingDepartmentController
 * @Author: huanhuan
 * @Date: 2024-03-29 11:10
 * @Description:
 */
@Tag(name = "管理后台 - 初始化钉钉部门、花名册和中间表")
@RestController
@RequestMapping("/information/dingTalk/")
public class DingDepartmentController {
    @Resource
    private DingDepartmentService dingDepartmentService;


    @GetMapping("/init")
    private CommonResult<Void> initRoster(Long deptId) throws ApiException {
        dingDepartmentService.dingTalkInit(deptId);
        return CommonResult.success(null);
    }

}
