package com.fmyd888.fengmao.module.information.controller.admin.maintenancecoefficients;

import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.*;

import javax.validation.constraints.*;
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

import com.fmyd888.fengmao.module.information.controller.admin.maintenancecoefficients.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.maintenancecoefficients.MaintenanceCoefficientsDO;
import com.fmyd888.fengmao.module.information.service.maintenancecoefficients.MaintenanceCoefficientsService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 保养系数维护")
@RestController
@RequestMapping("/information/maintenance-coefficients")
@Validated
public class MaintenanceCoefficientsController {

    @Resource
    private MaintenanceCoefficientsService maintenanceCoefficientsService;

    @PostMapping("/create")
    @Operation(summary = "创建保养系数维护")
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:create')")
    public CommonResult<Long> createMaintenanceCoefficients(@Valid @RequestBody MaintenanceCoefficientsSaveReqVO createReqVO) {
        return success(maintenanceCoefficientsService.createMaintenanceCoefficients(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新保养系数维护")
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:update')")
    public CommonResult<Boolean> updateMaintenanceCoefficients(@Valid @RequestBody MaintenanceCoefficientsSaveReqVO updateReqVO) {
        maintenanceCoefficientsService.updateMaintenanceCoefficients(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除保养系数维护")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:delete')")
    public CommonResult<Boolean> deleteMaintenanceCoefficients(@RequestParam("id") Long id) {
        maintenanceCoefficientsService.deleteMaintenanceCoefficients(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得保养系数维护")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:query')")
    public CommonResult<MaintenanceCoefficientsRespVO> getMaintenanceCoefficients(@RequestParam("id") Long id) {
            MaintenanceCoefficientsDO maintenanceCoefficients = maintenanceCoefficientsService.getMaintenanceCoefficients(id);
            return success(BeanUtils.toBean(maintenanceCoefficients, MaintenanceCoefficientsRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得保养系数维护列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:query')")
    public CommonResult<List<MaintenanceCoefficientsRespVO>> getMaintenanceCoefficientsList(@RequestParam("ids") Collection<Long> ids) {
        List<MaintenanceCoefficientsDO> list = maintenanceCoefficientsService.getMaintenanceCoefficientsList(ids);
        return success(BeanUtils.toBean(list, MaintenanceCoefficientsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得保养系数维护分页")
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:query')")
    public CommonResult<PageResult<MaintenanceCoefficientsRespVO>> getMaintenanceCoefficientsPage(@Valid MaintenanceCoefficientsPageReqVO pageReqVO) {
        PageResult<MaintenanceCoefficientsDO> pageResult = maintenanceCoefficientsService.getMaintenanceCoefficientsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MaintenanceCoefficientsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出保养系数维护 Excel")
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:export')")
    @OperateLog(type = EXPORT)
    public void exportMaintenanceCoefficientsExcel(@Valid MaintenanceCoefficientsListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<MaintenanceCoefficientsDO> list = maintenanceCoefficientsService.getMaintenanceCoefficientsList(exportReqVO);
        // 导出 Excel
        List<MaintenanceCoefficientsExcelVO> excelVo = BeanUtils.toBean(list, MaintenanceCoefficientsExcelVO.class);
        ExcelUtils.write(response, "保养系数维护.xls", "数据", MaintenanceCoefficientsExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新保养系数维护列表")
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:batch-update')")
    public CommonResult<Boolean> batchUpdateMaintenanceCoefficients(@Valid @RequestBody List<MaintenanceCoefficientsSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        maintenanceCoefficientsService.batchUpdateMaintenanceCoefficients(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除保养系数维护列表")
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:batch-delete')")
    public CommonResult<Boolean> batchDeleteMaintenanceCoefficients(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        maintenanceCoefficientsService.batchDeleteMaintenanceCoefficients(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载保养系数维护导入模板")
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<MaintenanceCoefficientsExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "保养系数维护模板.xls", "保养系数维护列表", MaintenanceCoefficientsExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "保养系数维护预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:import')")
    public CommonResult<List<MaintenanceCoefficientsExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<MaintenanceCoefficientsExcelVO> list = ExcelUtils.read(file, MaintenanceCoefficientsExcelVO.class);
    return success(maintenanceCoefficientsService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入保养系数维护")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:maintenance-coefficients:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody MaintenanceCoefficientsExcelVO importReqVo) throws Exception {
        ImportResult result = maintenanceCoefficientsService.importData(importReqVo);
        return success(result);
    }

}