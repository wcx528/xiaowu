package com.fmyd888.fengmao.module.information.controller.admin.measurement;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.CommonResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.excel.core.util.ExcelUtils;
import com.fmyd888.fengmao.framework.operatelog.core.annotations.OperateLog;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.*;
import com.fmyd888.fengmao.module.information.convert.measurement.MeasurementConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import com.fmyd888.fengmao.module.information.service.measurement.MeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.pojo.CommonResult.success;
import static com.fmyd888.fengmao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 计量单位表")
@Slf4j
@RestController
@RequestMapping("/information/measurement")
@Validated
public class MeasurementController {

    @Resource
    private MeasurementService measurementService;


    @PostMapping("/create")
    @Operation(summary = "创建计量单位表")
    @PreAuthorize("@ss.hasPermission('information:measurement:create')")
    public CommonResult<Long> createMeasurement(@Valid @RequestBody MeasurementCreateReqVO createReqVO) {
        Long measurement = measurementService.createMeasurement(createReqVO);
        CommonResult<Long> result = success(measurement);
        return result;
    }

    @PutMapping("/update")
    @Operation(summary = "更新计量单位表")
    @PreAuthorize("@ss.hasPermission('information:measurement:update')")
    public CommonResult<Boolean> updateMeasurement(@Valid @RequestBody MeasurementUpdateReqVO updateReqVO) {
        measurementService.updateMeasurement(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除计量单位表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:measurement:delete')")
    public CommonResult<Boolean> deleteMeasurement(@RequestParam("id") Long id) {
        measurementService.deleteMeasurement(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得计量单位表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:measurement:query')")
    public CommonResult<MeasurementRespVO> getMeasurement(@RequestParam("id") Long id) {
        MeasurementRespVO measurement = measurementService.getMeasurement(id);
        CommonResult<MeasurementRespVO> result = success(measurement);
        return result;
    }

    @GetMapping("/list")
    @Operation(summary = "获得计量单位表列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:measurement:query')")
    public CommonResult<List<MeasurementRespVO>> getMeasurementList(@RequestParam("ids") Collection<Long> ids) {
        List<MeasurementRespVO> measurementList = measurementService.getMeasurementList(ids);
        CommonResult<List<MeasurementRespVO>> result = success(measurementList);
        return result;
    }

    @GetMapping("/page")
    @Operation(summary = "获得计量单位表分页")
    @PreAuthorize("@ss.hasPermission('information:measurement:query')")
    public CommonResult<PageResult<MeasurementRespVO>> getMeasurementPage(@Valid MeasurementPageReqVO pageVO) {
        PageResult<MeasurementRespVO> pageResult = measurementService.getMeasurementPage(pageVO);
        return  success(pageResult);
    }

    @GetMapping("/getMeasurementPage")
    @Operation(summary = "计量单位导出字段选择")
    @PreAuthorize("@ss.hasPermission('information:store:query')")
    public CommonResult<List<HashMap<String, Object>>> getStorePage() {
        List<HashMap<String, Object>> exportList = measurementService.getExportList();
        return success(exportList);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出计量单位表 Excel")
    @PreAuthorize("@ss.hasPermission('information:measurement:export')")
    @OperateLog(type = EXPORT)
    public void exportMeasurementExcel(@Valid MeasurementExportReqVO exportReqVO,
                                       HttpServletResponse response) throws IOException {
        List<MeasurementExcelVO> list = measurementService.getMeasurementList(exportReqVO);
        ExcelUtils.write2(response, "计量单位表.xls", "数据", MeasurementExcelVO.class, list, exportReqVO.getExportFileds());
    }

    @GetMapping("/updateMeasurementStatus")
    @Operation(summary = "更新税率状态")
    public CommonResult<Void> updateMeasurementStatus(@Param("id") Long id) {
        measurementService.updateStatus(id);
        return success(null);
    }

    @PostMapping("/assignToDept")
    @Operation(summary = "将仓库分配到组织下")
    public CommonResult<Void> assignToDept(@Valid @RequestBody MeasurementDeptReqVO measurementDeptReqVO) {
        measurementService.assignMeasurementToDept(measurementDeptReqVO);
        return success(null);
    }

    @GetMapping("/getNameList")
    @Operation(summary = "获得计量单位name详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<List<MeasurementRespVO>> getNameList() {
        List<MeasurementDO> measurementDOList = measurementService.getNameListById();
        List<MeasurementRespVO> measurementRespVOS = measurementDOList.stream()
                .map(MeasurementConvert.INSTANCE::convert)
                .collect(Collectors.toList());
        return CommonResult.success(measurementRespVOS);
    }

    @GetMapping("/getSimpleMeasurementList")
    @Operation(summary = "获得计量单位精简信息")
    public CommonResult<List<HashMap<String, Object>>> getSimpleMeasurementList(@Valid CommonQueryParam param) {
        List<HashMap<String, Object>> list = measurementService.getSimpleMeasurementList(param);
        return success(list);
    }

}
