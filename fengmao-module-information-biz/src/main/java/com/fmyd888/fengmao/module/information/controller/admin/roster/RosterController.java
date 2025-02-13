package com.fmyd888.fengmao.module.information.controller.admin.roster;

import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.module.information.controller.admin.roster.vo.RosterRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo.SalaryRuleRespVO;
import com.fmyd888.fengmao.module.information.service.roster.RosterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 花名册")
@RestController
@RequestMapping("/information/roster")
@Validated
public class RosterController {

    @Resource
    private RosterService rosterService;

    @GetMapping("/simple-list")
    @Operation(summary = "获取花名册信息简表")
    @PreAuthorize("@ss.hasPermission('information:roster:query')")
    public CommonResult<List<RosterRespVO>> getRosterSimpleList() {
        List<RosterRespVO> result = rosterService.getRosterSimpleList();
        return success(result);
    }
}
