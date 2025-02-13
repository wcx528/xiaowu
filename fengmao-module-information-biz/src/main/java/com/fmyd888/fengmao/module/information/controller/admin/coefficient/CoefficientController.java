package com.fmyd888.fengmao.module.information.controller.admin.coefficient;

import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.CoefficientDetailDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.LoadingRateDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MaintenanceCostsDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MileageAccountingDO;
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

import com.fmyd888.fengmao.module.information.controller.admin.coefficient.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.CoefficientDO;

import com.fmyd888.fengmao.module.information.service.coefficient.CoefficientService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 系数维护")
@RestController
@RequestMapping("/information/coefficient")
@Validated
public class CoefficientController {

    @Resource
    private CoefficientService coefficientService;

    @PostMapping("/create")
    @Operation(summary = "创建系数维护")
    @PreAuthorize("@ss.hasPermission('information:coefficient:create')")
    public CommonResult<Long> createCoefficient(@Valid @RequestBody CoefficientSaveReqVO createReqVO) {
        return success(coefficientService.createCoefficient(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新系数维护")
    @PreAuthorize("@ss.hasPermission('information:coefficient:update')")
    public CommonResult<Boolean> updateCoefficient(@Valid @RequestBody CoefficientSaveReqVO updateReqVO) {
        coefficientService.updateCoefficient(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除系数维护")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:coefficient:delete')")
    public CommonResult<Boolean> deleteCoefficient(@RequestParam("id") Long id) {
        coefficientService.deleteCoefficient(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得系数维护")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:coefficient:query')")
    public CommonResult<CoefficientRespVO> getCoefficient(@RequestParam("id") Long id) {
            CoefficientDO coefficient = coefficientService.getCoefficient(id);
            return success(BeanUtils.toBean(coefficient, CoefficientRespVO.class));
    }

    @GetMapping("/coefficientDetails")
    @Operation(summary = "获得系数维护详情")
//    @Parameter(name = "id", description = "编号", required = false, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:coefficient:query')")
    public CommonResult<CoefficientDetailDTO> coefficientDetails(@RequestParam("subordinateCompaniesId") Long subordinateCompaniesId) {
        CoefficientDetailDTO coefficient2 = coefficientService.coefficientDetails(subordinateCompaniesId);
        return success(coefficient2);
    }

    @GetMapping("/list")
    @Operation(summary = "获得系数维护列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:coefficient:query')")
    public CommonResult<List<CoefficientRespVO>> getCoefficientList(@RequestParam("ids") Collection<Long> ids) {
        List<CoefficientDO> list = coefficientService.getCoefficientList(ids);
        return success(BeanUtils.toBean(list, CoefficientRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得系数维护分页")
    @PreAuthorize("@ss.hasPermission('information:coefficient:query')")
    public CommonResult<PageResult<CoefficientRespVO>> getCoefficientPage(@Valid CoefficientPageReqVO pageReqVO) {
        PageResult<CoefficientDO> pageResult = coefficientService.getCoefficientPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CoefficientRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出系数维护 Excel")
    @PreAuthorize("@ss.hasPermission('information:coefficient:export')")
    @OperateLog(type = EXPORT)
    public void exportCoefficientExcel(@Valid CoefficientListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<CoefficientDO> list = coefficientService.getCoefficientList(exportReqVO);
        // 导出 Excel
        List<CoefficientExcelVO> excelVo = BeanUtils.toBean(list, CoefficientExcelVO.class);
        ExcelUtils.write(response, "系数维护.xls", "数据", CoefficientExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新系数维护列表")
    @PreAuthorize("@ss.hasPermission('information:coefficient:batch-update')")
    public CommonResult<Boolean> batchUpdateCoefficient(@Valid @RequestBody List<CoefficientSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        coefficientService.batchUpdateCoefficient(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除系数维护列表")
    @PreAuthorize("@ss.hasPermission('information:coefficient:batch-delete')")
    public CommonResult<Boolean> batchDeleteCoefficient(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        coefficientService.batchDeleteCoefficient(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载系数维护导入模板")
    @PreAuthorize("@ss.hasPermission('information:coefficient:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<CoefficientExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "系数维护模板.xls", "系数维护列表", CoefficientExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "系数维护预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:coefficient:import')")
    public CommonResult<List<CoefficientExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<CoefficientExcelVO> list = ExcelUtils.read(file, CoefficientExcelVO.class);
    return success(coefficientService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入系数维护")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:coefficient:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody CoefficientExcelVO importReqVo) throws Exception {
        ImportResult result = coefficientService.importData(importReqVo);
        return success(result);
    }

// ==================== 子表（装载率系数明细） ====================

    @GetMapping("/loading-rate/page")
    @Operation(summary = "获得装载率系数明细分页")
    @Parameter(name = "coefficientId", description = "系数维护表id")
    @PreAuthorize("@ss.hasPermission('information:coefficient:query')")
    public CommonResult<PageResult<LoadingRateDO>> getLoadingRatePage(PageParam pageReqVO,
                                                                      @RequestParam("coefficientId") Long coefficientId) {
        return success(coefficientService.getLoadingRatePage(pageReqVO, coefficientId));
    }

        @GetMapping("/loading-rate/list-by-coefficient-id")
    @Operation(summary = "获得装载率系数明细列表")
    @Parameter(name = "coefficientId", description = "系数维护表id")
    @PreAuthorize("@ss.hasPermission('information:coefficient:query')")
    public CommonResult<List<LoadingRateDO>> getLoadingRateListByCoefficientId(@RequestParam("coefficientId") Long coefficientId) {
        return success(coefficientService.getLoadingRateListByCoefficientId(coefficientId));
    }

// ==================== 子表（工资里程核算系数明细） ====================

    @GetMapping("/mileage-accounting/page")
    @Operation(summary = "获得工资里程核算系数明细分页")
    @Parameter(name = "coefficientId", description = "系数维护表id")
    @PreAuthorize("@ss.hasPermission('information:coefficient:query')")
    public CommonResult<PageResult<MileageAccountingDO>> getMileageAccountingPage(PageParam pageReqVO,
                                                                                  @RequestParam("coefficientId") Long coefficientId) {
        return success(coefficientService.getMileageAccountingPage(pageReqVO, coefficientId));
    }

        @GetMapping("/mileage-accounting/list-by-coefficient-id")
    @Operation(summary = "获得工资里程核算系数明细列表")
    @Parameter(name = "coefficientId", description = "系数维护表id")
    @PreAuthorize("@ss.hasPermission('information:coefficient:query')")
    public CommonResult<List<MileageAccountingDO>> getMileageAccountingListByCoefficientId(@RequestParam("coefficientId") Long coefficientId) {
        return success(coefficientService.getMileageAccountingListByCoefficientId(coefficientId));
    }

// ==================== 子表（维修费用系数维护明细） ====================

    @GetMapping("/maintenance-costs/page")
    @Operation(summary = "获得维修费用系数维护明细分页")
    @Parameter(name = "coefficientId", description = "系数维护表id")
    @PreAuthorize("@ss.hasPermission('information:coefficient:query')")
    public CommonResult<PageResult<MaintenanceCostsDO>> getMaintenanceCostsPage(PageParam pageReqVO,
                                                                                @RequestParam("coefficientId") Long coefficientId) {
        return success(coefficientService.getMaintenanceCostsPage(pageReqVO, coefficientId));
    }

        @GetMapping("/maintenance-costs/list-by-coefficient-id")
    @Operation(summary = "获得维修费用系数维护明细列表")
    @Parameter(name = "coefficientId", description = "系数维护表id")
    @PreAuthorize("@ss.hasPermission('information:coefficient:query')")
    public CommonResult<List<MaintenanceCostsDO>> getMaintenanceCostsListByCoefficientId(@RequestParam("coefficientId") Long coefficientId) {
        return success(coefficientService.getMaintenanceCostsListByCoefficientId(coefficientId));
    }

}
