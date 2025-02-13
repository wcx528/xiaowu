package com.fmyd888.fengmao.module.information.controller.admin.salaryRule;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule.SalaryRuleDO;
import com.fmyd888.fengmao.module.information.service.salaryRule.SalaryRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 薪资规则配置")
@RestController
@RequestMapping("/information/salary-rule")
@Validated
public class SalaryRuleController {

    @Resource
    private SalaryRuleService salaryRuleService;

    @PostMapping("/create")
    @Operation(summary = "创建薪资规则配置")
    @PreAuthorize("@ss.hasPermission('information:salary-rule:create')")
    public CommonResult<Long> createSalaryRule(@Valid @RequestBody SalaryRuleSaveReqVO createReqVO) {
        return success(salaryRuleService.createSalaryRule(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新薪资规则配置")
    @PreAuthorize("@ss.hasPermission('information:salary-rule:update')")
    public CommonResult<Boolean> updateSalaryRule(@Valid @RequestBody SalaryRuleSaveReqVO updateReqVO) {
        salaryRuleService.updateSalaryRule(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除薪资规则配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:salary-rule:delete')")
    public CommonResult<Boolean> deleteSalaryRule(@RequestParam("id") Long id) {
        salaryRuleService.deleteSalaryRule(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得薪资规则配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:salary-rule:query')")
    public CommonResult<SalaryRuleRespVO> getSalaryRule(@RequestParam("id") Long id) {
        SalaryRuleRespVO salaryRule = salaryRuleService.getSalaryRule(id);
        return success(salaryRule);
    }

    @GetMapping("/list")
    @Operation(summary = "获得薪资规则配置列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:salary-rule:query')")
    public CommonResult<List<SalaryRuleRespVO>> getSalaryRuleList(@RequestParam("ids") Collection<Long> ids) {
        List<SalaryRuleDO> list = salaryRuleService.getSalaryRuleList(ids);
        return success(BeanUtils.toBean(list, SalaryRuleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得薪资规则配置分页")
    @PreAuthorize("@ss.hasPermission('information:salary-rule:query')")
    public CommonResult<PageResult<SalaryRuleRespVO>> getSalaryRulePage(@Valid SalaryRulePageReqVO pageReqVO) {
        PageResult<SalaryRuleRespVO> pageResult = salaryRuleService.getSalaryRulePage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出薪资规则配置 Excel")
    @PreAuthorize("@ss.hasPermission('information:salary-rule:export')")
    @OperateLog(type = EXPORT)
    public void exportSalaryRuleExcel(@Valid SalaryRuleListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<SalaryRuleExcelVO> list = salaryRuleService.getSalaryRuleList(exportReqVO);
        ExcelUtils.write(response, "薪资规则配置.xls", "数据", SalaryRuleExcelVO.class, list, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新薪资规则配置列表")
    @PreAuthorize("@ss.hasPermission('information:salary-rule:batch-update')")
    public CommonResult<Boolean> batchUpdateSalaryRule(@Valid @RequestBody List<SalaryRuleSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        salaryRuleService.batchUpdateSalaryRule(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除薪资规则配置列表")
    @PreAuthorize("@ss.hasPermission('information:salary-rule:batch-delete')")
    public CommonResult<Boolean> batchDeleteSalaryRule(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        salaryRuleService.batchDeleteSalaryRule(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载薪资规则配置导入模板")
    @PreAuthorize("@ss.hasPermission('information:salary-rule:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<SalaryRuleExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "薪资规则配置模板.xls", "薪资规则配置列表", SalaryRuleExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "薪资规则配置预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:salary-rule:import')")
    public CommonResult<List<SalaryRuleExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<SalaryRuleExcelVO> list = ExcelUtils.read(file, SalaryRuleExcelVO.class);
    return success(salaryRuleService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入薪资规则配置")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:salary-rule:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody SalaryRuleExcelVO importReqVo) throws Exception {
        ImportResult result = salaryRuleService.importData(importReqVo);
        return success(result);
    }

    @PostMapping("/assignToDept")
    @Operation(summary = "将薪资规则分配到组织下")
    @PreAuthorize("@ss.hasPermission('information:salaryRule:assignToDept')")
    public CommonResult<Void> assignToDept(@Valid @RequestBody SalaryRuleDeptReqVO salaryRuleDeptReqVO) {
        salaryRuleService.assignSalaryRuleToDept(salaryRuleDeptReqVO);
        return success(null);
    }
}