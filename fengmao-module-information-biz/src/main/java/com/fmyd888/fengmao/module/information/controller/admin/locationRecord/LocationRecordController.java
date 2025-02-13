package com.fmyd888.fengmao.module.information.controller.admin.locationRecord;

import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LatestLocationRecordDO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.*;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.math.BigDecimal;
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

import com.fmyd888.fengmao.module.information.controller.admin.locationRecord.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord.LocationRecordDO;
import com.fmyd888.fengmao.module.information.service.locationRecord.LocationRecordService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 车辆GPS定位")
@RestController
@RequestMapping("/information/location-record")
@Validated
public class LocationRecordController {

    @Resource
    private LocationRecordService locationRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建车辆GPS定位")
    @PreAuthorize("@ss.hasPermission('information:location-record:create')")
    public CommonResult<Long> createLocationRecord(@Valid @RequestBody LocationRecordSaveReqVO createReqVO) {
        return success(locationRecordService.createLocationRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新车辆GPS定位")
    @PreAuthorize("@ss.hasPermission('information:location-record:update')")
    public CommonResult<Boolean> updateLocationRecord(@Valid @RequestBody LocationRecordSaveReqVO updateReqVO) {
        locationRecordService.updateLocationRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除车辆GPS定位")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('information:location-record:delete')")
    public CommonResult<Boolean> deleteLocationRecord(@RequestParam("id") Long id) {
        locationRecordService.deleteLocationRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得车辆GPS定位")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:location-record:query')")
    public CommonResult<LocationRecordRespVO> getLocationRecord(@RequestParam("id") Long id) {
            LocationRecordDO locationRecord = locationRecordService.getLocationRecord(id);
            return success(BeanUtils.toBean(locationRecord, LocationRecordRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得车辆GPS定位列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('information:location-record:query')")
    public CommonResult<List<LocationRecordRespVO>> getLocationRecordList(@RequestParam("ids") Collection<Long> ids) {
        List<LocationRecordDO> list = locationRecordService.getLocationRecordList(ids);
        return success(BeanUtils.toBean(list, LocationRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得车辆GPS定位分页")
    @PreAuthorize("@ss.hasPermission('information:location-record:query')")
    public CommonResult<PageResult<LatestLocationRecordDO>> getLocationRecordPage(@Valid LocationRecordPageReqVO pageReqVO) {
        PageResult<LatestLocationRecordDO> pageResult = locationRecordService.getLocationRecordPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出车辆GPS定位 Excel")
    @PreAuthorize("@ss.hasPermission('information:location-record:export')")
    @OperateLog(type = EXPORT)
    public void exportLocationRecordExcel(@Valid LocationRecordListReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<LocationRecordDO> list = locationRecordService.getLocationRecordList(exportReqVO);
        // 导出 Excel
        List<LocationRecordExcelVO> excelVo = BeanUtils.toBean(list, LocationRecordExcelVO.class);
        ExcelUtils.write(response, "车辆GPS定位.xls", "数据", LocationRecordExcelVO.class, excelVo, exportReqVO.getExportFileds());
    }

    @PutMapping("/batch-update")
    @Operation(summary = "批量更新车辆GPS定位列表")
    @PreAuthorize("@ss.hasPermission('information:location-record:batch-update')")
    public CommonResult<Boolean> batchUpdateLocationRecord(@Valid @RequestBody List<LocationRecordSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        locationRecordService.batchUpdateLocationRecord(updateReqVOList);
        return success(true);
    }

    @DeleteMapping("/batch-delete")
    @Operation(summary = "批量删除车辆GPS定位列表")
    @PreAuthorize("@ss.hasPermission('information:location-record:batch-delete')")
    public CommonResult<Boolean> batchDeleteLocationRecord(@RequestBody Map<String, List<Long>> body) {
        // 在这里处理批量删除逻辑
        locationRecordService.batchDeleteLocationRecord(body.get("ids"));
        return success(true);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "下载车辆GPS定位导入模板")
    @PreAuthorize("@ss.hasPermission('information:location-record:import')")
    public void importTemplate(HttpServletResponse response) throws IOException {
    // 手动创建导出 demo
    List<LocationRecordExcelVO> list = Arrays.asList();

    // 输出
    ExcelUtils.write(response, "车辆GPS定位模板.xls", "车辆GPS定位列表", LocationRecordExcelVO.class, list);
    }

    @PostMapping("/importPreview")
    @Operation(summary = "车辆GPS定位预览")
    @Parameters({
    @Parameter(name = "file", description = "Excel 文件", required = true),
    @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('information:location-record:import')")
    public CommonResult<List<LocationRecordExcelVO>> importPreview(@RequestParam("file") MultipartFile file,
    @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
    List<LocationRecordExcelVO> list = ExcelUtils.read(file, LocationRecordExcelVO.class);
    return success(locationRecordService.importPreviewList(list, updateSupport));
    }

    @PostMapping("/import")
    @Operation(summary = "导入车辆GPS定位")
    @Parameters({
    @Parameter(name = "importReqVo", description = "导入的excel数据", required = true),
    })
    @PreAuthorize("@ss.hasPermission('information:location-record:import')")
    public CommonResult<ImportResult> importExcel(@RequestBody LocationRecordExcelVO importReqVo) throws Exception {
        ImportResult result = locationRecordService.importData(importReqVo);
        return success(result);
    }

    @GetMapping("/get-car-location")
    @Operation(summary = "查询车辆当前位置")
    @Parameter(name = "carId", description = "车辆id", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('information:location-record:query')")
        public CommonResult<LocationRecordRespVO> getCarLocation(@RequestParam("carId") Long carId) {
        LocationRecordRespVO locationRecord = locationRecordService.getCarLocation(carId);
        return success(locationRecord);
    }

    @GetMapping("/get-car-trajectory")
    @Operation(summary = "查询车辆轨迹")
    @PreAuthorize("@ss.hasPermission('information:location-record:query')")
    public CommonResult<List<List<BigDecimal>>> getCarTrajectory(@Valid LocationRecordListReqVO reqVO) {
        List<List<BigDecimal>> coordinateList = locationRecordService.getCarTrajectory(reqVO);
        return success(coordinateList);
    }

    @PostMapping("/mobile-create")
    @Operation(summary = "手机端GPS位置记录")
    public CommonResult<Long> createMobileLocationRecord(@Valid @RequestBody List<LocationRecordMobileSaveReqVO> createReqVO) {
        return success(locationRecordService.createMobileLocationRecord(createReqVO));
    }
}