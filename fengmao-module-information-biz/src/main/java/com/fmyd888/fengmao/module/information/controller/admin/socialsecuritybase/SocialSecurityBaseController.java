package com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerDeptReqVO;
import com.fmyd888.fengmao.module.information.service.socialsecuritybase.SocialSecurityBaseDeptService;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.*;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;

import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybase.SocialSecurityBaseDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybasedept.SocialSecurityBaseDeptDO;
import com.fmyd888.fengmao.module.information.service.socialsecuritybase.SocialSecurityBaseService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 社保基数维护")
@RestController
@RequestMapping("/information/social-security-base")
@Validated
public class SocialSecurityBaseController {

    @Resource
    private SocialSecurityBaseService socialSecurityBaseService;

    @Resource
    private SocialSecurityBaseDeptService socialSecurityBaseDeptService;

    @Resource
    private DeptService deptService;

    @PostMapping("/create")
    @Operation(summary = "创建社保基数维护")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:create')")
    public CommonResult<Long> createSocialSecurityBase(@Valid @RequestBody SocialSecurityBaseSaveReqVO createReqVO) {
        return success(socialSecurityBaseService.createSocialSecurityBase(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新社保基数维护")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:update')")
    public CommonResult<Boolean> updateSocialSecurityBase(@Valid @RequestBody SocialSecurityBaseSaveReqVO updateReqVO) {
        socialSecurityBaseService.updateSocialSecurityBase(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除社保基数维护")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:social-security-base:delete')")
    public CommonResult<Boolean> deleteSocialSecurityBase(@RequestParam("id") Long id) {
        socialSecurityBaseService.deleteSocialSecurityBase(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得社保基数维护")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:query')")
    public CommonResult<SocialSecurityBaseRespVO> getSocialSecurityBase(@RequestParam("id") Long id) {
        SocialSecurityBaseRespVO socialSecurityBase = socialSecurityBaseService.getSocialSecurityBase(id);
        return success(socialSecurityBase);
    }

    @GetMapping("/list")
    @Operation(summary = "获得社保基数维护列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:query')")
    public CommonResult<List<SocialSecurityBaseRespVO>> getSocialSecurityBaseList(@RequestParam("ids") Collection<Long> ids) {
        List<SocialSecurityBaseDO> list = socialSecurityBaseService.getSocialSecurityBaseList(ids);
        return success(BeanUtils.toBean(list, SocialSecurityBaseRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得社保基数维护关键字条件查询分页")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:query')")
    public CommonResult<ClientSettingsPage<SocialSecurityBaseRespVO>> getSocialSecurityBasePage(@Valid SocialSecurityBasePageReqVO pageReqVO) {
        ClientSettingsPage<SocialSecurityBaseRespVO> socialSecurityBasePage = socialSecurityBaseService.getSocialSecurityBasePage(pageReqVO);
        return success(socialSecurityBasePage);
    }

    @GetMapping("/page2")
    @Operation(summary = "获得社保基数维护分页")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:query')")
    public CommonResult<PageResult<SocialSecurityBaseRespVO>> getSocialSecurityBasePage2(@Valid SocialSecurityBasePageReqVO pageReqVO) {
        PageResult<SocialSecurityBaseRespVO> socialSecurityBasePage2 = socialSecurityBaseService.getSocialSecurityBasePage2(pageReqVO);
        return success(socialSecurityBasePage2);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出社保基数维护 Excel")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:export')")
    @OperateLog(type = EXPORT)
    public void exportSocialSecurityBaseExcel(@Valid SocialSecurityBaseListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<SocialSecurityBaseDO> list = socialSecurityBaseService.getSocialSecurityBaseList(exportReqVO);
        // 导出 Excel
        List<SocialSecurityBaseExcelVO> excelVo = BeanUtils.toBean(list, SocialSecurityBaseExcelVO.class);
        ExcelUtils.write(response, "社保基数维护.xls", "数据", SocialSecurityBaseExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新社保基数维护列表")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:batch-update')")
    public CommonResult<Boolean> batchUpdateSocialSecurityBase(@Valid @RequestBody List<SocialSecurityBaseSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        socialSecurityBaseService.batchUpdateSocialSecurityBase(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除社保基数维护列表")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:batch-delete')")
    public CommonResult<Boolean> batchDeleteSocialSecurityBase(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        socialSecurityBaseService.batchDeleteSocialSecurityBase(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载社保基数维护导入模板")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<SocialSecurityBaseExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "社保基数维护模板.xls", "社保基数维护列表", SocialSecurityBaseExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "社保基数维护预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:social-security-base:import')")
    public CommonResult<List<SocialSecurityBaseExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<SocialSecurityBaseExcelVO> list = ExcelUtils.read(file, SocialSecurityBaseExcelVO.class);
    return success(socialSecurityBaseService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入社保基数维护")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:social-security-base:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody SocialSecurityBaseExcelVO importReqVo) throws Exception {
        ImportResult result = socialSecurityBaseService.importData(importReqVo);
        return success(result);
    }

// ==================== 子表（社保基数维护表和部门组织） ====================

    @GetMapping("/social-security-base-dept/page")
    @Operation(summary = "获得社保基数维护表和部门组织分页")
    @Parameter(name = "entityId", description = "主表id(社保基数维护表id)")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:query')")
    public CommonResult<PageResult<SocialSecurityBaseDeptDO>> getSocialSecurityBaseDeptPage(PageParam pageReqVO,
    @RequestParam("entityId") Long entityId) {
        return success(socialSecurityBaseService.getSocialSecurityBaseDeptPage(pageReqVO, entityId));
    }

        @GetMapping("/social-security-base-dept/list-by-entity-id")
    @Operation(summary = "获得社保基数维护表和部门组织列表")
    @Parameter(name = "entityId", description = "主表id(社保基数维护表id)")
    @PreAuthorize("@ss.hasPermission('information:social-security-base:query')")
    public CommonResult<List<DeptRespVO>> getSocialSecurityBaseDeptListByEntityId(@RequestParam("entityId") Long entityId) {
       List<SocialSecurityBaseDeptDO> socialSecurityBaseDeptList = socialSecurityBaseDeptService.getSocialSecurityBaseDeptListByEntityId(entityId);
       List<Long> collect = socialSecurityBaseDeptList.stream().map(SocialSecurityBaseDeptDO::getDeptId).collect(Collectors.toList());
       List<DeptDO> deptList = deptService.getDeptList(collect);
       List<DeptRespVO> deptRespVOS = BeanUtils.toBean(deptList, DeptRespVO.class);
       return success(deptRespVOS);
    }

    @PostMapping("/assignToDept")
    @Operation(summary = "将社保基数分配到组织下")
    public CommonResult<Void> assignToDept(@Valid @RequestBody SocialSecurityBaseDeptSaveReqVO updateReqVO) {
        socialSecurityBaseDeptService.assignSocialSecurityBaseToDept(updateReqVO);
        return success(null);
    }

}
