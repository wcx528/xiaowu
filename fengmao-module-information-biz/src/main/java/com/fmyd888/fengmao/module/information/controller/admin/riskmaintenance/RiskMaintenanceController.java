package com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance;

import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto.RiskInspectionItemOuterDTO;
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

import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;

import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;

import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskMaintenanceDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskInspectionItemDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskMaintenanceCommodityDO;
import com.fmyd888.fengmao.module.information.service.riskmaintenance.RiskMaintenanceService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 隐患排查项目维护表(主表)")
@RestController
@RequestMapping("/information/risk-maintenance")
@Validated
public class RiskMaintenanceController {

    @Resource
    private RiskMaintenanceService riskMaintenanceService;

    @PostMapping("/create")
    @Operation(summary = "创建隐患排查项目维护表(主表)")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:create')")
    public CommonResult<Long> createRiskMaintenance(@Valid @RequestBody RiskMaintenanceSaveReqVO createReqVO) {
        return success(riskMaintenanceService.createRiskMaintenance(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新隐患排查项目维护表(主表)")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:update')")
    public CommonResult<Boolean> updateRiskMaintenance(@Valid @RequestBody RiskMaintenanceSaveReqVO updateReqVO) {
        riskMaintenanceService.updateRiskMaintenance(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除隐患排查项目维护表(主表)")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:delete')")
    public CommonResult<Boolean> deleteRiskMaintenance(@RequestParam("id") Long id) {
        riskMaintenanceService.deleteRiskMaintenance(id);
        return success(true);
    }



    @GetMapping("/getRiskMaintenance")
    @Operation(summary = "获得隐患排查项目维护表详情")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:query2')")
    public CommonResult<RiskInspectionItemOuterDTO> selectRiskInspectionItemById(@RequestParam("companyId") Long companyId, @RequestParam("checkType")Integer checkType, @RequestParam("type")Integer type) {
        RiskInspectionItemOuterDTO riskInspectionItemOuterDTO = riskMaintenanceService.selectRiskInspectionItemById(companyId, checkType, type);
        return success(riskInspectionItemOuterDTO);
    }

    @GetMapping("/get")
    @Operation(summary = "获得隐患排查项目维护表(主表)")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:query')")
    public CommonResult<RiskMaintenanceRespVO> getRiskMaintenance(@RequestParam("id") Long id) {
            RiskMaintenanceDO riskMaintenance = riskMaintenanceService.getRiskMaintenance(id);
            return success(BeanUtils.toBean(riskMaintenance, RiskMaintenanceRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得隐患排查项目维护表(主表)列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:query')")
    public CommonResult<List<RiskMaintenanceRespVO>> getRiskMaintenanceList(@RequestParam("ids") Collection<Long> ids) {
        List<RiskMaintenanceDO> list = riskMaintenanceService.getRiskMaintenanceList(ids);
        return success(BeanUtils.toBean(list, RiskMaintenanceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得隐患排查项目维护表(主表)分页")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:query')")
    public CommonResult<PageResult<RiskMaintenanceRespVO>> getRiskMaintenancePage(@Valid RiskMaintenancePageReqVO pageReqVO) {
        PageResult<RiskMaintenanceDO> pageResult = riskMaintenanceService.getRiskMaintenancePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RiskMaintenanceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出隐患排查项目维护表(主表) Excel")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:export')")
    @OperateLog(type = EXPORT)
    public void exportRiskMaintenanceExcel(@Valid RiskMaintenanceListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<RiskMaintenanceDO> list = riskMaintenanceService.getRiskMaintenanceList(exportReqVO);
        // 导出 Excel
        List<RiskMaintenanceExcelVO> excelVo = BeanUtils.toBean(list, RiskMaintenanceExcelVO.class);
        ExcelUtils.write(response, "隐患排查项目维护表(主表).xls", "数据", RiskMaintenanceExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新隐患排查项目维护表(主表)列表")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:batch-update')")
    public CommonResult<Boolean> batchUpdateRiskMaintenance(@Valid @RequestBody List<RiskMaintenanceSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        riskMaintenanceService.batchUpdateRiskMaintenance(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除隐患排查项目维护表(主表)列表")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:batch-delete')")
    public CommonResult<Boolean> batchDeleteRiskMaintenance(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        riskMaintenanceService.batchDeleteRiskMaintenance(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载隐患排查项目维护表(主表)导入模板")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<RiskMaintenanceExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "隐患排查项目维护表(主表)模板.xls", "隐患排查项目维护表(主表)列表", RiskMaintenanceExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "隐患排查项目维护表(主表)预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:import')")
    public CommonResult<List<RiskMaintenanceExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<RiskMaintenanceExcelVO> list = ExcelUtils.read(file, RiskMaintenanceExcelVO.class);
    return success(riskMaintenanceService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入隐患排查项目维护表(主表)")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody RiskMaintenanceExcelVO importReqVo) throws Exception {
        ImportResult result = riskMaintenanceService.importData(importReqVo);
        return success(result);
    }

    @GetMapping("/getRiskMaintenanceSimpleList")
    @Operation(summary = "隐患排查精简接口")
    public CommonResult<RiskInspectionItemOuterDTO> getRiskMaintenanceSimpleList(@RequestParam("checkType") Integer checkType,
                                                                                 @RequestParam("companyId")Long companyId) {
        RiskInspectionItemOuterDTO riskMaintenanceSimpleList = riskMaintenanceService.getRiskMaintenanceSimpleList(checkType,companyId);
        return success(riskMaintenanceSimpleList);
    }

// ==================== 子表（检查类型表(子表)） ====================

    @GetMapping("/risk-inspection-item/page")
    @Operation(summary = "获得检查类型表(子表)分页")
    @Parameter(name = "entityId", description = "主表ID")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:query')")
    public CommonResult<PageResult<RiskInspectionItemDO>> getRiskInspectionItemPage(PageParam pageReqVO,
    @RequestParam("entityId") Long entityId) {
        return success(riskMaintenanceService.getRiskInspectionItemPage(pageReqVO, entityId));
    }

        @GetMapping("/risk-inspection-item/list-by-entity-id")
    @Operation(summary = "获得检查类型表(子表)列表")
    @Parameter(name = "entityId", description = "主表ID")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:query')")
    public CommonResult<List<RiskInspectionItemDO>> getRiskInspectionItemListByEntityId(@RequestParam("entityId") Long entityId) {
        return success(riskMaintenanceService.getRiskInspectionItemListByEntityId(entityId));
    }

// ==================== 子表（隐患排查项目维护与介质关联） ====================

    @GetMapping("/risk-maintenance-commodity/page")
    @Operation(summary = "获得隐患排查项目维护与介质关联分页")
    @Parameter(name = "entityId", description = "主表ID")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:query')")
    public CommonResult<PageResult<RiskMaintenanceCommodityDO>> getRiskMaintenanceCommodityPage(PageParam pageReqVO,
    @RequestParam("entityId") Long entityId) {
        return success(riskMaintenanceService.getRiskMaintenanceCommodityPage(pageReqVO, entityId));
    }

        @GetMapping("/risk-maintenance-commodity/list-by-entity-id")
    @Operation(summary = "获得隐患排查项目维护与介质关联列表")
    @Parameter(name = "entityId", description = "主表ID")
    @PreAuthorize("@ss.hasPermission('information:risk-maintenance:query')")
    public CommonResult<List<RiskMaintenanceCommodityDO>> getRiskMaintenanceCommodityListByEntityId(@RequestParam("entityId") Long entityId) {
        return success(riskMaintenanceService.getRiskMaintenanceCommodityListByEntityId(entityId));
    }

}
